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
)

object PlanComptableRepository {

    val allElements: List<AccountingElement> by lazy {
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

    private val elementsByCodeMap: Map<String, AccountingElement> by lazy {
        allElements.associateBy { it.code }
    }

    fun getElementsByClass(classNumber: Int): List<AccountingElement> {
        return allElements.filter { it.classNumber == classNumber }
    }

    fun getElementByCode(code: String): AccountingElement? {
        return elementsByCodeMap[code.trim()]
    }

    fun getChildren(parentCode: String): List<AccountingElement> {
        val cleanParent = parentCode.trim()
        return allElements.filter { it.parentCode == cleanParent }
    }

    fun getBreadcrumbs(code: String): List<AccountingElement> {
        val crumbs = mutableListOf<AccountingElement>()
        var current = getElementByCode(code)
        while (current != null) {
            crumbs.add(0, current)
            current = current.parentCode?.let { getElementByCode(it) }
        }
        return crumbs
    }

    fun normalizeFrench(input: String): String {
        val nfd = Normalizer.normalize(input.lowercase(Locale.FRENCH), Normalizer.Form.NFD)
        return nfd.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "").trim()
    }

    fun normalizeArabic(input: String): String {
        var str = input.trim()
        // Remove Arabic diacritics (Harakat)
        str = str.replace("[\\u064B-\\u065F\\u0670]".toRegex(), "")
        // Normalize Alif forms
        str = str.replace("[أإآ]".toRegex(), "ا")
        // Normalize Ta Marbuta and Ya
        str = str.replace("ة", "ه")
        str = str.replace("ى", "ي")
        return str.lowercase(Locale.ROOT)
    }

    fun search(query: String, filterClass: Int? = null): List<AccountingElement> {
        val q = query.trim()
        if (q.isEmpty()) return emptyList()

        val normQFr = normalizeFrench(q)
        val normQAr = normalizeArabic(q)
        val isNumeric = q.all { it.isDigit() || it == '/' }

        val baseList = if (filterClass != null) getElementsByClass(filterClass) else allElements

        return baseList.filter { elem ->
            if (isNumeric) {
                elem.code.startsWith(q) || elem.code.contains(q)
            } else {
                val elemFr = normalizeFrench(elem.officialFrenchTitle)
                val elemAr = normalizeArabic(elem.arabicTitle)
                val expFr = normalizeFrench(elem.shortExplanationFr)
                val expAr = normalizeArabic(elem.shortExplanationAr)

                elemFr.contains(normQFr) ||
                elemAr.contains(normQAr) ||
                expFr.contains(normQFr) ||
                expAr.contains(normQAr)
            }
        }.sortedWith(
            compareBy<AccountingElement> {
                // Exact code match first
                if (it.code == q) 0
                else if (it.code.startsWith(q)) 1
                else 2
            }.thenBy { it.code.length }
             .thenBy { it.code }
        )
    }

    fun deconstructCode(code: String): DeconstructionResult {
        val clean = code.trim().filter { it.isDigit() }
        if (clean.isEmpty()) {
            return DeconstructionResult(inputCode = code, steps = emptyList())
        }

        val steps = mutableListOf<DeconstructedDigit>()
        val len = clean.length

        // Step 1: Classe (1 digit)
        if (len >= 1) {
            val c1 = clean.substring(0, 1)
            val elem1 = getElementByCode(c1)
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
            val elem2 = getElementByCode(c2)
            steps.add(
                DeconstructedDigit(
                    digits = c2,
                    levelNameAr = "القسم (Rubrique)",
                    levelNameFr = "Rubrique",
                    element = elem2,
                    roleNoteAr = "الخانة الثانية تحدد التقسيم الوظيفي داخل الصنف (مثلاً 34 = ديون الزبائن والأصول المتداولة)."
                )
            )
        }

        // Step 3: Poste (3 digits)
        if (len >= 3) {
            val c3 = clean.substring(0, 3)
            val elem3 = getElementByCode(c3)
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
            val elem4 = getElementByCode(c4)
            steps.add(
                DeconstructedDigit(
                    digits = c4,
                    levelNameAr = "الحساب الرئيسي (Compte)",
                    levelNameFr = "Compte",
                    element = elem4,
                    roleNoteAr = "الخانة الرابعة هي الوحدة المحاسبية الأساسية المستخدمة في القيود اليومية في الدفتر اليومي."
                )
            )
        }

        // Step 5: Sous-compte (5 digits or more)
        if (len >= 5) {
            val c5 = clean
            val elem5 = getElementByCode(c5)
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

        // Special rule check
        var specialNote: String? = null
        if (clean.length >= 2 && clean.startsWith("2") && clean[1] == '8') {
            specialNote = "قاعدة خاصة بالرقم 8: في الصنف 2، وجود الرقم 8 في الخانة الثانية (28) يعني دائماً حساب استهلاك (Amortissement) للأصل المقابل."
        } else if (clean.length >= 2 && clean[1] == '9') {
            specialNote = "قاعدة خاصة بالرقم 9: وجود الرقم 9 في الخانة الثانية (29, 39, 49) يعني مؤونة لتدهور قيمة الحسابات المقابلة (Provisions pour dépréciation)."
        } else if (clean.length >= 3 && clean.startsWith("6") && clean[2] == '9') {
            specialNote = "قاعدة خاصة بالرقم 9: في الصنف 6، وجود الرقم 9 في الخانة الثالثة (619, 639, 659) يعني مخصصات الاستهلاك والمؤونات (Dotations)."
        } else if (clean.length >= 3 && clean.startsWith("7") && clean[2] == '9') {
            specialNote = "قاعدة خاصة بالرقم 9: في الصنف 7، وجود الرقم 9 في الخانة الثالثة (719, 739, 759) يعني استرجاع المؤونات السابقة (Reprises)."
        } else if (clean.length >= 4 && clean[3] == '9' && (clean.startsWith("6119") || clean.startsWith("7119") || clean.startsWith("6129") || clean.startsWith("7129"))) {
            specialNote = "قاعدة الرقم 9 في الخانة الرابعة: يعني التخفيضات التجارية الممنوحة أو المستفاد منها (RRR) ويسجل في الاتجاه المعاكس للحساب الأصلي."
        }

        return DeconstructionResult(
            inputCode = code,
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
                titleFr = classElem?.officialFrenchTitle ?: "Classe $cNum",
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
