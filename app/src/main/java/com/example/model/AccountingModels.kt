package com.example.model

enum class ElementLevel(val displayNameAr: String, val displayNameFr: String) {
    CLASSE("صنف", "Classe"),
    RUBRIQUE("قسم", "Rubrique"),
    POSTE("باب", "Poste"),
    COMPTE("حساب رئيسي", "Compte"),
    SOUS_COMPTE("حساب فرعي", "Sous-compte")
}

enum class StatementType(val labelAr: String, val labelFr: String) {
    BILAN_ACTIF("الميزانية - الأصول", "Bilan - Actif"),
    BILAN_PASSIF("الميزانية - الخصوم", "Bilan - Passif"),
    CPC_CHARGES("قائمة التكاليف (CPC)", "C.P.C. - Charges"),
    CPC_PRODUITS("قائمة العائدات (CPC)", "C.P.C. - Produits"),
    RESULTATS("حسابات النتائج", "Comptes de Résultats"),
    ANALYTIQUE("محاسبة تحليلية", "Comptabilité Analytique"),
    SPECIAL("حسابات خاصة / نظامية", "Comptes Spéciaux / D'ordre")
}

enum class BalanceType(val labelAr: String, val labelFr: String) {
    DEBIT("مدين دائماً (Débiteur)", "Débiteur"),
    CREDIT("دائن دائماً (Créditeur)", "Créditeur"),
    VARIABLE("متغير بحسب العملية", "Variable"),
    SANS_SOLDE("حساب تسوية / بدون رصيد", "Sans solde")
}

data class AcronymInfo(
    val acronym: String,
    val fullNameFr: String,
    val fullNameAr: String,
    val explanationAr: String
)

data class AccountingElement(
    val code: String,
    val level: ElementLevel,
    val officialFrenchTitle: String,
    val arabicTitle: String,
    val classNumber: Int,
    val parentCode: String? = null,
    val statementType: StatementType,
    val normalBalance: BalanceType = BalanceType.VARIABLE,
    val shortExplanationAr: String,
    val shortExplanationFr: String,
    val detailedExplanationAr: String? = null,
    val detailedExplanationFr: String? = null,
    val practicalExample: String? = null,
    val anomalyNote: String? = null,
    val acronyms: List<AcronymInfo> = emptyList()
)

data class JournalEntryLine(
    val accountCode: String,
    val accountTitleFr: String,
    val accountTitleAr: String,
    val debitAmount: Double? = null,
    val creditAmount: Double? = null,
    val note: String? = null
)

data class JournalEntry(
    val id: String,
    val titleAr: String,
    val titleFr: String,
    val descriptionAr: String,
    val dateExample: String,
    val documentRef: String,
    val lines: List<JournalEntryLine>,
    val pedagogicalExplanationAr: String
)

data class LexiconTerm(
    val termFr: String,
    val termAr: String,
    val phoneticAr: String,
    val definitionAr: String,
    val definitionFr: String,
    val category: String
)

data class ComparisonRow(
    val criterionAr: String,
    val conceptAValue: String,
    val conceptBValue: String
)

data class ComparisonItem(
    val titleAr: String,
    val titleFr: String,
    val conceptAName: String,
    val conceptBName: String,
    val summaryAr: String,
    val rows: List<ComparisonRow>
)

data class QuizQuestion(
    val id: Int,
    val classNumber: Int,
    val questionAr: String,
    val questionFr: String,
    val optionsAr: List<String>,
    val correctIndex: Int,
    val explanationAr: String
)

enum class LanguageMode {
    BILINGUAL,
    FRENCH_ONLY,
    ARABIC_ONLY
}
