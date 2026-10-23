package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AccountingElement
import com.example.model.ElementLevel
import com.example.model.LanguageMode
import com.example.ui.theme.AnomalyAmber
import com.example.ui.theme.LedgerClassColors
import com.example.ui.theme.LocalLanguageMode
import com.example.ui.theme.MonoFontFamily

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountRow(
    element: AccountingElement,
    isFavorite: Boolean,
    hasChildren: Boolean,
    isExpanded: Boolean,
    depth: Int = 0,
    highlightQuery: String? = null,
    onToggleExpand: (() -> Unit)? = null,
    onToggleFavorite: (() -> Unit)? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val languageMode = LocalLanguageMode.current
    val classColor = LedgerClassColors.forClass(element.classNumber)
    val rotationState by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f, label = "rotate")

    val indentWidth = (depth * 14).dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(if (languageMode == LanguageMode.BILINGUAL) 64.dp else 54.dp)
            .testTag("account_row_${element.uid}")
            .clip(RoundedCornerShape(12.dp))
            .background(
                when (element.level) {
                    ElementLevel.RUBRIQUE -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.62f)
                    ElementLevel.POSTE -> MaterialTheme.colorScheme.surface
                    else -> MaterialTheme.colorScheme.surface
                }
            )
            .combinedClickable(
                role = Role.Button,
                onClick = onClick,
                onLongClick = onToggleFavorite
            )
            .padding(end = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Tree Indentation Guide Line
        if (depth > 0) {
            Spacer(modifier = Modifier.width(indentWidth))
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .fillMaxHeight(0.7f)
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.6f))
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        // 4dp Class Color Accent Bar
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight(0.8f)
                .clip(RoundedCornerShape(2.dp))
                .background(classColor)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Account Number in Monospace LTR
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = classColor.copy(alpha = 0.12f),
                contentColor = classColor
            ) {
                Text(
                    text = element.code,
                    fontFamily = MonoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (element.level == ElementLevel.RUBRIQUE) 15.sp else 13.sp,
                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Titles Area
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            // Line 1: French Title (Verbatim from PDF)
            if (languageMode != LanguageMode.ARABIC_ONLY) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Text(
                        text = element.officialFrenchTitle,
                        style = if (element.level == ElementLevel.RUBRIQUE) {
                            MaterialTheme.typography.titleSmall
                        } else {
                            MaterialTheme.typography.bodyMedium
                        },
                        fontWeight = if (element.level == ElementLevel.RUBRIQUE || element.level == ElementLevel.POSTE) FontWeight.SemiBold else FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Line 2: Arabic Translation
            if (languageMode != LanguageMode.FRENCH_ONLY) {
                Text(
                    text = element.arabicTitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Warning Icon if Anomaly
        if (element.anomalyNote != null) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "رقم غير منسجم في الكتيب",
                tint = AnomalyAmber,
                modifier = Modifier
                    .size(16.dp)
                    .padding(end = 4.dp)
            )
        }

        // Actions: Favorite and Expand Chevron
        if (onToggleFavorite != null) {
            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier
                    .size(40.dp)
                    .testTag("row_fav_${element.uid}")
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = if (isFavorite) "في المفضلة" else "حفظ بالمفضلة",
                    tint = if (isFavorite) classColor else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        if (hasChildren && onToggleExpand != null) {
            IconButton(
                onClick = onToggleExpand,
                modifier = Modifier
                    .size(40.dp)
                    .testTag("row_expand_${element.uid}")
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "طي" else "توسيع",
                    tint = classColor,
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(rotationState)
                )
            }
        } else {
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}
