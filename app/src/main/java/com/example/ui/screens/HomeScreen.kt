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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PlanComptableRepository
import com.example.model.ElementLevel
import com.example.model.LanguageMode
import com.example.ui.components.BadgeSize
import com.example.ui.components.ClassBadge
import com.example.ui.theme.LedgerClassColors
import com.example.ui.theme.LocalLanguageMode
import com.example.ui.theme.MonoFontFamily
import com.example.viewmodel.AppTab
import com.example.viewmodel.LearnSubTab
import com.example.viewmodel.MainViewModel

data class QuickActionItem(
    val titleAr: String,
    val titleFr: String,
    val icon: ImageVector,
    val color: Color,
    val onClick: () -> Unit
)

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val languageMode = LocalLanguageMode.current
    val recentViewedUids by viewModel.recentViewedUids.collectAsState()
    val introDismissed by viewModel.introBannerDismissed.collectAsState()

    val quickActions = listOf(
        QuickActionItem(
            titleAr = "فكّك الرقم",
            titleFr = "Décomposer",
            icon = Icons.Default.Calculate,
            color = MaterialTheme.colorScheme.primary,
            onClick = {
                viewModel.selectLearnSubTab(LearnSubTab.DECONSTRUCT)
                viewModel.selectTab(AppTab.LEARN)
            }
        ),
        QuickActionItem(
            titleAr = "قيود نموذجية",
            titleFr = "Écritures",
            icon = Icons.Default.HistoryEdu,
            color = LedgerClassColors.Class4,
            onClick = {
                viewModel.selectLearnSubTab(LearnSubTab.JOURNAL_ENTRIES)
                viewModel.selectTab(AppTab.LEARN)
            }
        ),
        QuickActionItem(
            titleAr = "القاموس",
            titleFr = "Lexique",
            icon = Icons.Default.Spellcheck,
            color = LedgerClassColors.Class2,
            onClick = {
                viewModel.selectLearnSubTab(LearnSubTab.LEXICON)
                viewModel.selectTab(AppTab.LEARN)
            }
        ),
        QuickActionItem(
            titleAr = "اختبر نفسك",
            titleFr = "Quiz",
            icon = Icons.Default.Quiz,
            color = LedgerClassColors.Class8,
            onClick = {
                viewModel.selectLearnSubTab(LearnSubTab.QUIZ)
                viewModel.selectTab(AppTab.LEARN)
            }
        )
    )

    val classNumbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // 1. Compact Header & Persistent Search Bar
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ClassBadge(classNumber = 1, size = BadgeSize.MEDIUM)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "الدليل المحاسباتي المغربي",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            Text(
                                text = "Guide du Plan Comptable Marocain (CGNC)",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Prominent Search Bar
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable(role = Role.Button) { viewModel.openSearch() }
                        .testTag("home_search_launcher")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "بحث",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "ابحث برقم الحساب أو بالاسم (Code ou Nom)...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            HorizontalDivider()
        }

        // 2. "استئناف" (Resume): Recent viewed accounts
        if (recentViewedUids.isNotEmpty()) {
            item {
                Column(modifier = Modifier.padding(top = 14.dp, bottom = 4.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "استئناف التصفح (Dernières Consultations)",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(recentViewedUids) { uid ->
                            val elem = PlanComptableRepository.getByUid(uid)
                            if (elem != null) {
                                val classColor = LedgerClassColors.forClass(elem.classNumber)
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                                    modifier = Modifier
                                        .clickable { viewModel.openAccountDetails(elem) }
                                        .width(170.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .width(3.dp)
                                                .height(28.dp)
                                                .clip(RoundedCornerShape(2.dp))
                                                .background(classColor)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                                                Text(
                                                    text = elem.code,
                                                    fontFamily = MonoFontFamily,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 12.sp,
                                                    color = classColor
                                                )
                                            }
                                            Text(
                                                text = if (languageMode == LanguageMode.ARABIC_ONLY) elem.arabicTitle else elem.officialFrenchTitle,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
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

        // 3. Compact 2x5 Grid of the 10 Classes
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
                Text(
                    text = "الأصناف المحاسبية العشرة (Classes du Plan)",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "منقولة حرفياً من الكتيب الرسمي (CGNC)",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Render 5 rows of 2 columns
                for (rowIndex in 0 until 5) {
                    val cNum1 = classNumbers[rowIndex * 2]
                    val cNum2 = classNumbers[rowIndex * 2 + 1]

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ClassGridCard(
                            classNumber = cNum1,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.selectClass(cNum1)
                                viewModel.selectTab(AppTab.TREE)
                            }
                        )
                        ClassGridCard(
                            classNumber = cNum2,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                viewModel.selectClass(cNum2)
                                viewModel.selectTab(AppTab.TREE)
                            }
                        )
                    }
                }
            }
        }

        // 4. Quick Action Tools Row
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = "الأدوات التعليمية (Outils Pédagogiques)",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    quickActions.forEach { action ->
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .height(72.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { action.onClick() },
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = action.icon,
                                    contentDescription = action.titleAr,
                                    tint = action.color,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = action.titleAr,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1
                                )
                                Text(
                                    text = action.titleFr,
                                    fontSize = 9.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }

        // 5. PCM Intro Card (collapsible / dismissable)
        if (!introDismissed) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "كيف تستعمل هذا الدليل؟",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            IconButton(
                                onClick = { viewModel.dismissIntroBanner() },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "إخفاء", modifier = Modifier.size(16.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "هذا الدليل مرجع جيب موثوق يعمل بالكامل دون إنترنت. يربط كل حساب بمكانه في القوائم المالية، ويبين رصيده الطبيعي مع قيود نموذجية وأداة ذكية لتفكيك الأرقام.",
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

@Composable
private fun ClassGridCard(
    classNumber: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val languageMode = LocalLanguageMode.current
    val classElements = PlanComptableRepository.getElementsByClass(classNumber)
    val root = classElements.firstOrNull { it.level == ElementLevel.CLASSE }
    val classColor = LedgerClassColors.forClass(classNumber)

    // Accurate titles directly read from data!
    val officialTitleFr = root?.officialFrenchTitle ?: "CLASSE $classNumber"
    val officialTitleAr = root?.arabicTitle ?: "الصنف $classNumber"

    Surface(
        modifier = modifier
            .height(68.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(role = Role.Button, onClick = onClick)
            .testTag("class_grid_card_$classNumber"),
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClassBadge(classNumber = classNumber, size = BadgeSize.SMALL)
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = officialTitleAr,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (languageMode != LanguageMode.ARABIC_ONLY) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        Text(
                            text = officialTitleFr,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
