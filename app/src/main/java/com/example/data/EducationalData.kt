package com.example.data

import com.example.model.AcronymInfo
import com.example.model.ComparisonItem
import com.example.model.ComparisonRow
import com.example.model.JournalEntry
import com.example.model.JournalEntryLine
import com.example.model.LexiconTerm
import com.example.model.QuizQuestion

object EducationalData {

    val acronyms: List<AcronymInfo> = listOf(
        AcronymInfo(
            acronym = "P.C.M.",
            fullNameFr = "Plan Comptable Marocain",
            fullNameAr = "المخطط المحاسباتي المغربي",
            explanationAr = "المرجع الرسمي والمنظومة المحاسبية المعتمدة في المغرب لتبويب الحسابات وقيد العمليات المالية."
        ),
        AcronymInfo(
            acronym = "C.G.N.C.",
            fullNameFr = "Code Général de la Normalisation Comptable",
            fullNameAr = "القانون العام للتوحيد المحاسبي",
            explanationAr = "النص التشريعي المنظم للقواعد والمبادئ والأساليب المحاسبية الإلزامية للمقاولات في المغرب."
        ),
        AcronymInfo(
            acronym = "D.E.A.",
            fullNameFr = "Dotations d'Exploitation aux Amortissements",
            fullNameAr = "مخصصات الاستغلال للاستهلاكات",
            explanationAr = "مبلغ التكلفة السنوية التي تسجلها المقاولة في الصنف 619 لتمثيل النقصان التدريجي في قيمة أصولها الثابتة."
        ),
        AcronymInfo(
            acronym = "D.E.P.",
            fullNameFr = "Dotations d'Exploitation aux Provisions",
            fullNameAr = "مخصصات الاستغلال للمؤونات",
            explanationAr = "مبالغ التكاليف الاحتياطية لمواجهة مخاطر أو خسائر محتملة (نزاعات، زبائن مشكوك فيهم، هبوط أسعار المخزون)."
        ),
        AcronymInfo(
            acronym = "V.N.A.",
            fullNameFr = "Valeur Nette d'Amortissements",
            fullNameAr = "القيمة الصافية بعد الاستهلاك",
            explanationAr = "قيمة الأصل الدفترية المتبقية بعد طرح مجموع الاستهلاكات المتراكمة من القيمة الأصلية للشراء (VO - Amortissements)."
        ),
        AcronymInfo(
            acronym = "R.R.R.",
            fullNameFr = "Rabais, Remises, Ristournes",
            fullNameAr = "التخفيضات التجارية (تنزيل، خصم تجاري، مكافأة نهاية السنة)",
            explanationAr = "تخفيضات تجارية تمنح للزبون بسبب عيب في السلعة، أو حجم الشراء الكبير، أو مجموع مشتريات السنة كاملة."
        ),
        AcronymInfo(
            acronym = "T.V.A.",
            fullNameFr = "Taxe sur la Valeur Ajoutée",
            fullNameAr = "الضريبة على القيمة المضافة",
            explanationAr = "ضريبة غير مباشرة على الاستهلاك؛ المقاولة تحصلها لفائدة الدولة على مبيعاتها (TVA facturée: 4455) وتسترجع ما دفعته لمورديها (TVA récupérable: 3455)."
        ),
        AcronymInfo(
            acronym = "E.B.E.",
            fullNameFr = "Excédent Brut d'Exploitation",
            fullNameAr = "فائض الاستغلال الإجمالي",
            explanationAr = "أهم مؤشر في الصنف 8 يقيس السيولة النقدية الذاتية التي ولدها النشاط التشغيلي للمقاولة قبل التمويل والضرائب والاستهلاكات."
        ),
        AcronymInfo(
            acronym = "C.A.G.E.",
            fullNameFr = "Comptabilité Analytique de Gestion",
            fullNameAr = "المحاسبة التحليلية للتسيير",
            explanationAr = "المحاسبة الداخلية الخاصة (الصنف 9) التي تحلل التكاليف وتحدد سعر تكلفة كل منتج وهامش ربحه بدقة."
        ),
        AcronymInfo(
            acronym = "I.S.",
            fullNameFr = "Impôt sur les Sociétés",
            fullNameAr = "الضريبة على الشركات",
            explanationAr = "الضريبة المفروضة على الأرباح الصافية للشركات في المغرب (الحساب 6701)."
        )
    )

