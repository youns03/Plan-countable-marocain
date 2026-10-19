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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.EducationalData
import com.example.data.PlanComptableRepository
import com.example.model.ComparisonItem
import com.example.model.JournalEntry
import com.example.model.LexiconTerm
import com.example.model.QuizQuestion
import com.example.ui.theme.ClassColors
import com.example.ui.theme.CreditColor
import com.example.ui.theme.DebitColor
import com.example.ui.theme.PrimaryBlue
import com.example.viewmodel.LearnSubTab
import com.example.viewmodel.MainViewModel

@Composable
fun LearnScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val subTab by viewModel.learnSubTab.collectAsState()
    val subTabs = LearnSubTab.values()

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("learn_screen")
    ) {
        // Sub-tabs navigation bar
        ScrollableTabRow(
            selectedTabIndex = subTab.ordinal,
            edgePadding = 12.dp,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = PrimaryBlue
        ) {
            subTabs.forEach { tab ->
                Tab(
                    selected = subTab == tab,
                    onClick = { viewModel.selectLearnSubTab(tab) },
                    text = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = tab.titleAr,
                                fontWeight = if (subTab == tab) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 13.sp
                            )
                            Text(
                                text = tab.titleFr,
                                fontSize = 10.sp,
                                color = if (subTab == tab) PrimaryBlue else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    modifier = Modifier.testTag("learn_tab_${tab.name.lowercase()}")
                )
            }
        }

        when (subTab) {
            LearnSubTab.DECONSTRUCT -> DeconstructToolSection(viewModel)
            LearnSubTab.LEXICON -> LexiconSection()
            LearnSubTab.COMPARISONS -> ComparisonsSection()
            LearnSubTab.JOURNAL_ENTRIES -> JournalEntriesSection(viewModel)
            LearnSubTab.QUIZ -> QuizSection(viewModel)
        }
    }
}

