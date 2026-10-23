package com.example.model

import androidx.compose.runtime.Immutable
import java.text.Normalizer
import java.util.Locale

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

@Immutable
data class AcronymInfo(
    val acronym: String,
    val fullNameFr: String,
    val fullNameAr: String,
    val explanationAr: String
)

@Immutable
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
    val acronyms: List<AcronymInfo> = emptyList(),
    val customParentUid: String? = null
) {
    // Unique identifier across the entire chart of accounts: "$classNumber:$code"
    val uid: String get() = "$classNumber:$code"

    // Parent UID resolves hierarchically within the same class
    val parentUid: String? get() = customParentUid ?: parentCode?.let { "$classNumber:$it" }

    // Pre-normalized text for lightning-fast search without runtime overhead
    val normFr: String by lazy(LazyThreadSafetyMode.NONE) {
        val nfd = Normalizer.normalize(officialFrenchTitle.lowercase(Locale.FRENCH), Normalizer.Form.NFD)
        nfd.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "").trim()
    }

    val normAr: String by lazy(LazyThreadSafetyMode.NONE) {
        var str = arabicTitle.trim()
        str = str.replace("[\\u064B-\\u065F\\u0670]".toRegex(), "") // remove Harakat
        str = str.replace("ـ", "") // remove Tatweel
        str = str.replace("[أإآ]".toRegex(), "ا")
        str = str.replace("ة", "ه")
        str = str.replace("ى", "ي")
        str = str.replace("[ؤئء]".toRegex(), "ء")
        str.lowercase(Locale.ROOT)
    }

    val normShortExpFr: String by lazy(LazyThreadSafetyMode.NONE) {
        val nfd = Normalizer.normalize(shortExplanationFr.lowercase(Locale.FRENCH), Normalizer.Form.NFD)
        nfd.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "").trim()
    }

    val normShortExpAr: String by lazy(LazyThreadSafetyMode.NONE) {
        var str = shortExplanationAr.trim()
        str = str.replace("[\\u064B-\\u065F\\u0670]".toRegex(), "")
        str = str.replace("ـ", "")
        str = str.replace("[أإآ]".toRegex(), "ا")
        str = str.replace("ة", "ه")
        str = str.replace("ى", "ي")
        str.lowercase(Locale.ROOT)
    }
}

@Immutable
data class JournalEntryLine(
    val accountCode: String,
    val accountTitleFr: String,
    val accountTitleAr: String,
    val debitAmount: Double? = null,
    val creditAmount: Double? = null,
    val note: String? = null
)

@Immutable
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

enum class ExerciseType {
    BALANCE_SHEET,
    FLOW_ANALYSIS,
    ACCOUNT_OPERATION,
    DOUBLE_ENTRY,
    OPENING_CLOSING
}

@Immutable
data class SourceExercise(
    val id: String,
    val sourceDocument: String,
    val sourcePage: Int,
    val sourceOrdinal: String,
    val titleFr: String,
    val type: ExerciseType,
    val statementFr: String,
    val taskFr: String,
    val theoryTopics: List<String>,
    val hasVerifiedSolution: Boolean = false
)

@Immutable
data class LexiconTerm(
    val termFr: String,
    val termAr: String,
    val phoneticAr: String,
    val definitionAr: String,
    val definitionFr: String,
    val category: String
)

@Immutable
data class ComparisonRow(
    val criterionAr: String,
    val conceptAValue: String,
    val conceptBValue: String
)

@Immutable
data class ComparisonItem(
    val titleAr: String,
    val titleFr: String,
    val conceptAName: String,
    val conceptBName: String,
    val summaryAr: String,
    val rows: List<ComparisonRow>
)

@Immutable
data class QuizQuestion(
    val id: Int,
    val classNumber: Int,
    val questionAr: String,
    val questionFr: String,
    val optionsAr: List<String>,
    val correctIndex: Int,
    val explanationAr: String
)

@Immutable
data class LearningLesson(
    val id: String,
    val stageNumber: Int,
    val titleAr: String,
    val titleFr: String,
    val objectiveAr: String,
    val objectiveFr: String,
    val explanationAr: String,
    val explanationFr: String,
    val terms: List<Pair<String, String>>,
    val exampleFr: String,
    val exampleAr: String,
    val reviewQuestionsAr: List<String>,
    val sourceLabel: String,
    val sourceUrl: String? = null,
    val relatedAccountCodes: List<String> = emptyList()
)

enum class LanguageMode {
    BILINGUAL,
    FRENCH_ONLY,
    ARABIC_ONLY
}