    val lexicon: List<LexiconTerm> = listOf(
        LexiconTerm(
            termFr = "Bilan",
            termAr = "الميزانية الختامية",
            phoneticAr = "بيلان",
            definitionAr = "صورة فوتوغرافية للوضعية المالية للمقاولة في تاريخ معين؛ تبين ما تملكه في جانب الأصول (Actif) وما عليها من التزامات ومصادر تمويل في جانب الخصوم (Passif).",
            definitionFr = "Tableau financier de synthèse décrivant la situation patrimoniale de l'entreprise à une date donnée (Actif = Passif).",
            category = "Général"
        ),
        LexiconTerm(
            termFr = "C.P.C. (Compte de Produits et Charges)",
            termAr = "حساب العائدات والتكاليف",
            phoneticAr = "سي بي سي",
            definitionAr = "قائمة مالية تلخص وتوازن مجموع التكاليف والمصاريف (Charges: الصنف 6) ومجموع المداخيل والإيرادات (Produits: الصنف 7) لحساب نتيجة السنة الصافية (ربح أو خسارة).",
            definitionFr = "État de synthèse récapitulant les produits et les charges de l'exercice pour déterminer le résultat net.",
            category = "Général"
        ),
        LexiconTerm(
            termFr = "Actif",
            termAr = "الأصول (ممتلكات المقاولة)",
            phoneticAr = "أكتيف",
            definitionAr = "كل ما تملكه المقاولة من أراضٍ ومباني وشاحنات وبضائع وأموال في البنك والصندوق ومستحقات لدى الزبائن.",
            definitionFr = "Ensemble des biens et droits détenus par l'entreprise (Immobilisations, stocks, créances, trésorerie).",
            category = "Bilan"
        ),
        LexiconTerm(
            termFr = "Passif",
            termAr = "الخصوم (مصادر التمويل والالتزامات)",
            phoneticAr = "باسيف",
            definitionAr = "المصادر التي استعملت لتمويل الأصول: رأس المال، أرباح السنوات السابقة، القروض البنكية، وديون الموردين.",
            definitionFr = "Ensemble des ressources ayant permis de financer l'actif (Capitaux propres et dettes).",
            category = "Bilan"
        ),
        LexiconTerm(
            termFr = "Débit",
            termAr = "المدين (الجانب الأيمن للحساب)",
            phoneticAr = "ديبي",
            definitionAr = "يسجل في حسابات الأصول والتكاليف كل زيادة (+)، وفي حسابات الخصوم والعائدات كل نقصان (-).",
            definitionFr = "Partie gauche d'un compte comptable; enregistre les emplois de ressources, les augmentations d'actif et les charges.",
            category = "Règles"
        ),
        LexiconTerm(
            termFr = "Crédit",
            termAr = "الدائن (الجانب الأيسر للحساب)",
            phoneticAr = "كريدي",
            definitionAr = "يسجل في حسابات الخصوم والعائدات كل زيادة (+)، وفي حسابات الأصول والتكاليف كل نقصان (-).",
            definitionFr = "Partie droite d'un compte comptable; enregistre les ressources, les augmentations de passif et les produits.",
            category = "Règles"
        ),
        LexiconTerm(
            termFr = "Charge",
            termAr = "التكلفة / المصروف (الصنف 6)",
            phoneticAr = "شارج",
            definitionAr = "استهلاك أو إنفاق مالي نهائي لا تستعيده المقاولة، يترتب عليه نقصان في النتيجة الصافية (مثل شراء بضائع، فاتورة الكهرباء، أجور العمال).",
            definitionFr = "Consommation définitive ou dépense non récupérable réduisant le résultat net (achats, salaires, loyers).",
            category = "CPC"
        ),
        LexiconTerm(
            termFr = "Produit",
            termAr = "العائد / الإيراد (الصنف 7)",
            phoneticAr = "برودوي",
            definitionAr = "مدخول مالي يرفع من النتيجة الصافية للمقاولة، ناتج عن مبيعات المنتجات أو تقديم الخدمات أو عوائد الفوائد.",
            definitionFr = "Ressource définitive enrichissant l'entreprise (ventes de marchandises, prestations, intérêts perçus).",
            category = "CPC"
        ),
        LexiconTerm(
            termFr = "Amortissement",
            termAr = "الاستهلاك المحاسبي",
            phoneticAr = "أمورتيسمون",
            definitionAr = "تسجيل النقصان الفعلي والمؤكد في قيمة الأصول الثابتة نتيجة الاستعمال أو التقادم التكنولوجي مع مرور السنين (حسابات 28).",
            definitionFr = "Constatation comptable de la perte irréversible de valeur d'une immobilisation due à l'usure ou au temps.",
            category = "Opérations"
        ),
        LexiconTerm(
            termFr = "Provision",
            termAr = "المؤونة المحاسبية",
            phoneticAr = "بروفيزيون",
            definitionAr = "احتياط مالي لمواجهة خسارة أو عبء محتمل الوقوع وغير مؤكد بدقة (مثل احتمال عدم تسديد زبون لديونه، أو قضية في المحكمة).",
            definitionFr = "Constatation comptable d'une diminution probable mais non certaine de la valeur d'un actif ou d'une dette future.",
            category = "Opérations"
        ),
        LexiconTerm(
            termFr = "Fournisseur",
            termAr = "المورد (الحساب 4411)",
            phoneticAr = "فورنيسور",
            definitionAr = "الشخص أو الشركة التي تبيع للمقاولة البضائع أو الخدمات؛ وتعتبر المقاولة مدينة له بمبلغ الفاتورة في انتظار السداد.",
            definitionFr = "Partenaire économique qui vend des biens ou services à l'entreprise à crédit.",
            category = "Tiers"
        ),
        LexiconTerm(
            termFr = "Client",
            termAr = "الزبون (الحساب 3421)",
            phoneticAr = "كليون",
            definitionAr = "الشخص أو الشركة التي تشتري منتجات المقاولة، ويكون مديناً لها بمبلغ البيع حتى تاريخ الأداء.",
            definitionFr = "Acheteur des biens ou prestations de l'entreprise; titulaire d'une créance.",
            category = "Tiers"
        ),
        LexiconTerm(
            termFr = "Créance",
            termAr = "المستحَق / الدين للمقاولة على الغير",
            phoneticAr = "كرييونس",
            definitionAr = "حق مالي للمقاولة على طرف خارجي (كالزبائن أو الدولة) يُلزمهم بالدفع في أجل محدد؛ تسجل في الأصول (Actif).",
            definitionFr = "Droit qu'a l'entreprise d'exiger une somme d'argent d'un tiers à une échéance donnée.",
            category = "Bilan"
        ),
        LexiconTerm(
            termFr = "Dette",
            termAr = "الدين / الالتزام على المقاولة للغير",
            phoneticAr = "ديت",
            definitionAr = "التزام مالي على المقاولة لصالح طرف خارجي (كالمورد أو البنك أو إدارة الضرائب)؛ تسجل في الخصوم (Passif).",
            definitionFr = "Obligation financière imposant à l'entreprise de payer une somme d'argent à un tiers.",
            category = "Bilan"
        )
    )