// -------------------------------------------------------------
// 1. DECONSTRUCT TOOL (أداة فكك الرقم)
// -------------------------------------------------------------
@Composable
fun DeconstructToolSection(viewModel: MainViewModel) {
    val inputCode by viewModel.deconstructInput.collectAsState()
    val result = remember(inputCode) { PlanComptableRepository.deconstructCode(inputCode) }
    val quickExamples = listOf("3421", "6111", "2834", "4411", "5141", "6193", "7119", "2332")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, bottom = 90.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryBlue)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "أداة فكّك الرقم المحاسباتي (Décomposeur)",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "اكتب أي رقم حساب لتكتشف فورياً كيف بنِي خانة بخانة من الصنف إلى الحساب الفرعي.",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Text input
            OutlinedTextField(
                value = inputCode,
                onValueChange = { viewModel.setDeconstructInput(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("deconstruct_input_field"),
                label = { Text("أدخل رقم الحساب (مثال: 3421 أو 2834)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Quick example pills
            Text(
                text = "جرّب أرقاماً شائعة:",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(quickExamples) { code ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.clickable { viewModel.setDeconstructInput(code) }
                    ) {
                        Text(
                            text = code,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = PrimaryBlue,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
        }

        // Special rule alert
        if (result.specialRuleNoteAr != null) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 14.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f)
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = result.specialRuleNoteAr,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        // Deconstructed Steps
        items(result.steps) { step ->
            val classNum = step.digits.first().digitToIntOrNull() ?: 1
            val stepColor = ClassColors.forClass(classNum)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .clickable {
                        step.element?.let { viewModel.openAccountDetails(it) }
                    },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, stepColor.copy(alpha = 0.25f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = stepColor,
                                contentColor = Color.White
                            ) {
                                Text(
                                    text = step.digits,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = step.levelNameAr,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        if (step.element != null) {
                            Text(
                                text = "اضغط للمزيد ←",
                                fontSize = 11.sp,
                                color = stepColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (step.element != null) {
                        Text(
                            text = step.element.officialFrenchTitle,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = step.element.arabicTitle,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        Text(
                            text = "(رقم غير مسجل كعنوان رسمي منفصل، أو مخصص للشركات داخلياً)",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "• ${step.roleNoteAr}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 2. LEXICON & ACRONYMS (القاموس والرموز)
// -------------------------------------------------------------
@Composable
fun LexiconSection() {
    var selectedFilter by remember { mutableStateOf("ALL") } // ALL, LEXICON, ACRONYMS
    var searchQuery by remember { mutableStateOf("") }

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
                placeholder = { Text("ابحث في القاموس أو الرموز...") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Filter chips
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    "ALL" to "الكل (Tous)",
                    "LEXICON" to "المصطلحات (Termes)",
                    "ACRONYMS" to "الرموز (Sigles)"
                ).forEach { (key, label) ->
                    val isSelected = selectedFilter == key
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) PrimaryBlue else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable { selectedFilter = key }
                    ) {
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Acronyms Cards
        if (filteredAcronyms.isNotEmpty()) {
            item {
                Text(
                    text = "الرموز والاختصارات المحاسبية (Sigles)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(filteredAcronyms) { acr ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = PrimaryBlue.copy(alpha = 0.15f),
                                contentColor = PrimaryBlue
                            ) {
                                Text(
                                    text = acr.acronym,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Text(
                                text = acr.fullNameFr,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = acr.fullNameAr,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = PrimaryBlue
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = acr.explanationAr,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        }

        // Lexicon Terms Cards
        if (filteredLexicon.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "قاموس المصطلحات الأساسية (Lexique)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(filteredLexicon) { term ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = term.termFr,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryBlue
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = "نطق: ${term.phoneticAr}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = term.termAr,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = term.definitionAr,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = term.definitionFr,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 3. COMPARISONS (الفروق المهمة)
// -------------------------------------------------------------
@Composable
fun ComparisonsSection() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, bottom = 90.dp)
    ) {
        items(EducationalData.comparisons) { comp ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = comp.titleAr,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue
                    )
                    Text(
                        text = comp.titleFr,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = comp.summaryAr,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(10.dp))

                    // Comparison Table Rows
                    comp.rows.forEach { row ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "🔹 ${row.criterionAr}:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = comp.conceptAName,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = PrimaryBlue
                                    )
                                    Text(
                                        text = row.conceptAValue,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = comp.conceptBName,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = ClassColors.Class4
                                    )
                                    Text(
                                        text = row.conceptBValue,
                                        fontSize = 12.sp,
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
// 4. JOURNAL ENTRIES (القيود التطبيقية النموذجية)
// -------------------------------------------------------------
@Composable
fun JournalEntriesSection(viewModel: MainViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, bottom = 90.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = ClassColors.Class4)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "5 قيود محاسبية نموذجية من المقرر المغربي",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "شراء، بيع، أداء بنكي، أجور CNSS، واستهلاكات سنوية بأرقام حسابات دقيقة.",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        items(EducationalData.practicalJournalEntries) { entry ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = "${entry.dateExample} • ${entry.documentRef}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = entry.titleAr,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = entry.titleFr,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = entry.descriptionAr,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Ledger table header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    ) {
                        Text(text = "رقم الحساب والبيان", fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(2f))
                        Text(text = "مدين (DH)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DebitColor, modifier = Modifier.weight(1f))
                        Text(text = "دائن (DH)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CreditColor, modifier = Modifier.weight(1f))
                    }

                    // Ledger lines
                    entry.lines.forEach { line ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(2f)) {
                                Text(
                                    text = "${line.accountCode} - ${line.accountTitleFr}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.clickable {
                                        viewModel.openAccountDetailsByCode(line.accountCode)
                                    }
                                )
                                Text(
                                    text = line.accountTitleAr,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = line.debitAmount?.let { "%.2f".format(it) } ?: "-",
                                fontSize = 12.sp,
                                fontWeight = if (line.debitAmount != null) FontWeight.Bold else FontWeight.Normal,
                                color = if (line.debitAmount != null) DebitColor else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = line.creditAmount?.let { "%.2f".format(it) } ?: "-",
                                fontSize = 12.sp,
                                fontWeight = if (line.creditAmount != null) FontWeight.Bold else FontWeight.Normal,
                                color = if (line.creditAmount != null) CreditColor else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Pedagogical Explanation
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(PrimaryBlue.copy(alpha = 0.08f))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "💡 تحليل القيد وقاعدة التوازن:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryBlue
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = entry.pedagogicalExplanationAr,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// 5. QUIZ (اختبر نفسك)
// -------------------------------------------------------------
@Composable
fun QuizSection(viewModel: MainViewModel) {
    val selectedAnswers by viewModel.quizSelectedAnswers.collectAsState()
    val totalQuestions = EducationalData.quizzes.size
    val answeredCount = selectedAnswers.size
    val correctCount = selectedAnswers.count { (qId, ansIdx) ->
        val q = EducationalData.quizzes.firstOrNull { it.id == qId }
        q?.correctIndex == ansIdx
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, bottom = 90.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = ClassColors.Class8)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "اختبارات الأصناف المحاسبية",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "النتيجة: $correctCount من $answeredCount إجابة صحيحة (إجمالي $totalQuestions أسئلة)",
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }

                    if (answeredCount > 0) {
                        OutlinedButton(
                            onClick = { viewModel.resetQuiz() },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                        ) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("إعادة", fontSize = 12.sp)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        items(EducationalData.quizzes) { quiz ->
            val selectedOption = selectedAnswers[quiz.id]
            val hasAnswered = selectedOption != null
            val isCorrect = selectedOption == quiz.correctIndex

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = ClassColors.forClass(quiz.classNumber).copy(alpha = 0.15f),
                        contentColor = ClassColors.forClass(quiz.classNumber)
                    ) {
                        Text(
                            text = "سؤال حول الصنف ${quiz.classNumber}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = quiz.questionAr,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 21.sp
                    )
                    Text(
                        text = quiz.questionFr,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Options list
                    quiz.optionsAr.forEachIndexed { index, optionText ->
                        val isThisSelected = selectedOption == index
                        val isThisCorrect = index == quiz.correctIndex

                        val containerColor = when {
                            !hasAnswered -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                            isThisCorrect -> Color(0xFFDCFCE7) // green
                            isThisSelected -> Color(0xFFFEE2E2) // red
                            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
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
                                .padding(vertical = 4.dp)
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
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (hasAnswered) {
                                    if (isThisCorrect) {
                                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF16A34A), modifier = Modifier.size(18.dp))
                                    } else if (isThisSelected) {
                                        Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(18.dp))
                                    } else {
                                        Spacer(modifier = Modifier.size(18.dp))
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                }

                                Text(
                                    text = optionText,
                                    fontSize = 13.sp,
                                    fontWeight = if (isThisSelected || (hasAnswered && isThisCorrect)) FontWeight.Bold else FontWeight.Normal,
                                    color = textColor,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

                    // Explanation after answering
                    if (hasAnswered) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isCorrect) Color(0xFFF0FDF4) else Color(0xFFFEF2F2))
                                .border(1.dp, if (isCorrect) Color(0xFF86EFAC) else Color(0xFFFECACA), RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Text(
                                text = if (isCorrect) "✓ أحسنت! إجابة صحيحة." else "✗ إجابة غير موفقة. الإجابة الصحيحة موضحة بالأخضر.",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isCorrect) Color(0xFF166534) else Color(0xFF991B1B)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = quiz.explanationAr,
                                fontSize = 12.sp,
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
