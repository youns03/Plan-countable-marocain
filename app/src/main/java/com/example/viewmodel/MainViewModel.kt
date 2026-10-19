package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.EducationalData
import com.example.data.PlanComptableRepository
import com.example.data.PreferencesRepository
import com.example.model.AccountingElement
import com.example.model.LanguageMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppTab(val titleAr: String, val titleFr: String) {
    HOME("الرئيسية", "Accueil"),
    CLASSES("الأقسام", "Classes"),
    SEARCH("البحث", "Recherche"),
    LEARN("التعلّم", "Apprendre"),
    SETTINGS("الإعدادات", "Réglages")
}

enum class LearnSubTab(val titleAr: String, val titleFr: String) {
    DECONSTRUCT("فكّك الرقم", "Décomposeur"),
    LEXICON("القاموس والرموز", "Lexique"),
    COMPARISONS("الفروق المهمة", "Différences"),
    JOURNAL_ENTRIES("قيود نموذجية", "Écritures"),
    QUIZ("اختبر نفسك", "Quiz")
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val prefsRepo = PreferencesRepository.getInstance(application)

    // Navigation state
    private val _currentTab = MutableStateFlow(AppTab.HOME)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    private val _selectedClassNumber = MutableStateFlow(1)
    val selectedClassNumber: StateFlow<Int> = _selectedClassNumber.asStateFlow()

    private val _learnSubTab = MutableStateFlow(LearnSubTab.DECONSTRUCT)
    val learnSubTab: StateFlow<LearnSubTab> = _learnSubTab.asStateFlow()

    // Selected account for details sheet
    private val _selectedAccount = MutableStateFlow<AccountingElement?>(null)
    val selectedAccount: StateFlow<AccountingElement?> = _selectedAccount.asStateFlow()

    // Search state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchFilterClass = MutableStateFlow<Int?>(null)
    val searchFilterClass: StateFlow<Int?> = _searchFilterClass.asStateFlow()

    private val _searchResults = MutableStateFlow<List<AccountingElement>>(emptyList())
    val searchResults: StateFlow<List<AccountingElement>> = _searchResults.asStateFlow()

    // Deconstruct tool state
    private val _deconstructInput = MutableStateFlow("3421")
    val deconstructInput: StateFlow<String> = _deconstructInput.asStateFlow()

    // Preferences & Bookmarks
    val favorites: StateFlow<Set<String>> = prefsRepo.favorites
    val recentSearches: StateFlow<List<String>> = prefsRepo.recentSearches
    val languageMode: StateFlow<LanguageMode> = prefsRepo.languageMode
    val isDarkTheme: StateFlow<Boolean?> = prefsRepo.isDarkTheme
    val largeFont: StateFlow<Boolean> = prefsRepo.largeFont

    // Quiz state
    private val _quizSelectedAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val quizSelectedAnswers: StateFlow<Map<Int, Int>> = _quizSelectedAnswers.asStateFlow()

    // Show favorites sheet/dialog
    private val _showFavoritesDialog = MutableStateFlow(false)
    val showFavoritesDialog: StateFlow<Boolean> = _showFavoritesDialog.asStateFlow()

    // Expanded nodes in Class tree: Set of account codes currently expanded
    private val _expandedCodes = MutableStateFlow<Set<String>>(emptySet())
    val expandedCodes: StateFlow<Set<String>> = _expandedCodes.asStateFlow()

    init {
        // Expand top level rubriques of Class 1 by default
        expandClassRubriques(1)
    }

    fun selectTab(tab: AppTab) {
        _currentTab.value = tab
    }

    fun selectClass(classNumber: Int) {
        _selectedClassNumber.value = classNumber
        expandClassRubriques(classNumber)
    }

    private fun expandClassRubriques(classNumber: Int) {
        val classElements = PlanComptableRepository.getElementsByClass(classNumber)
        val rubriqueCodes = classElements
            .filter { it.level == com.example.model.ElementLevel.RUBRIQUE }
            .map { it.code }
            .toSet()
        _expandedCodes.value = rubriqueCodes
    }

    fun toggleExpanded(code: String) {
        val current = _expandedCodes.value.toMutableSet()
        if (current.contains(code)) {
            current.remove(code)
        } else {
            current.add(code)
        }
        _expandedCodes.value = current
    }

    fun expandAllInClass() {
        val classElements = PlanComptableRepository.getElementsByClass(_selectedClassNumber.value)
        _expandedCodes.value = classElements.map { it.code }.toSet()
    }

    fun collapseAllInClass() {
        _expandedCodes.value = emptySet()
    }

    fun selectLearnSubTab(subTab: LearnSubTab) {
        _learnSubTab.value = subTab
    }

    fun openAccountDetails(element: AccountingElement) {
        _selectedAccount.value = element
    }

    fun openAccountDetailsByCode(code: String) {
        val elem = PlanComptableRepository.getElementByCode(code)
        if (elem != null) {
            _selectedAccount.value = elem
        }
    }

    fun closeAccountDetails() {
        _selectedAccount.value = null
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        performSearch()
    }

    fun setSearchFilterClass(classNum: Int?) {
        _searchFilterClass.value = classNum
        performSearch()
    }

    private fun performSearch() {
        val q = _searchQuery.value
        val filter = _searchFilterClass.value
        if (q.isBlank()) {
            _searchResults.value = emptyList()
        } else {
            _searchResults.value = PlanComptableRepository.search(q, filter)
        }
    }

    fun submitSearch(query: String) {
        if (query.isNotBlank()) {
            prefsRepo.addRecentSearch(query)
        }
    }

    fun clearRecentSearches() {
        prefsRepo.clearRecentSearches()
    }

    fun toggleFavorite(code: String) {
        prefsRepo.toggleFavorite(code)
    }

    fun isFavorite(code: String): Boolean {
        return prefsRepo.isFavorite(code)
    }

    fun setShowFavoritesDialog(show: Boolean) {
        _showFavoritesDialog.value = show
    }

    fun setDeconstructInput(input: String) {
        _deconstructInput.value = input.filter { it.isDigit() }.take(7)
    }

    fun answerQuiz(questionId: Int, selectedOptionIndex: Int) {
        val map = _quizSelectedAnswers.value.toMutableMap()
        map[questionId] = selectedOptionIndex
        _quizSelectedAnswers.value = map
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
