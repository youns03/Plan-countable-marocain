package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.EducationalData
import com.example.data.PlanComptableRepository
import com.example.model.LearningLesson
import com.example.model.LanguageMode
import com.example.ui.components.BadgeSize
import com.example.ui.components.ClassBadge
import com.example.ui.components.CodeInput
import com.example.ui.components.JournalTable
import com.example.ui.theme.AnomalyAmber
import com.example.ui.theme.CreditGreen
import com.example.ui.theme.DebitBlue
import com.example.ui.theme.LedgerClassColors
import com.example.ui.theme.MonoFontFamily
import com.example.ui.theme.LocalLanguageMode
import com.example.viewmodel.LearnSubTab
import com.example.viewmodel.MainViewModel

@Composable
fun LearnScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val subTab by viewModel.learnSubTab.collectAsState()
    val languageMode = LocalLanguageMode.current
    val subTabs = LearnSubTab.values()

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("learn_screen")
    ) {
        // Horizontal Sub-tabs strip
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 1.dp,
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            ScrollableTabRow(
                selectedTabIndex = subTab.ordinal,
                edgePadding = 12.dp,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                divider = {}
            ) {
                subTabs.forEach { tab ->
                    Tab(
                        selected = subTab == tab,
                        onClick = { viewModel.selectLearnSubTab(tab) },
                        text = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = if (languageMode == LanguageMode.FRENCH_ONLY) tab.titleFr else tab.titleAr,
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = if (subTab == tab) FontWeight.Bold else FontWeight.Medium
                                )
                                if (languageMode == LanguageMode.BILINGUAL) {
                                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                        Text(
                                            text = tab.titleFr,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (subTab == tab) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        },
                        modifier = Modifier.testTag("learn_tab_${tab.name.lowercase()}")
                    )
                }
            }
        }

        when (subTab) {
            LearnSubTab.LEARNING_PATH -> LearningPathSection(viewModel)
            LearnSubTab.DECONSTRUCT -> DeconstructSection(viewModel)
            LearnSubTab.JOURNAL_ENTRIES -> JournalEntriesSection(viewModel)
            LearnSubTab.LEXICON -> LexiconSection()
            LearnSubTab.COMPARISONS -> ComparisonsSection()
            LearnSubTab.QUIZ -> QuizSection(viewModel)
        }
    }
}

// -------------------------------------------------------------
// 0. STRUCTURED LEARNING PATH (مسار Comptabilité générale S1)
// -------------------------------------------------------------
@Composable
private fun LearningPathSection(viewModel: MainViewModel) {
    val languageMode = LocalLanguageMode.current
    val isFrenchOnly = languageMode == LanguageMode.FRENCH_ONLY
    var expandedLessonId by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("learning_path_screen"),
        contentPadding = PaddingValues(16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(
                text = if (isFrenchOnly) "Parcours de comptabilité générale S1" else "مسار المحاسبة العامة S1",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = if (isFrenchOnly) {
                    "Progressez des notions de base vers la lecture des exercices en français."
                } else {
                    "تدرّج من المفاهيم الأساسية إلى فهم التمارين باللغة الفرنسية، دون خلط الشرح بالمصدر الرسمي."
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 3.dp)
            )
        }

        items(EducationalData.learningPath, key = { it.id }) { lesson ->
            LearningLessonCard(
                lesson = lesson,
                isFrenchOnly = isFrenchOnly,
                expanded = expandedLessonId == lesson.id,
                onClick = {
                    expandedLessonId = if (expandedLessonId == lesson.id) null else lesson.id
                },
                onAccountClick = viewModel::openAccountDetailsByCode
            )
        }
    }
}