    val comparisons: List<ComparisonItem> = listOf(
        ComparisonItem(
            titleAr = "الفرق بين الاستهلاك (Amortissement) والمؤونة (Provision)",
            titleFr = "Amortissement vs Provision",
            conceptAName = "الاستهلاك (Amortissement)",
            conceptBName = "المؤونة (Provision)",
            summaryAr = "الاستهلاك يسجل خسارة مؤكدة ونهائية في قيمة الأصل بسبب التقادم، بينما المؤونة تسجل نقصاناً محتملاً وقابلاً للإلغاء أو التعديل.",
            rows = listOf(
                ComparisonRow("طبيعة الخسارة", "مؤكدة ولا رجعة فيها (Définitive)", "محتملة وغير مؤكدة تماماً (Probable)"),
                ComparisonRow("المجال والتطبيق", "يخص الأصول الثابتة القابلة للاستهلاك فقط", "يخص الأصول والخصوم والمخاطر القضائية"),
                ComparisonRow("حسابات النقصان", "حسابات الصنف 28 (أرقام تبدأ بـ 28)", "حسابات تنتهي برقم 9 (29, 39, 49, 15, 45)"),
                ComparisonRow("إمكانية الاسترجاع", "لا تسترجع أبداً حتى بيع الأصل أو إتلافه", "تسترجع كلياً أو جزئياً عند زوال الخطر (الصنف 719)")
            )
        ),
        ComparisonItem(
            titleAr = "الفرق بين التكلفة (Charge) والأصل الثابت (Immobilisation)",
            titleFr = "Charge vs Immobilisation",
            conceptAName = "التكلفة (Charge - الصنف 6)",
            conceptBName = "الأصل الثابت (Immobilisation - الصنف 2)",
            summaryAr = "التكلفة استهلاك سريع يخص السنة الجارية، بينما الأصل الثابت ممتلك يستمر لأكثر من سنة ويخلق قيمة مستقبلية.",
            rows = listOf(
                ComparisonRow("المدة الزمنية", "تستهلك في أقل من سنة محاسبية واحدة", "تستعمل لأكثر من سنة (مستدامة)"),
                ComparisonRow("الأثر المالي", "تخفّض فوراً ربح السنة الصافية (CPC)", "تضاف لممتلكات المقاولة في الميزانية (Actif)"),
                ComparisonRow("مثال تطبيقي", "شراء ورق وأقلام ومحروقات للسيارة", "شراء شاحنة توزيع جديدة أو عقار"),
                ComparisonRow("الاستهلاك", "لا تستهلك؛ تسجل بكامل قيمتها", "تستهلك سنوياً على مدار عمرها الإنتاجي")
            )
        ),
        ComparisonItem(
            titleAr = "الفرق بين الميزانية (Bilan) وحساب العائدات والتكاليف (C.P.C.)",
            titleFr = "Bilan vs C.P.C.",
            conceptAName = "الميزانية (Bilan)",
            conceptBName = "حساب العائدات والتكاليف (C.P.C.)",
            summaryAr = "الميزانية صورة تراكمية للممتلكات والديون، بينما الـ CPC فيلم يوثق النشاط والأرباح المتولدة خلال السنة.",
            rows = listOf(
                ComparisonRow("الأصناف المستعملة", "الأصناف 1 و 2 و 3 و 4 و 5", "الصنفان 6 (تكاليف) و 7 (إيرادات)"),
                ComparisonRow("الرؤية المحاسبية", "رؤية تراكمية للذمة المالية (Patrimoine)", "رؤية تدفقية للنشاط التشغيلي والمالي"),
                ComparisonRow("ترحيل الأرصدة", "الأرصدة ترحل للسنة القادمة (Bilan d'ouverture)", "الأرصدة تقفل نهائياً في نهاية السنة وتصفر")
            )
        ),
        ComparisonItem(
            titleAr = "التخفيضات التجارية (RRR) والتخفيض المالي (Escompte)",
            titleFr = "Réductions Commerciales vs Financière",
            conceptAName = "التخفيض التجاري (RRR: Rabais, Remise, Ristourne)",
            conceptBName = "الخصم المالي (Escompte de règlement)",
            summaryAr = "التخفيض التجاري يرتبط بالسلعة أو الكمية، بينما الخصم المالي يرتبط بالأداء النقدي السريع قبل الأجل.",
            rows = listOf(
                ComparisonRow("السبب الرئيسي", "عيب في السلعة أو كمية شراء ضخمة", "السداد قبل الأجل المتفق عليه نقداً"),
                ComparisonRow("المعالجة في الفاتورة", "يطرح من السعر الإجمالي للوصول للصافي التجاري", "يطرح للوصول للصافي المالي ويسجل في الحساب 7386 أو 6386"),
                ComparisonRow("التأثير على الـ TVA", "الـ TVA تحسب بعد طرح التخفيض التجاري", "الـ TVA تحسب على الصافي المالي بعد الخصم")
            )
        )
    )

