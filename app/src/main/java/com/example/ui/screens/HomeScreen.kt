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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LanguageMode
import com.example.ui.theme.ClassColors
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.SecondaryAmber
import com.example.viewmodel.AppTab
import com.example.viewmodel.LearnSubTab
import com.example.viewmodel.MainViewModel

data class QuickAction(
    val titleAr: String,
    val titleFr: String,
    val icon: ImageVector,
    val color: Color,
    val onClick: () -> Unit
)

data class ClassOverview(
    val number: Int,
    val titleFr: String,
    val titleAr: String,
    val statementAr: String,
    val statementFr: String,
    val summaryAr: String,
    val normalBalanceAr: String
)

val classesOverviewList = listOf(
    ClassOverview(1, "COMPTES DE FINANCEMENT PERMANENT", "التمويل الدائم", "الميزانية - الخصوم", "Bilan - Passif", "رأس المال، الاحتياطيات، والديون البنكية لأكثر من سنة.", "دائن غالباً"),
    ClassOverview(2, "COMPTES D'ACTIF IMMOBILISE", "الأصول الثابتة", "الميزانية - الأصول", "Bilan - Actif", "الأراضي، المباني، المعدات الصناعية، والبرمجيات.", "مدين دائماً"),
    ClassOverview(3, "COMPTES D'ACTIF CIRCULANT (HORS TRESORERIE)", "الأصول المتداولة", "الميزانية - الأصول", "Bilan - Actif", "المخزون، ديون الزبائن، وسندات التوظيف قصيرة الأجل.", "مدين غالباً"),
    ClassOverview(4, "COMPTES DE PASSIF CIRCULANT (HORS TRESORERIE)", "الخصوم المتداولة", "الميزانية - الخصوم", "Bilan - Passif", "ديون الموردين، مستحقات الضمان الاجتماعي والضرائب.", "دائن غالباً"),
    ClassOverview(5, "COMPTES DE TRESORERIE", "حسابات الخزينة", "الميزانية - سيولة", "Bilan - Trésorerie", "أموال البنك، صندوق السيولة، والسحب البنكي على المكشوف.", "مدين (أصل) / دائن (خصم)"),
    ClassOverview(6, "COMPTES DE CHARGES", "التكاليف والمصروفات", "قائمة التكاليف (CPC)", "C.P.C. - Charges", "مشتريات البضائع، الكراء، الكهرباء، والرواتب.", "مدين دائماً"),
    ClassOverview(7, "COMPTES DE PRODUITS", "العائدات والإيرادات", "قائمة العائدات (CPC)", "C.P.C. - Produits", "مبيعات البضائع، الخدمات المقدمة، والفوائد المقبوضة.", "دائن دائماً"),
    ClassOverview(8, "COMPTES DE RESULTATS", "حسابات النتائج (ESG)", "جدول أرصدة التسيير", "Soldes de Gestion", "حسابات نهاية السنة لتحديد القيمة المضافة وصافي الربح.", "متغير حسب الربح/الخسارة"),
    ClassOverview(9, "COMPTES ANALYTIQUES", "المحاسبة التحليلية للتسيير", "محاسبة داخلية", "Comptabilité CAGE", "حساب كلفة إنتاج كل سلعة ومردودية الأقسام الداخلية.", "توازن تحليلي داخلي"),
    ClassOverview(0, "COMPTES SPECIAUX", "الحسابات الخاصة والنظامية", "خارج الميزانية", "Engagements Hors Bilan", "حسابات فتح وإقفال الدفاتر والكفالات والضمانات البنكية.", "حسابات نظامية")
)

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val quickActions = listOf(
        QuickAction(
            titleAr = "فكّك الرقم",
            titleFr = "Décomposer",
            icon = Icons.Default.Calculate,
            color = PrimaryBlue,
            onClick = {
                viewModel.selectLearnSubTab(LearnSubTab.DECONSTRUCT)
                viewModel.selectTab(AppTab.LEARN)
            }
        ),
        QuickAction(
            titleAr = "القاموس",
            titleFr = "Lexique",
            icon = Icons.Default.Spellcheck,
            color = ClassColors.Class2,
            onClick = {
                viewModel.selectLearnSubTab(LearnSubTab.LEXICON)
                viewModel.selectTab(AppTab.LEARN)
            }
        ),
        QuickAction(
            titleAr = "قيود نموذجية",
            titleFr = "Écritures",
            icon = Icons.Default.HistoryEdu,
            color = ClassColors.Class4,
            onClick = {
                viewModel.selectLearnSubTab(LearnSubTab.JOURNAL_ENTRIES)
                viewModel.selectTab(AppTab.LEARN)
            }
        ),
        QuickAction(
            titleAr = "المفضلة",
            titleFr = "Favoris",
            icon = Icons.Default.Bookmark,
            color = SecondaryAmber,
            onClick = { viewModel.setShowFavoritesDialog(true) }
        ),
        QuickAction(
            titleAr = "اختبر نفسك",
            titleFr = "Quiz",
            icon = Icons.Default.Quiz,
            color = ClassColors.Class8,
            onClick = {
                viewModel.selectLearnSubTab(LearnSubTab.QUIZ)
                viewModel.selectTab(AppTab.LEARN)
            }
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Hero Header
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                PrimaryBlue,
                                PrimaryBlue.copy(alpha = 0.85f)
                            )
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.2f),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                    contentDescription = "الشعار",
                                    tint = Color.White,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "الدليل المحاسباتي المغربي",
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Guide du Plan Comptable Marocain",
                                fontSize = 13.sp,
                                color = Color.White.copy(alpha = 0.85f),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Instant Search launcher card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.selectTab(AppTab.SEARCH) }
                            .testTag("home_search_launcher"),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "بحث",
                                tint = PrimaryBlue,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "ابحث برقم الحساب أو بالاسم...",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF64748B)
                                )
                                Text(
                                    text = "Chercher par n° de compte ou mot clé",
                                    fontSize = 11.sp,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Quick Navigation Buttons Row
        item {
            Column(modifier = Modifier.padding(top = 18.dp, bottom = 6.dp)) {
                Text(
                    text = "الوصول السريع • Accès Rapide",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(quickActions) { action ->
                        Card(
                            modifier = Modifier
                                .clickable { action.onClick() }
                                .width(110.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, action.color.copy(alpha = 0.2f))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp, horizontal = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = action.color.copy(alpha = 0.12f),
                                    modifier = Modifier.size(42.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = action.icon,
                                            contentDescription = action.titleAr,
                                            tint = action.color,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = action.titleAr,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1
                                )
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

        // Introduction Card (Why & How)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryBlue.copy(alpha = 0.2f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = "تعريف",
                            tint = PrimaryBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ما هو المخطط المحاسباتي المغربي (PCM)؟",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "هو المنظومة المحاسبية الموحدة في المغرب (CGNC) التي تنظم تسجيل كل درهم يدخل أو يخرج من المقاولة. ينقسم إلى 10 أصناف (من 1 إلى 9 ثم 0) لضمان الشفافية والامتثال الضريبي والإداري.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 19.sp
                    )
                }
            }
        }

        // Section Title: The 10 Classes
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "الأصناف العشرة للكتيب (Classes 1 à 0)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "اختر صنفاً لاستعراض شجرته وحساباته",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // 10 Class Cards
        items(classesOverviewList) { item ->
            val classColor = ClassColors.forClass(item.number)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
                    .clickable {
                        viewModel.selectClass(item.number)
                        viewModel.selectTab(AppTab.CLASSES)
                    }
                    .testTag("class_card_${item.number}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, classColor.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Class number badge
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = classColor,
                        contentColor = Color.White,
                        modifier = Modifier.size(46.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "${item.number}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "الصنف ${item.number} • ${item.titleAr}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = classColor.copy(alpha = 0.12f),
                                contentColor = classColor
                            ) {
                                Text(
                                    text = item.statementAr,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = item.titleFr,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = classColor
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = item.summaryAr,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 17.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "فتح",
                        tint = classColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
