package com.example.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.example.model.LanguageMode

// -------------------------------------------------------------
// Modern Ledger ("دفتر الحسابات الحديث") Design Tokens
// -------------------------------------------------------------

// Light Theme Palette (Warm ledger paper & crisp ink)
val LedgerLightBackground = Color(0xFFF7F3EA)
val LedgerLightSurface = Color(0xFFFFFDF8)
val LedgerLightSurfaceVariant = Color(0xFFEFE9DB)
val LedgerLightInk = Color(0xFF1B2430)
val LedgerLightSecondary = Color(0xFF5B6573)
val LedgerLightBorder = Color(0xFFE3DCCB)
val LedgerLightPrimary = Color(0xFF1E3A8A)

// Dark Theme Palette (Deep blueprint charcoal & warm bone ink)
val LedgerDarkBackground = Color(0xFF12161C)
val LedgerDarkSurface = Color(0xFF1A2028)
val LedgerDarkSurfaceVariant = Color(0xFF242C37)
val LedgerDarkInk = Color(0xFFEDE8DC)
val LedgerDarkSecondary = Color(0xFF94A3B8)
val LedgerDarkBorder = Color(0xFF2B333D)
val LedgerDarkPrimary = Color(0xFF93C5FD)

// Semantic Financial Indicators (4.5:1 WCAG AA compliant)
val DebitBlue = Color(0xFF0369A1)
val CreditGreen = Color(0xFF15803D)
val AnomalyAmber = Color(0xFFB45309)
val AnomalyAmberBgLight = Color(0xFFFFFBEB)
val AnomalyAmberBgDark = Color(0xFF2E2009)

// 10 Accounting Class Distinct Colors (Subtle 4dp accents & tags)
object LedgerClassColors {
    val Class1 = Color(0xFF1E3A8A) // Financement permanent - Navy
    val Class2 = Color(0xFF0D9488) // Actif immobilisé - Teal
    val Class3 = Color(0xFF2563EB) // Actif circulant - Blue
    val Class4 = Color(0xFFEA580C) // Passif circulant - Warm Orange
    val Class5 = Color(0xFF16A34A) // Trésorerie - Green
    val Class6 = Color(0xFFDC2626) // Charges - Crimson
    val Class7 = Color(0xFF059669) // Produits - Emerald
    val Class8 = Color(0xFF7C3AED) // Résultats - Purple
    val Class9 = Color(0xFFB45309) // Analytique - Amber
    val Class0 = Color(0xFF475569) // Spéciaux - Slate Grey

    fun forClass(classNumber: Int): Color = when (classNumber) {
        1 -> Class1
        2 -> Class2
        3 -> Class3
        4 -> Class4
        5 -> Class5
        6 -> Class6
        7 -> Class7
        8 -> Class8
        9 -> Class9
        0 -> Class0
        else -> Class1
    }
}

// CompositionLocals for Global App State
val LocalLanguageMode = staticCompositionLocalOf { LanguageMode.BILINGUAL }
