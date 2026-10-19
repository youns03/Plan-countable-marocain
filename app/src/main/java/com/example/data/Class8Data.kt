package com.example.data

import com.example.model.AccountingElement
import com.example.model.BalanceType
import com.example.model.ElementLevel
import com.example.model.StatementType

object Class8Data {
    val elements: List<AccountingElement> = listOf(
        // CLASSE 8
        AccountingElement(
            code = "8",
            level = ElementLevel.CLASSE,
            officialFrenchTitle = "COMPTES DE RESULTATS",
            arabicTitle = "حسابات النتائج (جدول أرصدة التسيير ESG)",
            classNumber = 8,
            statementType = StatementType.RESULTATS,
            normalBalance = BalanceType.VARIABLE,
            shortExplanationAr = "يضم الحسابات المستعملة في نهاية السنة لحساب المؤشرات المتتالية للأرباح (جدول أرصدة التسيير ESG): الهامش التجاري الإجمالي، القيمة المضافة، فائض الاستغلال الإجمالي، ونتيجة السنة الصافية.",
            shortExplanationFr = "Comptes de regroupement permettant d'établir l'État des Soldes de Gestion (ESG / SIG) à la clôture.",
            detailedExplanationAr = "تختلف حسابات الصنف 8 عن الأصناف 1-5 (الميزانية) والأصناف 6-7 (التكاليف والإيرادات اليومية)؛ فهي لا تستعمل طوال السنة في القيود اليومية، بل تفتح حصرياً في نهاية السنة المحاسبية أثناء إقفال الدفاتر لتجميع الفوارق بين الإيرادات والتكاليف وتحديد كيف تولدت ثروة المقاولة عبر مؤشرات متسلسلة وصولاً للربح الصافي النهائي.",
            practicalExample = "تحديد القيمة المضافة (Valeur Ajoutée) التي خلقتها المقاولة بمبلغ 450,000 درهم بعد خصم استهلاك المواد والخدمات من الإنتاج."
        ),

        // RUBRIQUE 81
        AccountingElement(
            code = "81",
            level = ElementLevel.RUBRIQUE,
            officialFrenchTitle = "RESULTAT D'EXPLOITATION",
            arabicTitle = "نتيجة الاستغلال وأرصدة التسيير المرتبطة به",
            classNumber = 8,
            parentCode = "8",
            statementType = StatementType.RESULTATS,
            normalBalance = BalanceType.VARIABLE,
            shortExplanationAr = "يقيس الأداء التجاري والإنتاجي والتشغيلي للمقاولة بمعزل عن التمويل والضرائب والظروف الاستثنائية.",
            shortExplanationFr = "Regroupe les soldes de gestion de l'activité économique d'exploitation : Marge brute, Valeur ajoutée, EBE et Résultat d'exploitation."
        ),
        AccountingElement(code = "810", level = ElementLevel.POSTE, officialFrenchTitle = "Résultat d'exploitation", arabicTitle = "نتيجة الاستغلال", classNumber = 8, parentCode = "81", statementType = StatementType.RESULTATS, normalBalance = BalanceType.VARIABLE, shortExplanationAr = "الفارق الإجمالي بين إيرادات الاستغلال (الصنف 71) وتكاليف الاستغلال (الصنف 61).", shortExplanationFr = "Solde intermédiaire d'exploitation : Produits d'exploitation (71) - Charges d'exploitation (61)."),
        AccountingElement(code = "8100", level = ElementLevel.COMPTE, officialFrenchTitle = "Résultat d'exploitation", arabicTitle = "نتيجة الاستغلال", classNumber = 8, parentCode = "810", statementType = StatementType.RESULTATS, normalBalance = BalanceType.VARIABLE, shortExplanationAr = "صافي ربح أو عجز النشاط الصناعي والتجاري قبل الفوائد والضرائب.", shortExplanationFr = "Compte centralisant la performance opérationnelle du métier de base."),

        AccountingElement(code = "811", level = ElementLevel.POSTE, officialFrenchTitle = "Marge brute", arabicTitle = "الهامش الإجمالي (الهامش التجاري)", classNumber = 8, parentCode = "81", statementType = StatementType.RESULTATS, normalBalance = BalanceType.CREDIT, shortExplanationAr = "الربح الأساسي للمقاولة التجارية: مبيعات البضائع ناقص تكلفة شراء البضائع المبيعة.", shortExplanationFr = "Indicateur clé des entreprises commerciales : Ventes de marchandises - Achats revendus de marchandises."),
        AccountingElement(code = "8110", level = ElementLevel.COMPTE, officialFrenchTitle = "Marge brute", arabicTitle = "الهامش الإجمالي على المبيعات", classNumber = 8, parentCode = "811", statementType = StatementType.RESULTATS, normalBalance = BalanceType.CREDIT, shortExplanationAr = "يبين هامش الربح المحقق في كل سلعة قبل دفع مصاريف الكراء والرواتب والكهرباء.", shortExplanationFr = "Marge commerciale globale de l'exercice."),

        AccountingElement(code = "814", level = ElementLevel.POSTE, officialFrenchTitle = "Valeur ajoutée", arabicTitle = "القيمة المضافة (VA)", classNumber = 8, parentCode = "81", statementType = StatementType.RESULTATS, normalBalance = BalanceType.CREDIT, shortExplanationAr = "الثروة الحقيقية التي خلقتها المقاولة بجهدها الخاص بعد خصم ما استهلكته من سلع وخدمات الغير (Consommations intermédiaires).", shortExplanationFr = "Richesse réelle créée par l'entreprise : (Marge brute + Production) - Consommations en provenance des tiers."),
        AccountingElement(code = "8140", level = ElementLevel.COMPTE, officialFrenchTitle = "Valeur ajoutée", arabicTitle = "القيمة المضافة", classNumber = 8, parentCode = "814", statementType = StatementType.RESULTATS, normalBalance = BalanceType.CREDIT, shortExplanationAr = "المؤشر الأهم الذي يوزع على الأجراء (رواتب)، الدولة (ضرائب)، الأبناك (فوائد)، وأصحاب المقاولة (أرباح).", shortExplanationFr = "Compte calculant la valeur économique ajoutée de l'entreprise."),

        AccountingElement(code = "817", level = ElementLevel.POSTE, officialFrenchTitle = "Excédent brut d'exploitation", arabicTitle = "فائض الاستغلال الإجمالي (EBE / IBE)", classNumber = 8, parentCode = "81", statementType = StatementType.RESULTATS, normalBalance = BalanceType.VARIABLE, shortExplanationAr = "السيولة الناتجة عن دورة الاستغلال بعد دفع رواتب المستخدمين والضرائب وقبل احتساب الاستهلاكات والمؤونات.", shortExplanationFr = "Ressource de trésorerie opérationnelle générée par l'exploitation : Valeur Ajoutée + Subventions - Impôts & taxes - Charges de personnel."),
        AccountingElement(code = "8171", level = ElementLevel.COMPTE, officialFrenchTitle = "Excédent brut d'exploitation (créditeur)", arabicTitle = "فائض الاستغلال الإجمالي (رصيد دائن / ربح تشغيلي ناصع)", classNumber = 8, parentCode = "817", statementType = StatementType.RESULTATS, normalBalance = BalanceType.CREDIT, shortExplanationAr = "المقاولة تولد تدفقاً نقدياً إيجابياً من نشاطها اليومي يمكنها من سداد ديونها وتجديد آلاتها.", shortExplanationFr = "Solde positif mesurant la rentabilité économique brute intrinsèque."),
        AccountingElement(code = "8179", level = ElementLevel.COMPTE, officialFrenchTitle = "Insuffisance brute d'exploitation (déditeur)", arabicTitle = "عجز الاستغلال الإجمالي (رصيد مدين / نزيف تشغيلي)", classNumber = 8, parentCode = "817", statementType = StatementType.RESULTATS, normalBalance = BalanceType.DEBIT, shortExplanationAr = "النشاط التشغيلي يستهلك أموالاً أكثر مما يدره ولا يغطي حتى الرواتب والضرائب المباشرة.", shortExplanationFr = "Déficit brut d'exploitation de l'activité courante."),

        // RUBRIQUE 83
        AccountingElement(
            code = "83",
            level = ElementLevel.RUBRIQUE,
            officialFrenchTitle = "RESULTAT FINANCIER",
            arabicTitle = "النتيجة المالية",
            classNumber = 8,
            parentCode = "8",
            statementType = StatementType.RESULTATS,
            normalBalance = BalanceType.VARIABLE,
            shortExplanationAr = "الفارق بين الإيرادات المالية (الصنف 73) والتكاليف المالية (الصنف 63).",
            shortExplanationFr = "Solde net de la politique financière : Produits financiers (73) - Charges financières (63)."
        ),
        AccountingElement(code = "830", level = ElementLevel.POSTE, officialFrenchTitle = "Résultat financier", arabicTitle = "النتيجة المالية", classNumber = 8, parentCode = "83", statementType = StatementType.RESULTATS, normalBalance = BalanceType.VARIABLE, shortExplanationAr = "يقيس تأثير كلفة الاقتراض البنكي وفوارق أسعار الصرف والاستثمارات المالية.", shortExplanationFr = "Performance financière globale de l'exercice."),
        AccountingElement(code = "8300", level = ElementLevel.COMPTE, officialFrenchTitle = "Résultat financier", arabicTitle = "النتيجة المالية", classNumber = 8, parentCode = "830", statementType = StatementType.RESULTATS, normalBalance = BalanceType.VARIABLE, shortExplanationAr = "عادة ما يكون سالباً في المقاولات التي تعتمد على القروض لتمويل استثماراتها.", shortExplanationFr = "Compte déterminant le bénéfice ou la perte financière."),

        // RUBRIQUE 84
        AccountingElement(
            code = "84",
            level = ElementLevel.RUBRIQUE,
            officialFrenchTitle = "RESULTAT COURANT",
            arabicTitle = "النتيجة الجارية (العادية)",
            classNumber = 8,
            parentCode = "8",
            statementType = StatementType.RESULTATS,
            normalBalance = BalanceType.VARIABLE,
            shortExplanationAr = "مجموع نتيجة الاستغلال والنتيجة المالية معاً (Résultat d'exploitation + Résultat financier).",
            shortExplanationFr = "Résultat de l'activité normale et courante de l'entreprise : Résultat d'exploitation + Résultat financier."
        ),
        AccountingElement(code = "840", level = ElementLevel.POSTE, officialFrenchTitle = "Résultat courant", arabicTitle = "النتيجة الجارية", classNumber = 8, parentCode = "84", statementType = StatementType.RESULTATS, normalBalance = BalanceType.VARIABLE, shortExplanationAr = "تعكس قدرة المقاولة على تحقيق أرباح مستدامة من نشاطها الاعتيادي.", shortExplanationFr = "Capacité bénéficiaire récurrente hors événements exceptionnels."),
        AccountingElement(code = "8400", level = ElementLevel.COMPTE, officialFrenchTitle = "Résultat courant", arabicTitle = "النتيجة الجارية", classNumber = 8, parentCode = "840", statementType = StatementType.RESULTATS, normalBalance = BalanceType.VARIABLE, shortExplanationAr = "الربح أو الخسارة الناتجة عن الأنشطة المتكررة للشركة.", shortExplanationFr = "Compte calculant le solde courant."),

        // RUBRIQUE 85
        AccountingElement(
            code = "85",
            level = ElementLevel.RUBRIQUE,
            officialFrenchTitle = "RESULTAT NON COURANT",
            arabicTitle = "النتيجة غير الجارية (الاستثنائية)",
            classNumber = 8,
            parentCode = "8",
            statementType = StatementType.RESULTATS,
            normalBalance = BalanceType.VARIABLE,
            shortExplanationAr = "الفارق بين العائدات غير الجارية (الصنف 75) والتكاليف غير الجارية (الصنف 65).",
            shortExplanationFr = "Solde des opérations exceptionnelles : Produits non courants (75) - Charges non courantes (65)."
        ),
        AccountingElement(code = "850", level = ElementLevel.POSTE, officialFrenchTitle = "Résultat non courant", arabicTitle = "النتيجة غير الجارية", classNumber = 8, parentCode = "85", statementType = StatementType.RESULTATS, normalBalance = BalanceType.VARIABLE, shortExplanationAr = "الأرباح أو الخسائر الاستثنائية المحققة من بيع أصول أو دفع غرامات.", shortExplanationFr = "Impact des événements fortuits, cessions d'actifs et pénalités."),
        AccountingElement(code = "8500", level = ElementLevel.COMPTE, officialFrenchTitle = "Résultat non courant", arabicTitle = "النتيجة غير الجارية", classNumber = 8, parentCode = "850", statementType = StatementType.RESULTATS, normalBalance = BalanceType.VARIABLE, shortExplanationAr = "يقيد الفائض أو العجز الناتج عن العمليات غير الاعتيادية.", shortExplanationFr = "Compte déterminant le solde exceptionnel net."),

        // RUBRIQUE 86
        AccountingElement(
            code = "86",
            level = ElementLevel.RUBRIQUE,
            officialFrenchTitle = "RESULTAT AVANT IMPOTS",
            arabicTitle = "النتيجة قبل احتساب الضريبة على الشركات",
            classNumber = 8,
            parentCode = "8",
            statementType = StatementType.RESULTATS,
            normalBalance = BalanceType.VARIABLE,
            shortExplanationAr = "مجموع النتيجة الجارية والنتيجة غير الجارية (Résultat courant + Résultat non courant).",
            shortExplanationFr = "Bénéfice ou perte globale avant déduction de l'Impôt sur les Sociétés : Résultat courant + Résultat non courant."
        ),
        AccountingElement(code = "860", level = ElementLevel.POSTE, officialFrenchTitle = "Résultat avant impôts", arabicTitle = "النتيجة قبل احتساب الضريبة", classNumber = 8, parentCode = "86", statementType = StatementType.RESULTATS, normalBalance = BalanceType.VARIABLE, shortExplanationAr = "الربح المحاسبي الإجمالي الخاضع للتعديل الضريبي.", shortExplanationFr = "Assise comptable servant de base aux retraitements fiscaux de l'IS."),
        AccountingElement(code = "8600", level = ElementLevel.COMPTE, officialFrenchTitle = "Résultat avant impôts", arabicTitle = "النتيجة قبل احتساب الضريبة", classNumber = 8, parentCode = "860", statementType = StatementType.RESULTATS, normalBalance = BalanceType.VARIABLE, shortExplanationAr = "النتيجة الإجمالية لكافة العمليات المالية والتشغيلية والاستثنائية.", shortExplanationFr = "Compte de résultat brut avant impôt."),

        // RUBRIQUE 88
        AccountingElement(
            code = "88",
            level = ElementLevel.RUBRIQUE,
            officialFrenchTitle = "RESULTAT APRES IMPOTS",
            arabicTitle = "النتيجة الصافية بعد خصم الضريبة (Résultat net)",
            classNumber = 8,
            parentCode = "8",
            statementType = StatementType.RESULTATS,
            normalBalance = BalanceType.VARIABLE,
            shortExplanationAr = "الربح أو الخسارة الصافية النهائية للسنة المالية (النتيجة قبل الضريبة - الضريبة على الأرباح 670).",
            shortExplanationFr = "Bénéfice net ou perte nette définitive de l'exercice après déduction de l'impôt sur les sociétés."
        ),
        AccountingElement(code = "880", level = ElementLevel.POSTE, officialFrenchTitle = "Résultat après impôts", arabicTitle = "النتيجة بعد احتساب الضريبة", classNumber = 8, parentCode = "88", statementType = StatementType.RESULTATS, normalBalance = BalanceType.VARIABLE, shortExplanationAr = "تطابق تماماً الرصيد النهائي المسجل في الحساب 119 في الميزانية (الأموال الذاتية).", shortExplanationFr = "Équivaut exactement au résultat net figurant au passif du bilan (compte 119)."),
        AccountingElement(code = "8800", level = ElementLevel.COMPTE, officialFrenchTitle = "Résultat après impôts", arabicTitle = "النتيجة بعد احتساب الضريبة", classNumber = 8, parentCode = "880", statementType = StatementType.RESULTATS, normalBalance = BalanceType.VARIABLE, shortExplanationAr = "المبلغ النهائي القابل للتوزيع بين الشركاء كأنصبة أرباح (Dividendes) أو الاحتفاظ به كاحتياطيات (Réserves).", shortExplanationFr = "Bénéfice distribuable final ou perte nette à reporter.")
    )
}
