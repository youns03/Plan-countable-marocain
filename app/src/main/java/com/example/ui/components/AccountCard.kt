package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AccountingElement
import com.example.model.ElementLevel
import com.example.model.LanguageMode
import com.example.ui.theme.BadgeAnomalyBg
import com.example.ui.theme.BadgeAnomalyText
import com.example.ui.theme.BadgeOfficialBg
import com.example.ui.theme.BadgeOfficialText
import com.example.ui.theme.BadgePedagogicBg
import com.example.ui.theme.BadgePedagogicText
import com.example.ui.theme.ClassColors

@Composable
fun AccountCard(
    element: AccountingElement,
    languageMode: LanguageMode = LanguageMode.BILINGUAL,
    isFavorite: Boolean = false,
    hasChildren: Boolean = false,
    isExpanded: Boolean = false,
    largeFont: Boolean = false,
    onToggleExpand: (() -> Unit)? = null,
    onToggleFavorite: (() -> Unit)? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val classColor = ClassColors.forClass(element.classNumber)

    val baseFontSize = if (largeFont) 17.sp else 15.sp
    val titleFontSize = if (largeFont) 16.sp else 14.sp
    val codeFontSize = when (element.level) {
        ElementLevel.CLASSE -> if (largeFont) 22.sp else 19.sp
        ElementLevel.RUBRIQUE -> if (largeFont) 19.sp else 17.sp
        ElementLevel.POSTE -> if (largeFont) 17.sp else 15.sp
        ElementLevel.COMPTE -> if (largeFont) 16.sp else 14.sp
        ElementLevel.SOUS_COMPTE -> if (largeFont) 15.sp else 13.sp
    }

    val elevation = when (element.level) {
        ElementLevel.CLASSE -> 3.dp
        ElementLevel.RUBRIQUE -> 2.dp
        ElementLevel.POSTE -> 1.dp
        else -> 0.dp
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("account_card_${element.code}")
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (element.level) {
                ElementLevel.CLASSE -> MaterialTheme.colorScheme.surface
                ElementLevel.RUBRIQUE -> MaterialTheme.colorScheme.surface
                ElementLevel.POSTE -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ElementLevel.COMPTE -> MaterialTheme.colorScheme.surface
                ElementLevel.SOUS_COMPTE -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        border = if (element.level == ElementLevel.CLASSE || element.level == ElementLevel.RUBRIQUE) {
            androidx.compose.foundation.BorderStroke(1.dp, classColor.copy(alpha = 0.35f))
        } else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Header: Code + Level badge + Statement/Balance Pill + Bookmark + Expand Arrow
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    // Account code badge
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = classColor,
                        contentColor = Color.White
                    ) {
                        Text(
                            text = element.code,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = codeFontSize,
                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Level label (صنف / قسم / باب / حساب)
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ) {
                        Text(
                            text = "${element.level.displayNameAr} (${element.level.displayNameFr})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                        )
                    }
                }

                // Actions: Bookmark & Expand Arrow
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (onToggleFavorite != null) {
                        IconButton(
                            onClick = onToggleFavorite,
                            modifier = Modifier
                                .size(40.dp)
                                .testTag("favorite_btn_${element.code}")
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = if (isFavorite) "إزالة من المفضلة" else "إضافة للمفضلة",
                                tint = if (isFavorite) classColor else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    if (hasChildren && onToggleExpand != null) {
                        IconButton(
                            onClick = onToggleExpand,
                            modifier = Modifier
                                .size(40.dp)
                                .testTag("expand_btn_${element.code}")
                        ) {
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = if (isExpanded) "طي" else "توسيع",
                                tint = classColor
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Stacked Bilingual Display:
            // 1. French (Verbatim from official PDF)
            if (languageMode != LanguageMode.ARABIC_ONLY) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(BadgeOfficialBg.copy(alpha = 0.5f))
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = BadgeOfficialBg,
                            contentColor = BadgeOfficialText
                        ) {
                            Text(
                                text = "الكتيب الرسمي • Officiel",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        // Statement type indicator
                        Text(
                            text = element.statementType.labelFr,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = element.officialFrenchTitle,
                        fontSize = baseFontSize,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = (baseFontSize.value * 1.3).sp
                    )
                }
            }

            // 2. Arabic (Pedagogical translation and Moroccan explanation)
            if (languageMode != LanguageMode.FRENCH_ONLY) {
                if (languageMode == LanguageMode.BILINGUAL) {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(BadgePedagogicBg.copy(alpha = 0.5f))
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = BadgePedagogicBg,
                            contentColor = BadgePedagogicText
                        ) {
                            Text(
                                text = "شرح توضيحي بالعربية",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Text(
                            text = element.statementType.labelAr,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = element.arabicTitle,
                        fontSize = baseFontSize,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = (baseFontSize.value * 1.35).sp
                    )

                    if (element.shortExplanationAr.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = element.shortExplanationAr,
                            fontSize = titleFontSize,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = (titleFontSize.value * 1.35).sp
                        )
                    }
                }
            }

            // Anomaly note banner (if preserved from PDF)
            if (element.anomalyNote != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(BadgeAnomalyBg)
                        .border(1.dp, BadgeAnomalyText.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "تنبيه",
                        tint = BadgeAnomalyText,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = element.anomalyNote,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = BadgeAnomalyText,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}
