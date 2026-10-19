package com.example.data

import com.example.model.AccountingElement
import com.example.model.ElementLevel
import java.text.Normalizer
import java.util.Locale

data class ClassAuditStats(
    val classNumber: Int,
    val titleFr: String,
    val titleAr: String,
    val rubriquesCount: Int,
    val postesCount: Int,
    val comptesCount: Int,
    val sousComptesCount: Int,
    val totalCount: Int,
    val anomalies: List<AccountingElement>
)

data class DeconstructedDigit(
    val digits: String,
    val levelNameAr: String,
    val levelNameFr: String,
    val element: AccountingElement?,
    val roleNoteAr: String
)

data class DeconstructionResult(
    val inputCode: String,
    val steps: List<DeconstructedDigit>,
    val specialRuleNoteAr: String? = null
) {
    val detectedClassNumber: Int? get() = inputCode.firstOrNull()?.digitToIntOrNull()
}

object PlanComptableRepository {

    val allElements: List<AccountingElement> by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        Class1Data.elements +
        Class2Data.elements +
        Class3Data.elements +
        Class4Data.elements +
        Class5Data.elements +
        Class6Data.elements +
        Class7Data.elements +
        Class8Data.elements +
        Class9Data.elements +
        Class0Data.elements
    }

    private val elementsByUidMap: Map<String, AccountingElement> by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        allElements.associateBy { it.uid }
    }

    private val elementsByClassMap: Map<Int, List<AccountingElement>> by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        allElements.groupBy { it.classNumber }
    }

    private val childrenByParentUidMap: Map<String, List<AccountingElement>> by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        allElements.filter { it.parentUid != null }.groupBy { it.parentUid!! }
    }

    fun getElementsByClass(classNumber: Int): List<AccountingElement> {
        return elementsByClassMap[classNumber] ?: emptyList()
    }

    fun getElementByUid(uid: String): AccountingElement? {
        return elementsByUidMap[uid.trim()]
    }

    fun getByUid(uid: String): AccountingElement? = getElementByUid(uid)

    fun getByCodeInClass(code: String, classNumber: Int): AccountingElement? {
        return elementsByUidMap["$classNumber:${code.trim()}"]
    }

    fun getElementByCode(code: String): AccountingElement? {
        val clean = code.trim()
        val naturalClass = clean.firstOrNull()?.digitToIntOrNull()
        if (naturalClass != null) {
            val candidate = elementsByUidMap["$naturalClass:$clean"]
            if (candidate != null) return candidate
        }
        return allElements.firstOrNull { it.code == clean }
    }

    fun getChildren(parentUid: String): List<AccountingElement> {
        return childrenByParentUidMap[parentUid.trim()] ?: emptyList()
    }

    fun getBreadcrumbs(uid: String): List<AccountingElement> {
        val crumbs = mutableListOf<AccountingElement>()
        var current = getElementByUid(uid)
        while (current != null) {
            crumbs.add(0, current)
            current = current.parentUid?.let { getElementByUid(it) }
        }
        return crumbs
    }

    fun convertArabicIndicNumerals(input: String): String {
        val arabicIndic = "٠١٢٣٤٥٦٧٨٩"
        val easternArabic = "۰۱۲۳۴۵۶۷۸۹"
        val sb = StringBuilder()
        for (ch in input) {
            val idx1 = arabicIndic.indexOf(ch)
            val idx2 = easternArabic.indexOf(ch)
            if (idx1 >= 0) sb.append(idx1)
            else if (idx2 >= 0) sb.append(idx2)
            else sb.append(ch)
        }
        return sb.toString()
    }

    fun normalizeFrench(input: String): String {
        val nfd = Normalizer.normalize(input.lowercase(Locale.FRENCH), Normalizer.Form.NFD)
        return nfd.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "").trim()
    }

    fun normalizeArabic(input: String): String {
        var str = input.trim()
        str = str.replace("[\\u064B-\\u065F\\u0670]".toRegex(), "")
        str = str.replace("ـ", "")
        str = str.replace("[أإآ]".toRegex(), "ا")
        str = str.replace("ة", "ه")
        str = str.replace("ى", "ي")
        str = str.replace("[ؤئء]".toRegex(), "ء")
        return str.lowercase(Locale.ROOT)
    }

    fun search(rawQuery: String, filterClass: Int? = null): List<AccountingElement> {
        val converted = convertArabicIndicNumerals(rawQuery).trim()
        if (converted.isEmpty()) return emptyList()

        val normQFr = normalizeFrench(converted)
        val normQAr = normalizeArabic(converted)
        val isNumeric = converted.all { it.isDigit() || it == '/' }

        val baseList = if (filterClass != null) getElementsByClass(filterClass) else allElements

        return baseList.mapNotNull { elem ->
            val rank = calculateMatchRank(elem, converted, isNumeric, normQFr, normQAr)
            if (rank != null) Pair(elem, rank) else null
        }.sortedWith(
            compareBy<Pair<AccountingElement, Int>> { it.second }
                .thenBy { it.first.code.length }
                .thenBy { it.first.classNumber }
                .thenBy { it.first.code }
        ).map { it.first }
    }

    private fun calculateMatchRank(
        elem: AccountingElement,
        convertedQuery: String,
        isNumeric: Boolean,
        normQFr: String,
        normQAr: String
    ): Int? {
        if (isNumeric) {
            if (elem.code == convertedQuery) return 0 // exact code match
            if (elem.code.startsWith(convertedQuery)) return 1 // prefix match
            if (convertedQuery.length >= 3 && elem.code.contains(convertedQuery)) return 4 // substring match for 3+ digits
            return null
        }

        // Text search ranking
        val frTitle = elem.normFr
        val arTitle = elem.normAr

        // Exact title match
        if (frTitle == normQFr || arTitle == normQAr) return 0

        // Word match in title
        val frWords = frTitle.split(" ", "-", "'")
        val arWords = arTitle.split(" ")
        if (frWords.any { it == normQFr } || arWords.any { it == normQAr }) return 1

        // Starts with in title
        if (frTitle.startsWith(normQFr) || arTitle.startsWith(normQAr)) return 2

        // Contains in title
        if (frTitle.contains(normQFr) || arTitle.contains(normQAr)) return 3

        // Contains in short explanation
        if (elem.normShortExpFr.contains(normQFr) || elem.normShortExpAr.contains(normQAr)) return 5

        return null
    }

    fun deconstructCode(rawCode: String): DeconstructionResult {
        val converted = convertArabicIndicNumerals(rawCode)
        val clean = converted.trim().filter { it.isDigit() }
        if (clean.isEmpty()) {
            return DeconstructionResult(inputCode = rawCode, steps = emptyList())
        }

        val steps = mutableListOf<DeconstructedDigit>()
        val len = clean.length
        val classNum = clean.first().digitToInt()

        // Step 1: Classe (1 digit)
        if (len >= 1) {
            val c1 = clean.substring(0, 1)
            val elem1 = getByCodeInClass(c1, classNum)
            steps.add(
                DeconstructedDigit(
                    digits = c1,
                    levelNameAr = "الصنف (Classe)",
                    levelNameFr = "Classe",
                    element = elem1,
                    roleNoteAr = "الخانة الأولى تحدد الإطار العام للحساب وطبيعته (الميزانية أو التسيير أو النتائج)."
                )
            )
        }

        // Step 2: Rubrique (2 digits)
        if (len >= 2) {
            val c2 = clean.substring(0, 2)
            val elem2 = getByCodeInClass(c2, classNum)
            steps.add(
                DeconstructedDigit(
                    digits = c2,
                    levelNameAr = "القسم (Rubrique)",
                    levelNameFr = "Rubrique",
                    element = elem2,
                    roleNoteAr = "الخانة الثانية تحدد التقسيم الوظيفي داخل الصنف."
                )
            )
        }

        // Step 3: Poste (3 digits)
        if (len >= 3) {
            val c3 = clean.substring(0, 3)
            val elem3 = getByCodeInClass(c3, classNum)
            steps.add(
                DeconstructedDigit(
                    digits = c3,
                    levelNameAr = "الباب (Poste)",
                    levelNameFr = "Poste",
                    element = elem3,
                    roleNoteAr = "الخانة الثالثة تجمع الحسابات المتشابهة في سطر واحد داخل القوائم المالية."
                )
            )
        }

        // Step 4: Compte (4 digits)
        if (len >= 4) {
            val c4 = clean.substring(0, 4)
            val elem4 = getByCodeInClass(c4, classNum)
            steps.add(
                DeconstructedDigit(
                    digits = c4,
                    levelNameAr = "الحساب الرئيسي (Compte)",
                    levelNameFr = "Compte",
                    element = elem4,
                    roleNoteAr = "الخانة الرابعة هي الوحدة المحاسبية الأساسية المستخدمة في القيود اليومية."
                )
            )
        }

        // Step 5: Sous-compte (5 digits or more)
        if (len >= 5) {
            val c5 = clean
            val elem5 = getByCodeInClass(c5, classNum)
            steps.add(
                DeconstructedDigit(
                    digits = c5,
                    levelNameAr = "الحساب الفرعي (Sous-compte)",
                    levelNameFr = "Sous-compte",
                    element = elem5,
                    roleNoteAr = "الخانة الخامسة وما بعدها تفصّل الحساب الرئيسي لمزيد من الدقة التحليلية."
                )
            )
        }

        // Special rule checks with strict class scoping
        var specialNote: String? = null
        if (clean.length >= 2 && classNum == 2 && clean[1] == '8') {
            specialNote = "قاعدة خاصة بالرقم 8: في الصنف 2، وجود الرقم 8 في الخانة الثانية (28) يعني دائماً حساب استهلاك (Amortissement) للأصل المقابل."
        } else if (clean.length >= 2 && (classNum in 2..4) && clean[1] == '9') {
            specialNote = "قاعدة خاصة بالرقم 9: في الأصناف 2 و 3 و 4، وجود الرقم 9 في الخانة الثانية (29, 39, 49) يعني مؤونة لتدهور قيمة الحسابات المقابلة (Provisions pour dépréciation)."
        } else if (clean.length >= 3 && classNum == 6 && clean[2] == '9') {
            specialNote = "قاعدة خاصة بالرقم 9: في الصنف 6، وجود الرقم 9 في الخانة الثالثة (619, 639, 659) يعني مخصصات الاستهلاك والمؤونات (Dotations)."
        } else if (clean.length >= 3 && classNum == 7 && clean[2] == '9') {
            specialNote = "قاعدة خاصة بالرقم 9: في الصنف 7، وجود الرقم 9 في الخانة الثالثة (719, 739, 759) يعني استرجاع المؤونات السابقة (Reprises)."
        } else if (clean.length >= 4 && clean[3] == '9' && (clean.startsWith("6119") || clean.startsWith("7119") || clean.startsWith("6129") || clean.startsWith("7129"))) {
            specialNote = "قاعدة الرقم 9 في الخانة الرابعة: يعني التخفيضات التجارية الممنوحة أو المستفاد منها (RRR) ويسجل في الاتجاه المعاكس للحساب الأصلي."
        }

        return DeconstructionResult(
            inputCode = rawCode,
            steps = steps,
            specialRuleNoteAr = specialNote
        )
    }

    fun getAuditStats(): List<ClassAuditStats> {
        val classNumbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
        return classNumbers.map { cNum ->
            val list = getElementsByClass(cNum)
            val rubriques = list.count { it.level == ElementLevel.RUBRIQUE }
            val postes = list.count { it.level == ElementLevel.POSTE }
            val comptes = list.count { it.level == ElementLevel.COMPTE }
            val sousComptes = list.count { it.level == ElementLevel.SOUS_COMPTE }
            val anomalies = list.filter { it.anomalyNote != null }
            val classElem = list.firstOrNull { it.level == ElementLevel.CLASSE }

            ClassAuditStats(
                classNumber = cNum,
                titleFr = classElem?.officialFrenchTitle ?: "CLASSE $cNum",
                titleAr = classElem?.arabicTitle ?: "الصنف $cNum",
                rubriquesCount = rubriques,
                postesCount = postes,
                comptesCount = comptes,
                sousComptesCount = sousComptes,
                totalCount = list.size,
                anomalies = anomalies
            )
        }
    }
}
