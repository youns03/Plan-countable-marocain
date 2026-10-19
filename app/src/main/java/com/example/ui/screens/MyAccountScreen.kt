package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.SettingsBrightness
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PlanComptableRepository
import com.example.model.LanguageMode
import com.example.ui.components.AccountRow
import com.example.ui.components.BadgeSize
import com.example.ui.components.ClassBadge
import com.example.ui.theme.AnomalyAmber
import com.example.ui.theme.LedgerClassColors
import com.example.ui.theme.LocalLanguageMode
import com.example.viewmodel.MainViewModel

@Composable
fun MyAccountScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val favorites by viewModel.favorites.collectAsState()
    val languageMode by viewModel.languageMode.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val largeFont by viewModel.largeFont.collectAsState()

    var favSearchQuery by remember { mutableStateOf("") }
    var showAuditSection by remember { mutableStateOf(false) }

    val auditStats = remember { PlanComptableRepository.getAuditStats() }
    val totalElements = remember { auditStats.sumOf { it.totalCount } }
    val totalClasses = remember { auditStats.size }

    // Group favorites by Class
    val favoriteElements = favorites.mapNotNull { PlanComptableRepository.getByUid(it) }
        .filter {
            if (favSearchQuery.isBlank()) true
            else it.code.contains(favSearchQuery) ||
                 it.officialFrenchTitle.contains(favSearchQuery, ignoreCase = true) ||
                 it.arabicTitle.contains(favSearchQuery)
        }
    val favsByClass = favoriteElements.groupBy { it.classNumber }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("my_account_screen"),
        contentPadding = PaddingValues(16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Screen Title
        item {
            Text(
                text = "حسابي والمفضلة (Mon Compte)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // 2. Favorites Section
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "الحسابات المفضلة (${favoriteElements.size})",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        if (favoriteElements.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    val textBuilder = StringBuilder()
                                    textBuilder.append("الدليل المحاسباتي المغربي - الحسابات المفضلة:\n\n")
                                    favoriteElements.forEach { elem ->
                                        textBuilder.append("• ${elem.code} : ${elem.officialFrenchTitle} (${elem.arabicTitle})\n")
                                    }
                                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_SUBJECT, "Plan Comptable Marocain - Favoris")
                                        putExtra(Intent.EXTRA_TEXT, textBuilder.toString())
                                    }
                                    context.startActivity(Intent.createChooser(shareIntent, "تصدير المفضلة"))
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Share, contentDescription = "تصدير المفضلة", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }

                    if (favoriteElements.isEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "لم تقم بحفظ أي حساب في المفضلة بعد. اضغط على أيقونة الإشارة المرجعية في أي حساب لحفظه هنا ومراجعته بسرعة.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                    } else {
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = favSearchQuery,
                            onValueChange = { favSearchQuery = it },
                            placeholder = { Text("تصفية المفضلة...", style = MaterialTheme.typography.bodySmall) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }
            }
        }

        // Render Favorites Grouped by Class
        favsByClass.forEach { (cNum, elements) ->
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                ) {
                    ClassBadge(classNumber = cNum, size = BadgeSize.SMALL)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "صنف $cNum (${elements.size})",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = LedgerClassColors.forClass(cNum)
                    )
                }
            }

            items(elements, key = { it.uid }) { elem ->
                AccountRow(
                    element = elem,
                    isFavorite = true,
                    hasChildren = false,
                    isExpanded = false,
                    onToggleFavorite = { viewModel.toggleFavorite(elem.uid) },
                    onClick = { viewModel.openAccountDetails(elem) },
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }

        // 3. Settings Section
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "خيارات العرض واللغة (Affichage & Langue)",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Language modes
                    listOf(
                        LanguageMode.BILINGUAL to "فرنسي أولاً ثم عربي (Bilingue - الموصى به)",
                        LanguageMode.FRENCH_ONLY to "فرنسي فقط كما في الكتيب (Français seul)",
                        LanguageMode.ARABIC_ONLY to "عربي فقط مع الترجمة الرسمية (Arabe seul)"
                    ).forEach { (mode, label) ->
                        val isSelected = languageMode == mode
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { viewModel.setLanguageMode(mode) }
                                .testTag("lang_mode_${mode.name.lowercase()}"),
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else Color.Transparent
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (isSelected) {
                                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                                } else {
                                    Spacer(modifier = Modifier.size(16.dp))
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))

                    // Theme Selection
                    Text(
                        text = "المظهر (Thème):",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            Triple(null, "تلقائي", Icons.Default.SettingsBrightness),
                            Triple(false, "فاتح", Icons.Default.LightMode),
                            Triple(true, "داكن", Icons.Default.DarkMode)
                        ).forEach { (themeSetting, label, icon) ->
                            val isSelected = isDarkTheme == themeSetting
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { viewModel.setDarkTheme(themeSetting) },
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(text = label, style = MaterialTheme.typography.labelSmall, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))

                    // Font Scale Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "تكبير حجم الخط (Police Agrandie)",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "يطبق تلقائياً بنسبة +15% على جميع الشاشات",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Switch(
                            checked = largeFont,
                            onCheckedChange = { viewModel.setLargeFont(it) },
                            modifier = Modifier.testTag("large_font_switch")
                        )
                    }
                }
            }
        }

        // 4. Data Quality & Audit Section (Calculated dynamically)
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showAuditSection = !showAuditSection },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = Color(0xFF15803D))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "جودة البيانات والتدقيق (Qualité & Audit)",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Text(
                            text = if (showAuditSection) "إخفاء ▲" else "عرض التفاصيل ▼",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "إحصائيات محسوبة مباشرة من البيانات: $totalElements عنصراً موزعة على $totalClasses أصناف رسمية متكاملة.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (showAuditSection) {
                        Spacer(modifier = Modifier.height(10.dp))
                        auditStats.forEach { stat ->
                            val classColor = LedgerClassColors.forClass(stat.classNumber)
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                                    .padding(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        ClassBadge(classNumber = stat.classNumber, size = BadgeSize.SMALL)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(text = stat.titleAr, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                                    }
                                    Text(text = "${stat.totalCount} عنصر", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = classColor)
                                }
                                Text(
                                    text = "أقسام: ${stat.rubriquesCount} | أبواب: ${stat.postesCount} | حسابات: ${stat.comptesCount} | فرعية: ${stat.sousComptesCount}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                if (stat.anomalies.isNotEmpty()) {
                                    stat.anomalies.forEach { anom ->
                                        Text(
                                            text = "⚠️ الحساب ${anom.code}: ${anom.anomalyNote ?: ""}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = AnomalyAmber
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 5. About the App & Moroccan CGNC
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "حول التطبيق والمخطط المحاسباتي المغربي",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "مرجع جيب مخصص لطلاب تسيير المقاولات والاقتصاد والمحاسبة في المغرب (OFPPT, BTS, EST, ENCG, Facultés des Sciences Économiques). تم نقل جميع الحسابات حرفياً من نص المخطط المحاسباتي المغربي (CGNC) مع شروحات بيداغوجية وأمثلة عملية وأداة تفكيك الأرقام لتسهيل الفهم والحفظ.\n\nالتطبيق يعمل بدون إنترنت 100% ويحفظ بياناتك محلياً على جهازك.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