@Composable
private fun LearningLessonCard(
    lesson: LearningLesson,
    isFrenchOnly: Boolean,
    expanded: Boolean,
    onClick: () -> Unit,
    onAccountClick: (String) -> Unit
) {
    val title = if (isFrenchOnly) lesson.titleFr else lesson.titleAr
    val objective = if (isFrenchOnly) lesson.objectiveFr else lesson.objectiveAr
    val explanation = if (isFrenchOnly) lesson.explanationFr else lesson.explanationAr

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("learning_lesson_${lesson.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = lesson.stageNumber.toString(),
                            fontWeight = FontWeight.Bold,
                            fontFamily = MonoFontFamily
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    if (!isFrenchOnly) {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            Text(
                                text = lesson.titleFr,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                Text(
                    text = if (expanded) "−" else "+",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = objective,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (expanded) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                Text(
                    text = if (isFrenchOnly) "Objectif et explication" else "الهدف والشرح",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = explanation,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = if (isFrenchOnly) "Vocabulaire clé" else "المصطلحات الأساسية",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                lesson.terms.forEach { (termFr, termAr) ->
                    Row(modifier = Modifier.padding(top = 4.dp)) {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            Text(
                                text = termFr,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Text(
                            text = termAr,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "Exemple pratique",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            Text(
                                text = lesson.exampleFr,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 3.dp)
                            )
                        }
                        if (!isFrenchOnly) {
                            Text(
                                text = lesson.exampleAr,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 3.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = if (isFrenchOnly) "Questions de révision" else "مراجعة قصيرة",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                lesson.reviewQuestionsAr.forEachIndexed { index, question ->
                    Text(
                        text = "${index + 1}. $question",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 3.dp)
                    )
                }

                if (lesson.relatedAccountCodes.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = if (isFrenchOnly) "Comptes liés" else "حسابات مرتبطة بالدرس",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        items(lesson.relatedAccountCodes) { code ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)),
                                modifier = Modifier.clickable { onAccountClick(code) }
                            ) {
                                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                    Text(
                                        text = code,
                                        fontFamily = MonoFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Source / المصدر: ${lesson.sourceLabel}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )
                lesson.sourceUrl?.let { url ->
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Text(
                            text = url,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 1. DECONSTRUCT TOOL (فكك الرقم)
// -------------------------------------------------------------
@Composable
private fun DeconstructSection(viewModel: MainViewModel) {
    val inputCode by viewModel.deconstructInput.collectAsState()
    val result = remember(inputCode) { PlanComptableRepository.deconstructCode(inputCode) }
    val quickPresets = listOf("3421", "6193", "2834", "4455", "5141", "7119", "1111", "2355")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, bottom = 90.dp)
    ) {
        item {
            Text(
                text = "أداة فكّك الرقم المحاسباتي (Décomposeur)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "أدخل أي رقم لاكتشاف كيف بني خانة بخانة وفق قواعد المخطط المحاسباتي المغربي.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Custom On-screen Keypad and Boxes
            CodeInput(
                value = inputCode,
                onValueChange = { viewModel.setDeconstructInput(it) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Quick Presets
            Text(
                text = "أمثلة شائعة:",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(quickPresets) { code ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        modifier = Modifier.clickable { viewModel.setDeconstructInput(code) }
                    ) {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            Text(
                                text = code,
                                fontFamily = MonoFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Special rule card
        if (result.specialRuleNoteAr != null) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AnomalyAmber.copy(alpha = 0.1f)
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AnomalyAmber.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = AnomalyAmber,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = result.specialRuleNoteAr,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        // Step Breakdown Cards
        items(result.steps) { step ->
            val classNum = result.detectedClassNumber ?: step.digits.first().digitToIntOrNull() ?: 1
            val stepColor = LedgerClassColors.forClass(classNum)

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        step.element?.let { viewModel.openAccountDetails(it) }
                    },
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = stepColor,
                            contentColor = Color.White
                        ) {
                            Text(
                                text = step.digits,
                                fontFamily = MonoFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = step.levelNameAr,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                Text(
                                    text = "(${step.levelNameFr})",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        if (step.element != null) {
                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                Text(
                                    text = step.element.officialFrenchTitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                text = step.element.arabicTitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Text(
                            text = "• ${step.roleNoteAr}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 2. PRACTICAL JOURNAL ENTRIES (قيود نموذجية)
// -------------------------------------------------------------
@Composable
private fun JournalEntriesSection(viewModel: MainViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "10 قيود محاسبية نموذجية متوازنة",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "أمثلة حقيقية من صميم المقرر المغربي بأرقام حسابات دقيقة وتوازن آلي للمدين والدائن.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(EducationalData.practicalJournalEntries) { entry ->
            JournalTable(
                entry = entry,
                onAccountClick = { code -> viewModel.openAccountDetailsByCode(code) }
            )
        }
    }
}

// -------------------------------------------------------------
// 3. LEXICON & ACRONYMS (القاموس والرموز)
// -------------------------------------------------------------
@Composable
private fun LexiconSection() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("ALL") }

    val filteredLexicon = remember(searchQuery, selectedFilter) {
        if (selectedFilter == "ACRONYMS") emptyList()
        else EducationalData.lexicon.filter {
            if (searchQuery.isBlank()) true
            else it.termFr.contains(searchQuery, ignoreCase = true) ||
                 it.termAr.contains(searchQuery) ||
                 it.definitionAr.contains(searchQuery)
        }
    }

    val filteredAcronyms = remember(searchQuery, selectedFilter) {
        if (selectedFilter == "LEXICON") emptyList()
        else EducationalData.acronyms.filter {
            if (searchQuery.isBlank()) true
            else it.acronym.contains(searchQuery, ignoreCase = true) ||
                 it.fullNameFr.contains(searchQuery, ignoreCase = true) ||
                 it.fullNameAr.contains(searchQuery)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, bottom = 90.dp)
    ) {
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("ابحث في المصطلحات والرموز...", style = MaterialTheme.typography.bodySmall) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    "ALL" to "الكل",
                    "LEXICON" to "المصطلحات (Termes)",
                    "ACRONYMS" to "الرموز (Sigles)"
                ).forEach { (key, label) ->
                    val isSelected = selectedFilter == key
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable { selectedFilter = key }
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
        }

        // Acronyms
        if (filteredAcronyms.isNotEmpty()) {
            item {
                Text(
                    text = "الرموز والاختصارات المحاسبية (Sigles)",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(filteredAcronyms) { acr ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                    contentColor = MaterialTheme.colorScheme.primary
                                ) {
                                    Text(
                                        text = acr.acronym,
                                        fontFamily = MonoFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                Text(
                                    text = acr.fullNameFr,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = acr.fullNameAr,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = acr.explanationAr,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Terms
        if (filteredLexicon.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "قاموس المصطلحات (Lexique)",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(filteredLexicon) { term ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                Text(
                                    text = term.termFr,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = "نطق: ${term.phoneticAr}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = term.termAr,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = term.definitionAr,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 4. COMPARISONS (الفروق المهمة)
// -------------------------------------------------------------
@Composable
private fun ComparisonsSection() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(EducationalData.comparisons) { comp ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = comp.titleAr,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Text(
                            text = comp.titleFr,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = comp.summaryAr,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))

                    comp.rows.forEach { row ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "🔹 ${row.criterionAr}:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = comp.conceptAName,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = row.conceptAValue,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = comp.conceptBName,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = LedgerClassColors.Class4
                                    )
                                    Text(
                                        text = row.conceptBValue,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 5. QUIZ (اختبر نفسك - 40 سؤالاً مع خلط ومراجعة الأخطاء)
// -------------------------------------------------------------
@Composable
private fun QuizSection(viewModel: MainViewModel) {
    val questions by viewModel.quizQuestions.collectAsState()
    val selectedAnswers by viewModel.quizSelectedAnswers.collectAsState()
    val highScore by viewModel.quizHighScore.collectAsState()
    val mistakesMode by viewModel.quizOnlyMistakesMode.collectAsState()

    val total = questions.size
    val answeredCount = selectedAnswers.size
    val correctCount = selectedAnswers.count { (qId, ans) ->
        questions.firstOrNull { it.id == qId }?.correctIndex == ans
    }
    val mistakeCount = answeredCount - correctCount

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Quiz Score Banner & Actions
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (mistakesMode) "مراجعة الأسئلة الخاطئة" else "بنك الاختبارات المحاسبية (40 سؤالاً)",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "النتيجة: $correctCount من $answeredCount صحيحة (أفضل رصيد محفوظ: $highScore)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.shuffleQuiz() },
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Shuffle, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("خلط الأسئلة", style = MaterialTheme.typography.labelSmall)
                        }

                        if (mistakeCount > 0 && !mistakesMode) {
                            OutlinedButton(
                                onClick = { viewModel.reviewMistakes() },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp)
                            ) {
                                Text("راجع أخطائي ($mistakeCount)", style = MaterialTheme.typography.labelSmall, color = AnomalyAmber)
                            }
                        }

                        if (answeredCount > 0) {
                            OutlinedButton(
                                onClick = { viewModel.resetQuiz() },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("إعادة", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            }
        }

        // Questions List
        items(questions) { quiz ->
            val selectedOption = selectedAnswers[quiz.id]
            val hasAnswered = selectedOption != null
            val isCorrect = selectedOption == quiz.correctIndex

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        ClassBadge(classNumber = quiz.classNumber, size = BadgeSize.SMALL)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "سؤال في الصنف ${quiz.classNumber}",
                            style = MaterialTheme.typography.labelMedium,
                            color = LedgerClassColors.forClass(quiz.classNumber)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = quiz.questionAr,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 21.sp
                    )
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Text(
                            text = quiz.questionFr,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    quiz.optionsAr.forEachIndexed { index, optionText ->
                        val isThisSelected = selectedOption == index
                        val isThisCorrect = index == quiz.correctIndex

                        val containerColor = when {
                            !hasAnswered -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                            isThisCorrect -> Color(0xFFDCFCE7)
                            isThisSelected -> Color(0xFFFEE2E2)
                            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                        }

                        val textColor = when {
                            !hasAnswered -> MaterialTheme.colorScheme.onSurface
                            isThisCorrect -> Color(0xFF166534)
                            isThisSelected -> Color(0xFF991B1B)
                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                        }

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable(enabled = !hasAnswered) {
                                    viewModel.answerQuiz(quiz.id, index)
                                },
                            shape = RoundedCornerShape(8.dp),
                            color = containerColor
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (hasAnswered) {
                                    if (isThisCorrect) {
                                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = CreditGreen, modifier = Modifier.size(16.dp))
                                    } else if (isThisSelected) {
                                        Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(16.dp))
                                    } else {
                                        Spacer(modifier = Modifier.size(16.dp))
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                }

                                Text(
                                    text = optionText,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = if (isThisSelected || (hasAnswered && isThisCorrect)) FontWeight.Bold else FontWeight.Normal,
                                    color = textColor
                                )
                            }
                        }
                    }

                    if (hasAnswered) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isCorrect) Color(0xFFF0FDF4) else Color(0xFFFEF2F2))
                                .padding(10.dp)
                        ) {
                            Text(
                                text = if (isCorrect) "✓ أحسنت! إجابة صحيحة." else "✗ إجابة غير موفقة. الإجابة الصحيحة مظللة بالأخضر.",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (isCorrect) Color(0xFF166534) else Color(0xFF991B1B)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = quiz.explanationAr,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