    val practicalJournalEntries: List<JournalEntry> = listOf(
        JournalEntry(
            id = "entry_1",
            titleAr = "عملية 1: شراء بضائع على الحساب مع الضريبة (TVA)",
            titleFr = "Achat de marchandises à crédit avec TVA récupérable",
            descriptionAr = "في 12 أكتوبر، اشترت المقاولة بضائع بقيمة 50,000 درهم بدون احتساب الضريبة، خاضعة لـ TVA بنسبة 20%، على الحساب بموجب الفاتورة رقم F-102.",
            dateExample = "12/10/N",
            documentRef = "Facture n° F-102",
            lines = listOf(
                JournalEntryLine(
                    accountCode = "6111",
                    accountTitleFr = "Achats de marchandises",
                    accountTitleAr = "مشتريات بضائع",
                    debitAmount = 50000.0,
                    note = "المبلغ الصافي بدون احتساب الضريبة (HT)"
                ),
                JournalEntryLine(
                    accountCode = "34552",
                    accountTitleFr = "Etat-TVA récupérable sur les charges",
                    accountTitleAr = "الدولة - ضريبة على القيمة المضافة قابلة للاسترجاع على التكاليف",
                    debitAmount = 10000.0,
                    note = "مبلغ الضريبة 20% يسترجع من الدولة (Débit)"
                ),
                JournalEntryLine(
                    accountCode = "4411",
                    accountTitleFr = "Fournisseurs",
                    accountTitleAr = "الموردون",
                    creditAmount = 60000.0,
                    note = "المبلغ الإجمالي الواجب أداؤه للمورد شامل الضريبة (TTC)"
                )
            ),
            pedagogicalExplanationAr = "التكلفة تزيد في المدين (6111) بمبلغ الـ HT. الضريبة المسترجعة حق للمقاولة على الدولة فتسجل في مدين الأصول (34552). والتزام المقاولة تجاه المورد يزيد في دائن الخصوم (4411) بمبلغ TTC كاملاً. توازن القيد: 50,000 + 10,000 = 60,000 درهم."
        ),
        JournalEntry(
            id = "entry_2",
            titleAr = "عملية 2: بيع بضائع مع قبض جزئي بالبنك والباقي على الحساب",
            titleFr = "Vente de marchandises avec règlement partiel banque et solde à crédit",
            descriptionAr = "في 15 أكتوبر، باعت المقاولة بضائع بمبلغ 80,000 درهم (HT)، نسبة TVA هي 20% (المجموع TTC = 96,000 درهم). دفع الزبون النصف بشيك بنكي والباقي على الحساب (فاتورة رقم V-44).",
            dateExample = "15/10/N",
            documentRef = "Facture n° V-44 / Chèque n° 8871",
            lines = listOf(
                JournalEntryLine(
                    accountCode = "5141",
                    accountTitleFr = "Banques (soldes débiteurs)",
                    accountTitleAr = "البنوك (حساب جاري)",
                    debitAmount = 48000.0,
                    note = "نصف المبلغ TTC قبض في الحساب البنكي (+ مدين)"
                ),
                JournalEntryLine(
                    accountCode = "3421",
                    accountTitleFr = "Clients",
                    accountTitleAr = "الزبائن",
                    debitAmount = 48000.0,
                    note = "النصف المتبقي دين للمقاولة على الزبون (+ مدين)"
                ),
                JournalEntryLine(
                    accountCode = "7111",
                    accountTitleFr = "Ventes de marchandises au Maroc",
                    accountTitleAr = "مبيعات بضائع في المغرب",
                    creditAmount = 80000.0,
                    note = "إيراد البيع الصافي بدون احتساب الضريبة (HT)"
                ),
                JournalEntryLine(
                    accountCode = "4455",
                    accountTitleFr = "Etat, TVA facturée",
                    accountTitleAr = "الدولة - ضريبة على القيمة المضافة مفوترة",
                    creditAmount = 16000.0,
                    note = "الضريبة المحصلة من الزبون الواجب تسليمها للدولة (دائن)"
                )
            ),
            pedagogicalExplanationAr = "مبيعات البضائع إيراد يسجل في دائن الصنف 7 (7111). والـ TVA المحصلة دين للدولة على المقاولة في دائن الصنف 4 (4455). في المقابل زادت أموال البنك في المدين (5141) بنصف الـ TTC وزاد دين الزبائن في المدين (3421) بالنصف الآخر."
        ),
        JournalEntry(
            id = "entry_3",
            titleAr = "عملية 3: أداء مستحقات المورد عن طريق تحويل بنكي",
            titleFr = "Règlement d'une dette fournisseur par virement bancaire",
            descriptionAr = "في 20 أكتوبر، قامت المقاولة بسداد فاتورة المورد السابقة بمبلغ 60,000 درهم بتحويل بنكي مباشر (إشعار تحويل رقم VIR-209).",
            dateExample = "20/10/N",
            documentRef = "Avis de virement n° VIR-209",
            lines = listOf(
                JournalEntryLine(
                    accountCode = "4411",
                    accountTitleFr = "Fournisseurs",
                    accountTitleAr = "الموردون",
                    debitAmount = 60000.0,
                    note = "إلغاء وتصفير الدين تجاه المورد (- مدين)"
                ),
                JournalEntryLine(
                    accountCode = "5141",
                    accountTitleFr = "Banques",
                    accountTitleAr = "البنوك",
                    creditAmount = 60000.0,
                    note = "خروج الأموال ونقصان رصيد البنك (- دائن)"
                )
            ),
            pedagogicalExplanationAr = "الدين التزام خصوم؛ ونقصانه يسجل في المدين (4411). والبنك أصل نقدي؛ ونقصان رصيده بالتحويل يسجل في الدائن (5141). بذلك يُقفل حساب المورد للعملية السابقة."
        ),
        JournalEntry(
            id = "entry_4",
            titleAr = "عملية 4: قيد الأجور والرواتب والاقتطاعات الاجتماعية (CNSS)",
            titleFr = "Comptabilisation de la paie et des charges sociales",
            descriptionAr = "في 31 أكتوبر، أعدت المقاولة بيان أجور الشهر: الرواتب الإجمالية 40,000 درهم، اقتطاعات CNSS أجراء 2,500 درهم، الضريبة على الدخل IR محتجزة 3,500 درهم، وصافي الأجر يدفع بشيك بنكي.",
            dateExample = "31/10/N",
            documentRef = "Livre de paie - Mois d'Octobre",
            lines = listOf(
                JournalEntryLine(
                    accountCode = "61711",
                    accountTitleFr = "Appointements et salaires",
                    accountTitleAr = "الرواتب والتعويضات (الأجر الإجمالي Brut)",
                    debitAmount = 40000.0,
                    note = "التكلفة الإجمالية للأجور تسجل في مدين التكاليف"
                ),
                JournalEntryLine(
                    accountCode = "4441",
                    accountTitleFr = "C.N.S.S.",
                    accountTitleAr = "الصندوق الوطني للضمان الاجتماعي (حصة الأجراء)",
                    creditAmount = 2500.0,
                    note = "دين مستحق للصندوق الوطني للضمان الاجتماعي (دائن)"
                ),
                JournalEntryLine(
                    accountCode = "44525",
                    accountTitleFr = "Etat, I.R.",
                    accountTitleAr = "الدولة - الضريبة على الدخل محتجزة من المنبع",
                    creditAmount = 3500.0,
                    note = "دين مستحق لإدارة الضرائب محتجز من راتب الأجير (دائن)"
                ),
                JournalEntryLine(
                    accountCode = "5141",
                    accountTitleFr = "Banques",
                    accountTitleAr = "البنوك (صافي الأجور المدفوع للعمال Net)",
                    creditAmount = 34000.0,
                    note = "المبلغ الصافي المحول لحسابات الأجراء البنكية (40,000 - 2,500 - 3,500)"
                )
            ),
            pedagogicalExplanationAr = "التكلفة الإجمالية تسجل في مدين 61711. والخصومات من الأجر تصبح ديوناً على عاتق المقاولة تلتزم بدفعها للضمان الاجتماعي (4441) ولإدارة الضرائب (44525). وصافي ما يتبقى يخرج من البنك في الدائن (5141)."
        ),
        JournalEntry(
            id = "entry_5",
            titleAr = "عملية 5: قيد الاستهلاك السنوي لسيارات وشاحنات الشركة",
            titleFr = "Dotation annuelle aux amortissements du matériel de transport",
            descriptionAr = "في 31 دجنبر (أعمال نهاية السنة)، تم حساب قسط استهلاك سيارات وشاحنات التوزيع بمبلغ 25,000 درهم.",
            dateExample = "31/12/N",
            documentRef = "Tableau des amortissements N",
            lines = listOf(
                JournalEntryLine(
                    accountCode = "6193",
                    accountTitleFr = "D.E.A. des immobilisations corporelles",
                    accountTitleAr = "مخصصات الاستغلال لاستهلاكات الأصول المادية",
                    debitAmount = 25000.0,
                    note = "تكلفة استهلاك السنة تسجل في مدين الصنف 6"
                ),
                JournalEntryLine(
                    accountCode = "2834",
                    accountTitleFr = "Amortissements du matériel de transport",
                    accountTitleAr = "استهلاك معدات النقل والسيارات",
                    creditAmount = 25000.0,
                    note = "تراكم النقصان في قيمة الشاحنات يسجل في دائن الحساب 2834"
                )
            ),
            pedagogicalExplanationAr = "قيد تسوية بدون خروج سيولة نقدية: المقاولة تسجل عبء الاستهلاك في مدين التكاليف (6193) لتخفيض ربحها الخاضع للضريبة، وفي المقابل ترفع مجمع الاستهلاك في دائن الأصل (2834) الذي يطرح في الميزانية من قيمة الشاحنات الأصلية."
        )
    )

