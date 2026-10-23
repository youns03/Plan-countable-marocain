package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LedgerClassColors
import com.example.ui.theme.MonoFontFamily

enum class BadgeSize(val boxSize: Dp, val fontSize: TextUnit, val cornerRadius: Dp) {
    SMALL(28.dp, 12.sp, 6.dp),
    MEDIUM(38.dp, 16.sp, 8.dp),
    LARGE(48.dp, 22.sp, 12.dp)
}

@Composable
fun ClassBadge(
    classNumber: Int,
    size: BadgeSize = BadgeSize.MEDIUM,
    modifier: Modifier = Modifier
) {
    val color = LedgerClassColors.forClass(classNumber)

    Surface(
        shape = RoundedCornerShape(size.cornerRadius),
        color = color,
        contentColor = Color.White,
        shadowElevation = if (size == BadgeSize.SMALL) 1.dp else 3.dp,
        tonalElevation = 1.dp,
        modifier = modifier.size(size.boxSize)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.22f),
                            color,
                            color.copy(alpha = 0.94f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$classNumber",
                fontFamily = MonoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = size.fontSize
            )
        }
    }
}
