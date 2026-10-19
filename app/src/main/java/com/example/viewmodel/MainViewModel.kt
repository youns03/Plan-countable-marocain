package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.EducationalData
import com.example.data.PlanComptableRepository
import com.example.data.PreferencesRepository
import com.example.model.AccountingElement
import com.example.model.ElementLevel
import com.example.model.LanguageMode
import com.example.model.QuizQuestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppTab(val titleAr: String, val titleFr: String) {
    HOME("الرئيسية", "Accueil"),
    TREE("الشجرة", "Plan"),
    LEARN("التعلّم", "Apprendre"),
    ACCOUNT("حسابي", "Mon Compte")
}

enum class LearnSubTab(val titleAr: String, val titleFr: String) {
    DECONSTRUCT("فكّك الرقم", "Décomposeur"),
    JOURNAL_ENTRIES("قيود نموذجية", "Écritures"),
    LEXICON("القاموس والرموز", "Lexique"),
    COMPARISONS("الفروق المهمة", "Différences"),
    QUIZ("اختبر نفسك", "Quiz")
}

@OptIn(FlowPreview::class)
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val prefsRepo = PreferencesRepository.getInstance(application)

    // Navigation Tab
    private val _currentTab = MutableStateFlow(AppTab.HOME)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    // Global Search Overlay
    private val _isSearchOpen = MutableStateFlow(false)
    val isSearchOpen: StateFlow<Boolean> = _isSearchOpen.asStateFlow()

    // Selected Class in Tree
    private val _selectedClassNumber = MutableStateFlow(1)
    val selectedClassNumber: StateFlow<Int> = _selectedClassNumber.asStateFlow()

    // Selected Account for Detail Sheet
    private val _selectedAccount = MutableStateFlow<AccountingElement?>(null)
    val selectedAccount: StateFlow<AccountingElement?> = _selectedAccount.asStateFlow()

    // Expanded UIDs in Tree
    private val _expandedUids = MutableStateFlow<Set<String>>(emptySet())
    val expandedUids: StateFlow<Set<String>> = _expandedUids.asStateFlow()

    // Learn SubTab
    private val _learnSubTab = MutableStateFlow(LearnSubTab.DECONSTRUCT)
    val learnSubTab: StateFlow<LearnSubTab> = _learnSubTab.asStateFlow()

    // Selected Journal Entry in Learn Tab
    private val _selectedJournalEntryId = MutableStateFlow<String?>(null)
    val selectedJournalEntryId: StateFlow<String?> = _selectedJournalEntryId.asStateFlow()

    // Deconstruct Tool Input
    private val _deconstructInput = MutableStateFlow("3421")
    val deconstructInput: StateFlow<String> = _deconstructInput.asStateFlow()

    // Search Query & Class Filter
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchFilterClass = MutableStateFlow<Int?>(null)
    val searchFilterClass: StateFlow<Int?> = _searchFilterClass.asStateFlow()

    // Preferences & Settings Flows
    val favoriteUids: StateFlow<Set<String>> = prefsRepo.favoriteUids
    val favorites: StateFlow<Set<String>> get() = favoriteUids
    val recentSearches: StateFlow<List<String>> = prefsRepo.recentSearches
    val recentAccountUids: StateFlow<List<String>> = prefsRepo.recentAccountUids
    val recentViewedUids: StateFlow<List<String>> get() = recentAccountUids
    val languageMode: StateFlow<LanguageMode> = prefsRepo.languageMode
    val isDarkTheme: StateFlow<Boolean?> = prefsRepo.isDarkTheme
    val largeFont: StateFlow<Boolean> = prefsRepo.largeFont

    val introBannerDismissed: StateFlow<Boolean> = prefsRepo.introBannerDismissed

    fun dismissIntroBanner() {
        prefsRepo.dismissIntroBanner()
    }

    // Recent Accounts List derived from recentAccountUids
    val recentAccounts: StateFlow<List<AccountingElement>> = recentAccountUids.map { uids ->
        uids.mapNotNull { PlanComptableRepository.getElementByUid(it) }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Debounced Search Results computed on Dispatchers.Default
    val searchResults: StateFlow<List<AccountingElement>> = combine(
        _searchQuery.debounce(150).distinctUntilChanged(),
        _searchFilterClass
    ) { query, filter ->
        if (query.isBlank()) {
            emptyList()
        } else {
            PlanComptableRepository.search(query, filter)
        }
    }.flowOn(Dispatchers.Default)
     .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Precomputed Flattened Visible Tree for the selected class
    // Updates smoothly whenever class or expandedUids changes without O(N^2) recomposition!
    val flattenedVisibleTree: StateFlow<List<AccountingElement>> = combine(
        _selectedClassNumber,
        _expandedUids
    ) { classNum, expandedSet ->
        val classElements = PlanComptableRepository.getElementsByClass(classNum)

        fun isVisible(elem: AccountingElement): Boolean {
            if (elem.level == ElementLevel.CLASSE || elem.level == ElementLevel.RUBRIQUE) return true
            val parentUid = elem.parentUid ?: return true
            if (!expandedSet.contains(parentUid)) return false
            val parentElem = PlanComptableRepository.getElementByUid(parentUid) ?: return true
            return isVisible(parentElem)
        }

        classElements.filter { isVisible(it) }
    }.flowOn(Dispatchers.Default)
     .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Quiz State
    private val _quizQuestions = MutableStateFlow(EducationalData.quizzes)
    val quizQuestions: StateFlow<List<QuizQuestion>> = _quizQuestions.asStateFlow()

    private val _quizSelectedAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val quizSelectedAnswers: StateFlow<Map<Int, Int>> = _quizSelectedAnswers.asStateFlow()

    val quizHighScore: StateFlow<Int> = prefsRepo.quizHighScore

    private val _quizOnlyMistakesMode = MutableStateFlow(false)
    val quizOnlyMistakesMode: StateFlow<Boolean> = _quizOnlyMistakesMode.asStateFlow()

    fun reviewMistakes() {
        val mistakes = _quizQuestions.value.filter { q ->
            val answered = _quizSelectedAnswers.value[q.id]
            answered != null && answered != q.correctIndex
        }
        if (mistakes.isNotEmpty()) {
            _quizQuestions.value = mistakes
            _quizSelectedAnswers.value = emptyMap()
            _quizOnlyMistakesMode.value = true
        }
    }

    fun exitMistakesMode() {
        _quizQuestions.value = EducationalData.quizzes
        _quizSelectedAnswers.value = emptyMap()
        _quizOnlyMistakesMode.value = false
    }

    init {
        // Expand Rubriques of default Class 1
        expandClassRubriques(1)
    }

    fun selectTab(tab: AppTab) {
        _currentTab.value = tab
    }

    fun openSearch() {
        _isSearchOpen.value = true
    }

    fun closeSearch() {
        _isSearchOpen.value = false
        _searchQuery.value = ""
    }

    fun selectClass(classNumber: Int) {
        _selectedClassNumber.value = classNumber
        expandClassRubriques(classNumber)
    }

    private fun expandClassRubriques(classNumber: Int) {
        val classElements = PlanComptableRepository.getElementsByClass(classNumber)
        val rubriqueUids = classElements
            .filter { it.level == ElementLevel.RUBRIQUE }
            .map { it.uid }
            .toSet()
        _expandedUids.value = rubriqueUids
    }

    fun toggleExpanded(uid: String) {
        val current = _expandedUids.value.toMutableSet()
        if (current.contains(uid)) {
            current.remove(uid)
        } else {
            current.add(uid)
        }
        _expandedUids.value = current
    }

    fun expandAllInClass() {
        val classElements = PlanComptableRepository.getElementsByClass(_selectedClassNumber.value)
        _expandedUids.value = classElements.map { it.uid }.toSet()
    }

    fun collapseAllInClass() {
        _expandedUids.value = emptySet()
    }

    fun openAccountDetails(element: AccountingElement) {
        _selectedAccount.value = element
        prefsRepo.addRecentAccount(element.uid)
    }

    fun openAccountDetailsByCode(code: String) {
        val elem = PlanComptableRepository.getElementByCode(code)
        if (elem != null) {
            openAccountDetails(elem)
        }
    }

    fun closeAccountDetails() {
        _selectedAccount.value = null
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun setSearchFilterClass(classNum: Int?) {
        _searchFilterClass.value = classNum
    }

    fun submitSearch(query: String) {
        if (query.isNotBlank()) {
            prefsRepo.addRecentSearch(query)
        }
    }

    fun clearRecentSearches() {
        prefsRepo.clearRecentSearches()
    }

    fun toggleFavorite(uid: String) {
        prefsRepo.toggleFavorite(uid)
    }

    fun isFavorite(uid: String): Boolean {
        return prefsRepo.isFavorite(uid)
    }

    fun selectLearnSubTab(subTab: LearnSubTab) {
        _learnSubTab.value = subTab
    }

    fun selectJournalEntry(entryId: String) {
        _selectedJournalEntryId.value = entryId
        _learnSubTab.value = LearnSubTab.JOURNAL_ENTRIES
        _currentTab.value = AppTab.LEARN
    }

    fun setDeconstructInput(input: String) {
        _deconstructInput.value = PlanComptableRepository.convertArabicIndicNumerals(input).filter { it.isDigit() }.take(6)
    }

    fun answerQuiz(questionId: Int, selectedOptionIndex: Int) {
        val map = _quizSelectedAnswers.value.toMutableMap()
        map[questionId] = selectedOptionIndex
        _quizSelectedAnswers.value = map

        // Save the best score only when a complete, regular quiz has been answered.
        // Mistake-review sessions are practice aids and should not affect the record.
        val questions = _quizQuestions.value
        val allAnswered = questions.isNotEmpty() && questions.all { map.containsKey(it.id) }
        if (!_quizOnlyMistakesMode.value && allAnswered) {
            val score = questions.count { map[it.id] == it.correctIndex }
            prefsRepo.saveQuizHighScore(score)
        }
    }

    fun shuffleQuiz() {
        _quizQuestions.value = EducationalData.quizzes.shuffled()
        _quizSelectedAnswers.value = emptyMap()
    }

    fun resetQuiz() {
        _quizSelectedAnswers.value = emptyMap()
    }

    fun setLanguageMode(mode: LanguageMode) {
        prefsRepo.setLanguageMode(mode)
    }

    fun setDarkTheme(isDark: Boolean?) {
        prefsRepo.setDarkTheme(isDark)
    }

    fun setLargeFont(enabled: Boolean) {
        prefsRepo.setLargeFont(enabled)
    }
}