    val quizzes: List<QuizQuestion> = listOf(
        QuizQuestion(
            id = 1,
            classNumber = 1,
            questionAr = "ما هو الدور الأساسي لحسابات الصنف 1 (Financement permanent) في الميزانية؟",
            questionFr = "Quel est le rôle principal des comptes de la Classe 1 ?",
            optionsAr = listOf(
                "تسجيل المبيعات اليومية ونفقات الأجور",
                "تمثيل الموارد المالية الذاتية والديون طويلة الأجل المتاحة للمقاولة لأكثر من سنة",
                "تتبع ديون الزبائن وفواتير الكهرباء قصيرة الأجل",
                "إحصاء النقدية الموجودة في الصندوق فقط"
            ),
            correctIndex = 1,
            explanationAr = "الصنف 1 يضم الأموال الذاتية (رأس المال، الاحتياطيات) والديون طويلة الأجل (القروض البنكية لأكثر من سنة) التي تمول استثمارات المقاولة المستدامة."
        ),
        QuizQuestion(
            id = 2,
            classNumber = 2,
            questionAr = "أي من الحسابات التالية يعتبر أصلاً ثابتاً مادياً (Immobilisation corporelle)؟",
            questionFr = "Lequel de ces comptes est une immobilisation corporelle ?",
            optionsAr = listOf(
                "2111 مصاريف التأسيس",
                "2230 الأصل التجاري",
                "2332 عتاد ومعدات صناعية (Matériel et outillage)",
                "3111 بضائع في المستودع"
            ),
            correctIndex = 2,
            explanationAr = "الحساب 2332 يندرج تحت الباب 23 (الأصول المادية الملموسة). أما 2111 فهو أصل غير حقيقي (Non-valeurs) و 2230 أصل معنوي (Incorporelle)."
        ),
        QuizQuestion(
            id = 3,
            classNumber = 3,
            questionAr = "أين تظهر ديون الزبائن (Clients: 3421) في القوائم المالية وما طبيعة رصيدها؟",
            questionFr = "Où figure le compte 'Clients' (3421) et quel est son solde normal ?",
            optionsAr = listOf(
                "في الميزانية - الأصول المتداولة (Actif circulant)، ورصيدها مدين دائماً أو صفر",
                "في الميزانية - الخصوم المتداولة، ورصيدها دائن دائماً",
                "في قائمة التكاليف (CPC)، ورصيدها مدين",
                "في حسابات الخزينة (Trésorerie)"
            ),
            correctIndex = 0,
            explanationAr = "ديون الزبائن تمثل حقوقاً للمقاولة على عملائها تسدد في الأجل القصير، لذا تظهر في أصول الميزانية (Actif circulant: 3421) برصيد مدين طبيعي."
        ),
        QuizQuestion(
            id = 4,
            classNumber = 4,
            questionAr = "عند شراء بضائع على الحساب، أي حساب من الخصوم يسجل المبلغ الواجب أداؤه؟",
            questionFr = "Lors d'un achat à crédit, quel compte enregistre la dette envers le vendeur ?",
            optionsAr = listOf(
                "3421 الزبائن (Clients)",
                "4411 الموردون (Fournisseurs)",
                "1481 قروض لدى مؤسسات الائتمان",
                "5141 البنوك"
            ),
            correctIndex = 1,
            explanationAr = "الحساب 4411 (Fournisseurs) يمثل التزاماً قصير الأجل في خصوم الميزانية المتداولة تجاه من باع للمقاولة سلعاً أو خدمات."
        ),
        QuizQuestion(
            id = 5,
            classNumber = 5,
            questionAr = "ما هو الفرق بين الحساب 5141 (البنوك) والحساب 5161 (الصندوق)؟",
            questionFr = "Quelle est la différence entre le compte Banque (5141) et Caisse (5161) ?",
            optionsAr = listOf(
                "البنك يسجل المبيعات والصندوق يسجل المشتريات",
                "5141 يتبع الرصيد في الحساب البنكي، بينما 5161 يتبع السيولة الورقية والمعدنية في قاصة المقاولة",
                "الصندوق يظهر في الخصوم والبنك في الأصول",
                "لا يوجد أي فرق محاسبي بينهما"
            ),
            correctIndex = 1,
            explanationAr = "كلاهما من أصول الخزينة (الصنف 51)، لكن 5141 للأموال المودعة في الحساب البنكي، و5161 للنقود الملموسة الموجودة داخل صندوق المقاولة."
        ),
        QuizQuestion(
            id = 6,
            classNumber = 6,
            questionAr = "ماذا يعني وجود الرقم 9 في الخانة الثالثة لحسابات التكاليف مثل 6193؟",
            questionFr = "Que signifie le chiffre 9 en 3e position dans un compte de charge (ex: 619) ?",
            optionsAr = listOf(
                "أرباح استثنائية",
                "مخصصات الاستهلاك والمؤونات (Dotations aux amortissements et provisions)",
                "ضرائب على الدخل",
                "ديون مستحقة للدولة"
            ),
            correctIndex = 1,
            explanationAr = "في المخطط المحاسباتي المغربي، يشير الرقم 9 في الخانة الثالثة (619, 639, 659) دائماً إلى مخصصات الاستهلاك والمؤونات (Dotations)."
        ),
        QuizQuestion(
            id = 7,
            classNumber = 7,
            questionAr = "في أي قائمة مالية تسجل حسابات الصنف 7 (COMPTES DE PRODUITS)؟",
            questionFr = "Dans quel état financier sont regroupés les comptes de la classe 7 ?",
            optionsAr = listOf(
                "في أصول الميزانية (Actif du bilan)",
                "في خصوم الميزانية (Passif du bilan)",
                "في قائمة العائدات والتكاليف (C.P.C.) ضمن الإيرادات",
                "في الصنف 0 فقط"
            ),
            correctIndex = 2,
            explanationAr = "الصنف 7 يسجل كل إيرادات ومداخيل السنة في الطرف الدائن لحساب العائدات والتكاليف (C.P.C.)."
        ),
        QuizQuestion(
            id = 8,
            classNumber = 8,
            questionAr = "كيف تحسب القيمة المضافة (Valeur Ajoutée: 8140) في المخطط المحاسباتي المغربي؟",
            questionFr = "Comment est calculée la Valeur Ajoutée (8140) dans l'ESG ?",
            optionsAr = listOf(
                "مجموع المبيعات زائد القروض البنكية",
                "(الهامش الإجمالي + إنتاج السنة) ناقص استهلاكات السنة من الغير (Consommations)",
                "مجموع الرواتب ناقص الضرائب",
                "الأرباح الصافية زائد الاستهلاكات"
            ),
            correctIndex = 1,
            explanationAr = "القيمة المضافة = (Marge brute + Production de l'exercice) - Consommations en provenance des tiers."
        ),
        QuizQuestion(
            id = 9,
            classNumber = 9,
            questionAr = "ما هو الهدف الرئيسي من استخدام الصنف 9 (المحاسبة التحليلية)؟",
            questionFr = "Quel est le but principal de la classe 9 (Comptabilité analytique) ?",
            optionsAr = listOf(
                "تقديم الإقرار الضريبي الرسمي السنوي للدولة",
                "حساب تكلفة إنتاج كل سلعة وهامش ربحها وتوجيه قرارات الإدارة المالية داخلياً",
                "تسجيل أجور العمال الشهرية",
                "مراقبة دفتر الشيكات البنكية"
            ),
            correctIndex = 1,
            explanationAr = "الصنف 9 مخصص للمحاسبة التحليلية للتسيير (CAGE) لحساب أسعار التكلفة لكل منتج ومردودية الأقسام الداخلية."
        ),
        QuizQuestion(
            id = 10,
            classNumber = 0,
            questionAr = "لماذا لا تظهر حسابات الصنف 0 (Comptes spéciaux) في أرقام الميزانية الرسمية؟",
            questionFr = "Pourquoi les comptes de la classe 0 ne figurent pas dans les totaux du bilan ?",
            optionsAr = listOf(
                "لأنها مخصصة لحسابات الافتتاح والالتزامات المشروطة خارج الميزانية (Hors bilan)",
                "لأنها حسابات ملغاة قانونياً",
                "لأنها تخص الشركات الأجنبية فقط",
                "لأن رصيدها سلبي دائماً"
            ),
            correctIndex = 0,
            explanationAr = "الصنف 0 يضم حسابات فتح وإقفال الدفاتر والتزامات مشروطة (ضمانات بنكية، كفالات) لا تشكل أصولاً أو ديوناً مؤكدة في الذمة المالية."
        )
    )
}
