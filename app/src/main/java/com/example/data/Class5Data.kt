package com.example.data

import com.example.model.AccountingElement
import com.example.model.BalanceType
import com.example.model.ElementLevel
import com.example.model.StatementType

object Class5Data {
    val elements: List<AccountingElement> = listOf(
        // CLASSE 5
        AccountingElement(
            code = "5",
            level = ElementLevel.CLASSE,
            officialFrenchTitle = "COMPTES DE TRESORERIE",
            arabicTitle = "حسابات الخزينة (السيولة النقدية والبنكية)",
            classNumber = 5,
            statementType = StatementType.BILAN_ACTIF,
            normalBalance = BalanceType.VARIABLE,
            shortExplanationAr = "يضم السيولة الجاهزة للمقاولة (أموال البنك، الصندوق، الشيكات والكمبيالات قيد التحصيل) بالإضافة إلى السحب على المكشوف وقروض الخزينة العاجلة.",
            shortExplanationFr = "Disponibilités liquides (banque, caisse, chèques) et concours bancaires courants à très court terme.",
            detailedExplanationAr = "تنقسم إلى خزينة الأصول (Trésorerie - Actif، حسابات 51 ذات رصيد مدين) وخزينة الخصوم (Trésorerie - Passif، حسابات 55 ذات رصيد دائن). الفارق بينهما يعطي السيولة الصافية للمقاولة (Trésorerie Nette = TA - TP).",
            practicalExample = "إيداع مبلغ 50,000 درهم في الحساب البنكي، أو أداء مصاريف نقل نقداً من الصندوق بمبلغ 350 درهماً."
        ),

        // RUBRIQUE 51
        AccountingElement(
            code = "51",
            level = ElementLevel.RUBRIQUE,
            officialFrenchTitle = "TRESORERIE - ACTIF",
            arabicTitle = "خزينة الأصول (السيولة الإيجابية)",
            classNumber = 5,
            parentCode = "5",
            statementType = StatementType.BILAN_ACTIF,
            normalBalance = BalanceType.DEBIT,
            shortExplanationAr = "الأموال والسيولة النقدية المتاحة فعلياً وتحت تصرف المقاولة الفوري في البنوك والصناديق.",
            shortExplanationFr = "Disponibilités monétaires immédiatement mobilisables (banques créditrices, caisses et chèques en cours)."
        ),
        AccountingElement(
            code = "511",
            level = ElementLevel.POSTE,
            officialFrenchTitle = "Chèques et valeurs à encaisser",
            arabicTitle = "شيكات وقيم للتحصيل",
            classNumber = 5,
            parentCode = "51",
            statementType = StatementType.BILAN_ACTIF,
            normalBalance = BalanceType.DEBIT,
            shortExplanationAr = "الشيكات والكمبيالات المستلمة من الزبائن التي أودعت في البنك وتنتظر قيد رصيدها الفعلي.",
            shortExplanationFr = "Chèques reçus de clients et effets de commerce déposés à l'encaissement."
        ),
        AccountingElement(code = "5111", level = ElementLevel.COMPTE, officialFrenchTitle = "Chèques à encaisser ou à l'encaissement", arabicTitle = "شيكات للتحصيل أو قيد التحصيل", classNumber = 5, parentCode = "511", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.DEBIT, shortExplanationAr = "شيكات موقعة من الزبائن بحوزة أمين الصندوق أو أرسلت للبنك للتحصيل.", shortExplanationFr = "Chèques en attente d'encaissement sur le compte bancaire."),
        AccountingElement(code = "51111", level = ElementLevel.SOUS_COMPTE, officialFrenchTitle = "Chèques en portefeuille", arabicTitle = "شيكات في الحفظ (في الصندوق)", classNumber = 5, parentCode = "5111", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.DEBIT, shortExplanationAr = "شيكات ورقية استلمتها الشركة من الزبائن وموجودة في الخزنة الحديدية قبل إرسالها للبنك.", shortExplanationFr = "Chèques reçus physiquement non encore remis à la banque."),
        AccountingElement(code = "51112", level = ElementLevel.SOUS_COMPTE, officialFrenchTitle = "Chèques à l'encaissement", arabicTitle = "شيكات قيد التحصيل في البنك", classNumber = 5, parentCode = "5111", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.DEBIT, shortExplanationAr = "شيكات تم إيداعها في شباك البنك وتنتظر المقاصة (Compensations) لإضافتها للحساب.", shortExplanationFr = "Chèques déposés à la banque en attente de crédit effectif."),
        AccountingElement(code = "5113", level = ElementLevel.COMPTE, officialFrenchTitle = "Effets à encaisser ou à l'encaissement", arabicTitle = "كمبيالات للتحصيل أو قيد التحصيل", classNumber = 5, parentCode = "511", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.DEBIT, shortExplanationAr = "كمبيالات وسندات إذنية حان موعد استحقاقها وقدمت للبنك ليقبض قيمتها من الزبون.", shortExplanationFr = "Lettres de change échues remises à l'encaissement."),
        AccountingElement(code = "51131", level = ElementLevel.SOUS_COMPTE, officialFrenchTitle = "Effets échus à encaisser", arabicTitle = "كمبيالات حالة الأجل للتحصيل", classNumber = 5, parentCode = "5113", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.DEBIT, shortExplanationAr = "كمبيالات حل تاريخ استحقاقها في الصندوق ولم ترسل بعد للبنك.", shortExplanationFr = "Effets de commerce arrivés à échéance prêts à être encaissés."),
        AccountingElement(code = "51132", level = ElementLevel.SOUS_COMPTE, officialFrenchTitle = "Effets à l'encaissement", arabicTitle = "كمبيالات موضوعة للتحصيل لدى البنك", classNumber = 5, parentCode = "5113", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.DEBIT, shortExplanationAr = "كمبيالات أرسلت للبنك قبل الاستحقاق ببضعة أيام ليتولى البنك قبضها.", shortExplanationFr = "Effets remis à la banque pour encaissement à l'échéance."),
        AccountingElement(code = "5115", level = ElementLevel.COMPTE, officialFrenchTitle = "Virement de fonds", arabicTitle = "تحويل الأموال الداخلي (Virement interne)", classNumber = 5, parentCode = "511", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.VARIABLE, shortExplanationAr = "حساب وسيط لتسجيل تحويل نقود من الصندوق إلى البنك أو بين حسابين بنكيين لمنع التكرار المزدوج.", shortExplanationFr = "Compte de passage pour les transferts d'argent entre la caisse et la banque (virements de fonds internes)."),
        AccountingElement(code = "5118", level = ElementLevel.COMPTE, officialFrenchTitle = "Autres valeurs à encaisser", arabicTitle = "قيم أخرى للتحصيل", classNumber = 5, parentCode = "511", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.DEBIT, shortExplanationAr = "كوبونات أرباح أسهم، حوالات بريدية أو بطاقات بنكية قيد التسوية.", shortExplanationFr = "Coupons à encaisser, mandats et règlements par cartes bancaires en instance."),

        AccountingElement(
            code = "514",
            level = ElementLevel.POSTE,
            officialFrenchTitle = "Banques, Trésorerie Générale et chèques postaux débiteurs",
            arabicTitle = "الأبناك، الخزينة العامة والحسابات الجارية البريدية المدينة",
            classNumber = 5,
            parentCode = "51",
            statementType = StatementType.BILAN_ACTIF,
            normalBalance = BalanceType.DEBIT,
            shortExplanationAr = "الأرصدة الموجبة للمقاولة المودعة في الحسابات البنكية وبريد بنك والخزينة العامة.",
            shortExplanationFr = "Soldes positifs en comptes courants bancaires et postaux."
        ),
        AccountingElement(code = "5141", level = ElementLevel.COMPTE, officialFrenchTitle = "Banques (solde débiteur)", arabicTitle = "الأبناك (رصيد مدين / موجب)", classNumber = 5, parentCode = "514", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.DEBIT, shortExplanationAr = "أهم حساب خزينة؛ يسجل كل الإيداعات (مدين) والمدفوعات (دائن) في الحسابات البنكية التجارية للشركة.", shortExplanationFr = "Compte bancaire courant présentant un solde positif disponible."),
        AccountingElement(code = "5143", level = ElementLevel.COMPTE, officialFrenchTitle = "Trésorerie Générale", arabicTitle = "الخزينة العامة للمملكة (TGR)", classNumber = 5, parentCode = "514", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.DEBIT, shortExplanationAr = "حساب جاري مفتوح لدى الخزينة العامة للمملكة (خاص بالمؤسسات العمومية أو المتعاقدين مع الدولة).", shortExplanationFr = "Fonds déposés au Trésor Public marocain."),
        AccountingElement(code = "5146", level = ElementLevel.COMPTE, officialFrenchTitle = "Chèques postaux", arabicTitle = "الشيكات والحسابات البريدية (Al Barid Bank)", classNumber = 5, parentCode = "514", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.DEBIT, shortExplanationAr = "حساب جاري مفتوح لدى مؤسسة البريد بنك لتنفيذ التحويلات.", shortExplanationFr = "Compte de chèques postaux (CCP / Al Barid Bank)."),
        AccountingElement(code = "5148", level = ElementLevel.COMPTE, officialFrenchTitle = "Autres établissements financiers et assimilés (soldes débiteurs)", arabicTitle = "مؤسسات مالية أخرى وما يماثلها (أرصدة مدينة)", classNumber = 5, parentCode = "514", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.DEBIT, shortExplanationAr = "أرصدة إيجابية في شركات التمويل أو منصات الأداء الإلكتروني المعتمدة.", shortExplanationFr = "Avoirs auprès d'autres intermédiaires financiers agréés."),

        AccountingElement(
            code = "516",
            level = ElementLevel.POSTE,
            officialFrenchTitle = "Caisses, régies d'avances et accréditifs",
            arabicTitle = "الصناديق، وكالات التسبيقات والاعتمادات النقدية",
            classNumber = 5,
            parentCode = "51",
            statementType = StatementType.BILAN_ACTIF,
            normalBalance = BalanceType.DEBIT,
            shortExplanationAr = "الأوراق النقدية والقطع النقدية السائلة الموجودة في الخزنة النقدية للمقاولة والصناديق الفرعية.",
            shortExplanationFr = "Espèces en monnaie fiduciaire détenues dans les coffres et caisses de l'entreprise."
        ),
        AccountingElement(code = "5161", level = ElementLevel.COMPTE, officialFrenchTitle = "Caisses", arabicTitle = "الصناديق النقدية", classNumber = 5, parentCode = "516", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.DEBIT, shortExplanationAr = "الحساب النقدي الذي يسجل المقبوضات والمدفوعات نقداً (ورصيده لا يمكن أن يكون سالباً أبداً).", shortExplanationFr = "Compte caisse enregistrant les encaissements et décaissements en espèces (jamais créditeur)."),
        AccountingElement(code = "51611", level = ElementLevel.SOUS_COMPTE, officialFrenchTitle = "Caisse Centrale", arabicTitle = "الصندوق المركزي", classNumber = 5, parentCode = "5161", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.DEBIT, shortExplanationAr = "الخزنة النقدية الرئيسية في مقر المقاولة.", shortExplanationFr = "Caisse principale au siège de l'entreprise."),
        AccountingElement(code = "51613", level = ElementLevel.SOUS_COMPTE, officialFrenchTitle = "Caisse (succursale ou agence A)", arabicTitle = "صندوق (الفرع أو الوكالة أ)", classNumber = 5, parentCode = "5161", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.DEBIT, shortExplanationAr = "صندوق نقدي في نقطة بيع أو وكالة تجارية أولى.", shortExplanationFr = "Caisse décentralisée de la succursale A."),
        AccountingElement(code = "51614", level = ElementLevel.SOUS_COMPTE, officialFrenchTitle = "Caisse (succursale ou agence B)", arabicTitle = "صندوق (الفرع أو الوكالة ب)", classNumber = 5, parentCode = "5161", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.DEBIT, shortExplanationAr = "صندوق نقدي في فرع ثانٍ للشركة.", shortExplanationFr = "Caisse décentralisée de la succursale B."),
        AccountingElement(code = "5165", level = ElementLevel.COMPTE, officialFrenchTitle = "Régies d'avances et accréditifs", arabicTitle = "وكالات التسبيقات والاعتمادات النقدية", classNumber = 5, parentCode = "516", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.DEBIT, shortExplanationAr = "صناديق نفقات نثرية صغيرة (Petite caisse) تحت تصرف مسؤول لتسديد مصاريف طارئة.", shortExplanationFr = "Fonds avancés à un régisseur pour dépenses mineures et immédiates."),

        // RUBRIQUE 55
        AccountingElement(
            code = "55",
            level = ElementLevel.RUBRIQUE,
            officialFrenchTitle = "TRESORERIE - PASSIF",
            arabicTitle = "خزينة الخصوم (ديون البنوك العاجلة)",
            classNumber = 5,
            parentCode = "5",
            statementType = StatementType.BILAN_PASSIF,
            normalBalance = BalanceType.CREDIT,
            shortExplanationAr = "الديون والقروض البنكية قصيرة الأجل جداً: السحب على المكشوف (Découvert)، وخصم الكمبيالات غير المحصلة.",
            shortExplanationFr = "Concours financiers bancaires à court terme et facilités de caisse (soldes débiteurs en banque)."
        ),
        AccountingElement(code = "552", level = ElementLevel.POSTE, officialFrenchTitle = "Crédits d'escompte", arabicTitle = "قروض خصم الكمبيالات (Escompte)", classNumber = 5, parentCode = "55", statementType = StatementType.BILAN_PASSIF, normalBalance = BalanceType.CREDIT, shortExplanationAr = "سيولة يقدمها البنك للمقاولة مسبقاً مقابل التنازل عن كمبيالات قبل حلول أجل استحقاقها.", shortExplanationFr = "Avances bancaires obtenues par négociation d'effets de commerce avant échéance."),
        AccountingElement(code = "5520", level = ElementLevel.COMPTE, officialFrenchTitle = "Crédits d'escompte", arabicTitle = "قروض خصم الكمبيالات", classNumber = 5, parentCode = "552", statementType = StatementType.BILAN_PASSIF, normalBalance = BalanceType.CREDIT, shortExplanationAr = "التزام مالي مشروط على المقاولة حتى يقوم الزبون بسداد الكمبيالة للبنك في موعدها.", shortExplanationFr = "Dette d'escompte envers la banque jusqu'au dénouement de l'effet."),
        AccountingElement(code = "553", level = ElementLevel.POSTE, officialFrenchTitle = "Crédits de trésorerie", arabicTitle = "قروض الخزينة العاجلة", classNumber = 5, parentCode = "55", statementType = StatementType.BILAN_PASSIF, normalBalance = BalanceType.CREDIT, shortExplanationAr = "قروض مصرفية موسمية أو سلفات نقدية قصيرة المدى لمعالجة أزمة سيولة مؤقتة.", shortExplanationFr = "Crédits de campagne, facilités de caisse et crédits spot accordés par les banques."),
        AccountingElement(code = "5530", level = ElementLevel.COMPTE, officialFrenchTitle = "Crédits de trésorerie", arabicTitle = "قروض الخزينة العاجلة", classNumber = 5, parentCode = "553", statementType = StatementType.BILAN_PASSIF, normalBalance = BalanceType.CREDIT, shortExplanationAr = "قروض بنكية لشهر أو بضعة أسابيع لشراء مخزون موسمي.", shortExplanationFr = "Concours de trésorerie à très court terme."),
        AccountingElement(code = "554", level = ElementLevel.POSTE, officialFrenchTitle = "Banques (solde créditeur)", arabicTitle = "الأبناك (رصيد دائن / سحب على المكشوف Découvert)", classNumber = 5, parentCode = "55", statementType = StatementType.BILAN_PASSIF, normalBalance = BalanceType.CREDIT, shortExplanationAr = "الحساب البنكي عندما يصبح رصيده سالباً بتسهيل وائتمان مؤقت من البنك (Facilité de caisse).", shortExplanationFr = "Découverts bancaires autorisés et facilités de caisse utilisées par l'entreprise."),
        AccountingElement(code = "5541", level = ElementLevel.COMPTE, officialFrenchTitle = "Banques (solde créditeur)", arabicTitle = "الأبناك (رصيد دائن / مكشوف)", classNumber = 5, parentCode = "554", statementType = StatementType.BILAN_PASSIF, normalBalance = BalanceType.CREDIT, shortExplanationAr = "يسجل في ميزانية نهاية السنة كدين واجب السداد للبنك في عمود الخصوم.", shortExplanationFr = "Solde négatif du compte bancaire à la date de clôture."),
        AccountingElement(code = "5548", level = ElementLevel.COMPTE, officialFrenchTitle = "Autres établissements financiers et assimilés (soldes créditeurs)", arabicTitle = "مؤسسات مالية أخرى (أرصدة دائنة)", classNumber = 5, parentCode = "554", statementType = StatementType.BILAN_PASSIF, normalBalance = BalanceType.CREDIT, shortExplanationAr = "أرصدة مكشوفة لدى وسطاء أو مؤسسات دفع أخرى.", shortExplanationFr = "Soldes créditeurs auprès d'autres organismes financiers."),

        // RUBRIQUE 59
        AccountingElement(
            code = "59",
            level = ElementLevel.RUBRIQUE,
            officialFrenchTitle = "PROVISIONS POUR DEPRECIATION DES COMPTES DE TRESORERIE",
            arabicTitle = "مؤونات تدهور حسابات الخزينة",
            classNumber = 5,
            parentCode = "5",
            statementType = StatementType.BILAN_ACTIF,
            normalBalance = BalanceType.CREDIT,
            shortExplanationAr = "مؤونة استثنائية لمواجهة تعثر بنك مودع لديه أموال أو ضياع شيكات أو قيم نقدية.",
            shortExplanationFr = "Provisions constatant un risque exceptionnel d'irrécouvrabilité de liquidités ou faillite bancaire."
        ),
        AccountingElement(code = "590", level = ElementLevel.POSTE, officialFrenchTitle = "Provisions pour dépréciation des comptes de trésorerie", arabicTitle = "مؤونات تدهور حسابات الخزينة", classNumber = 5, parentCode = "59", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.CREDIT, shortExplanationAr = "تطرح في ميزانية الخزينة عند وجود خطر حقيقي على استرداد الوديعة.", shortExplanationFr = "Poste de provision sur disponibilités."),
        AccountingElement(code = "5900", level = ElementLevel.COMPTE, officialFrenchTitle = "Provisions pour dépréciation des comptes de trésorerie", arabicTitle = "مؤونات تدهور حسابات الخزينة", classNumber = 5, parentCode = "590", statementType = StatementType.BILAN_ACTIF, normalBalance = BalanceType.CREDIT, shortExplanationAr = "تجميد حساب بنكي خارجي أو وضع مصرف تحت التصفية القضائية.", shortExplanationFr = "Compte général de dépréciation de fonds bloqués en banque.")
    )
}
