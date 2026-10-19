package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = LedgerDarkPrimary,
    onPrimary = Color(0xFF0F172A),
    primaryContainer = Color(0xFF1E3A8A),
    onPrimaryContainer = Color(0xFFDBEAFE),
    secondary = LedgerClassColors.Class9,
    onSecondary = Color(0xFF451A03),
    secondaryContainer = Color(0xFF33200A),
    onSecondaryContainer = Color(0xFFFEF3C7),
    background = LedgerDarkBackground,
    onBackground = LedgerDarkInk,
    surface = LedgerDarkSurface,
    onSurface = LedgerDarkInk,
    surfaceVariant = LedgerDarkSurfaceVariant,
    onSurfaceVariant = LedgerDarkSecondary,
    outline = LedgerDarkBorder,
    outlineVariant = Color(0xFF384351)
)

private val LightColorScheme = lightColorScheme(
    primary = LedgerLightPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDBEAFE),
    onPrimaryContainer = Color(0xFF1E3A8A),
    secondary = LedgerClassColors.Class9,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFEF3C7),
    onSecondaryContainer = Color(0xFF78350F),
    background = LedgerLightBackground,
    onBackground = LedgerLightInk,
    surface = LedgerLightSurface,
    onSurface = LedgerLightInk,
    surfaceVariant = LedgerLightSurfaceVariant,
    onSurfaceVariant = LedgerLightSecondary,
    outline = LedgerLightBorder,
    outlineVariant = Color(0xFFD6CEBA)
)

@Composable
fun PlanComptableTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    PlanComptableTheme(darkTheme = darkTheme, content = content)
}
