package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Brush
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
            titleAr = "مسار التعلّم",
            titleFr = "Parcours d’apprentissage",
            icon = Icons.AutoMirrored.Filled.MenuBook,
            color = MaterialTheme.colorScheme.primary,
            onClick = {
                viewModel.selectLearnSubTab(LearnSubTab.LEARNING_PATH)
                viewModel.selectTab(AppTab.LEARN)
            }
        ),
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
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.surface
                            )
                        )
                    )
                    .padding(horizontal = 18.dp, vertical = 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ClassBadge(classNumber = 1, size = BadgeSize.MEDIUM)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = when (languageMode) {
                                LanguageMode.FRENCH_ONLY -> "Plan comptable marocain"
                                else -> "الدليل المحاسباتي المغربي"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            Text(
                                text = if (languageMode == LanguageMode.FRENCH_ONLY) "Guide du Plan Comptable Marocain (CGNC)" else "دليل المخطط المحاسبي المغربي (CGNC)",
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
                        .height(50.dp)
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
                            contentDescription = if (languageMode == LanguageMode.FRENCH_ONLY) "Rechercher" else "بحث",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (languageMode == LanguageMode.FRENCH_ONLY)
                                "Rechercher par code ou intitulé..."
                            else "ابحث برقم الحساب أو بالاسم (Code ou Nom)...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.65f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(role = Role.Button) {
                            viewModel.selectLearnSubTab(LearnSubTab.LEARNING_PATH)
                            viewModel.selectTab(AppTab.LEARN)
                        }
                        .testTag("home_learning_path_cta")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 11.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (languageMode == LanguageMode.FRENCH_ONLY) "Continuer l’apprentissage" else "ابدأ رحلة التعلّم",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (languageMode == LanguageMode.FRENCH_ONLY) "Notions, exemples et progression" else "مفاهيم وأمثلة وتقدّمك الدراسي",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
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
                            text = if (languageMode == LanguageMode.FRENCH_ONLY)
                                "Consultations récentes"
                            else "استئناف التصفح (Dernières Consultations)",
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
                    text = if (languageMode == LanguageMode.FRENCH_ONLY)
                        "Les dix classes comptables"
                    else "الأصناف المحاسبية العشرة (Classes du Plan)",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if (languageMode == LanguageMode.FRENCH_ONLY)
                        "Référentiel fidèle au document fourni (CGNC)"
                    else "منقولة حرفياً من الكتيب الرسمي (CGNC)",
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
                    text = if (languageMode == LanguageMode.FRENCH_ONLY)
                        "Outils pédagogiques"
                    else "الأدوات التعليمية (Outils Pédagogiques)",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    contentPadding = PaddingValues(end = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(quickActions) { action ->
                        Surface(
                            modifier = Modifier
                                .width(124.dp)
                                .height(100.dp)
                                .clip(RoundedCornerShape(18.dp))
                                .clickable(role = Role.Button) { action.onClick() },
                            shape = RoundedCornerShape(18.dp),
                            color = MaterialTheme.colorScheme.surface,
                            tonalElevation = 3.dp,
                            shadowElevation = 4.dp,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                action.color.copy(alpha = if (MaterialTheme.colorScheme.surface == Color.White) 0.28f else 0.42f)
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(
                                                action.color.copy(alpha = 0.16f),
                                                MaterialTheme.colorScheme.surface,
                                                MaterialTheme.colorScheme.surface
                                            )
                                        )
                                    )
                                    .padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = action.icon,
                                    contentDescription = if (languageMode == LanguageMode.FRENCH_ONLY) action.titleFr else action.titleAr,
                                    tint = action.color,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = if (languageMode == LanguageMode.FRENCH_ONLY) action.titleFr else action.titleAr,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1
                                )
                                if (languageMode == LanguageMode.BILINGUAL) {
                                    Text(
                                        text = action.titleFr,
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1
                                    )
                                }
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
                                    text = if (languageMode == LanguageMode.FRENCH_ONLY)
                                    "Comment utiliser ce guide ?"
                                else "كيف تستعمل هذا الدليل؟",
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
                            text = if (languageMode == LanguageMode.FRENCH_ONLY)
                                "Un guide de poche utilisable hors ligne : retrouvez la place de chaque compte dans les états financiers, son solde habituel et des exemples d’écritures."
                            else "هذا الدليل مرجع جيب موثوق يعمل بالكامل دون إنترنت. يربط كل حساب بمكانه في القوائم المالية، ويبين رصيده الطبيعي مع قيود نموذجية وأداة ذكية لتفكيك الأرقام.",
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
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.975f else 1f,
        label = "class-card-press"
    )

    // Accurate titles directly read from data!
    val officialTitleFr = root?.officialFrenchTitle ?: "CLASSE $classNumber"
    val officialTitleAr = root?.arabicTitle ?: "الصنف $classNumber"

    Surface(
        modifier = modifier
            .height(80.dp)
            .graphicsLayer {
                scaleX = cardScale
                scaleY = cardScale
            }
            .clip(RoundedCornerShape(14.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = androidx.compose.foundation.LocalIndication.current,
                role = Role.Button,
                onClick = onClick
            )
            .testTag("class_grid_card_$classNumber"),
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        shadowElevation = 3.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, classColor.copy(alpha = 0.42f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            classColor.copy(alpha = 0.16f),
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClassBadge(classNumber = classNumber, size = BadgeSize.SMALL)
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (languageMode == LanguageMode.FRENCH_ONLY) officialTitleFr else officialTitleAr,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (languageMode == LanguageMode.BILINGUAL) {
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
