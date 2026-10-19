package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Primary Brand Colors
val PrimaryBlue = Color(0xFF1E3A8A)
val PrimaryBlueDark = Color(0xFF93C5FD)
val SecondaryAmber = Color(0xFFD97706)
val SecondaryAmberDark = Color(0xFFFCD34D)
val SurfaceLight = Color(0xFFF8FAFC)
val SurfaceDark = Color(0xFF0F172A)
val CardLight = Color(0xFFFFFFFF)
val CardDark = Color(0xFF1E293B)

// The 10 Moroccan PCM Accounting Class Colors
object ClassColors {
    val Class1 = Color(0xFF1E3A8A) // Financement permanent - Deep Navy
    val Class2 = Color(0xFF0D9488) // Actif immobilisé - Teal
    val Class3 = Color(0xFF2563EB) // Actif circulant - Blue
    val Class4 = Color(0xFFEA580C) // Passif circulant - Orange
    val Class5 = Color(0xFF16A34A) // Trésorerie - Green
    val Class6 = Color(0xFFDC2626) // Charges - Crimson Red
    val Class7 = Color(0xFF059669) // Produits - Emerald Green
    val Class8 = Color(0xFF7C3AED) // Résultats - Purple
    val Class9 = Color(0xFFB45309) // Analytique - Amber/Brown
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
        else -> PrimaryBlue
    }
}

// Indicator and Badge Colors
val BadgeOfficialText = Color(0xFF334155)
val BadgeOfficialBg = Color(0xFFF1F5F9)
val BadgePedagogicText = Color(0xFF1D4ED8)
val BadgePedagogicBg = Color(0xFFEFF6FF)
val BadgeAnomalyText = Color(0xFFC2410C)
val BadgeAnomalyBg = Color(0xFFFFF7ED)

val DebitColor = Color(0xFF0284C7)
val CreditColor = Color(0xFF16A34A)
val VariableColor = Color(0xFF7C3AED)
