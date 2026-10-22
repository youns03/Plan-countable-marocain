package com.example.data

import com.example.model.AcronymInfo
import com.example.model.ComparisonItem
import com.example.model.ComparisonRow
import com.example.model.JournalEntry
import com.example.model.JournalEntryLine
import com.example.model.LearningLesson
import com.example.model.LexiconTerm
import com.example.model.QuizQuestion

object EducationalData {

    /**
     * First structured learning path for Comptabilité générale S1.
     * Account labels remain anchored in the supplied Plan Comptable Marocain PDF;
     * explanations and examples are educational content, not quotations from it.
     */
    val learningPath: List<LearningLesson> = listOf(
        LearningLesson(
            id = "s1-introduction",
            stageNumber = 1,
            titleAr = "اكتشاف المحاسبة",
            titleFr = "Comprendre la comptabilité",
            objectiveAr = "فهم لماذا تسجل المؤسسة عملياتها وكيف تتحول التسجيلات إلى معلومات مفيدة.",
            objectiveFr = "Comprendre pourquoi l'entreprise enregistre ses opérations et transforme ces enregistrements en informations.",
            explanationAr = "المحاسبة تقنية لتنظيم وتسجيل التدفقات والعمليات التي تقوم بها المؤسسة، ثم إعداد وثائق تلخص نشاطها ووضعيتها. في هذا المسار سننتقل من فهم العملية إلى اختيار الحساب ثم كتابة القيد.",
            explanationFr = "La comptabilité est une technique qui organise et enregistre les flux et les opérations de l'entreprise afin de produire des documents de synthèse. Le parcours va de la compréhension de l'opération au choix des comptes puis à l'écriture.",
            terms = listOf(
                "Entreprise" to "المؤسسة",
                "Opération" to "عملية",
                "Flux" to "تدفق",
                "Documents de synthèse" to "وثائق التركيب والتلخيص"
            ),
            exampleFr = "Une entreprise achète des fournitures et règle immédiatement le fournisseur.",
            exampleAr = "اشترت مؤسسة لوازم وأدت للمورد فورًا. سنحدد لاحقًا ما الذي دخل إلى المؤسسة وما الذي خرج منها.",
            reviewQuestionsAr = listOf("ما الهدف من تسجيل العمليات؟", "ما الفرق بين العملية والتدفق؟"),
            sourceLabel = "درس S1 تمهيدي + Cours de comptabilité générale، مع صياغة تعليمية مستقلة",
            sourceUrl = "https://mcours.net/cours/pdf/compta/Cours_de_comptabilite_generale.pdf"
        ),
        LearningLesson(
            id = "s1-bilan",
            stageNumber = 2,
            titleAr = "فهم الميزانية",
            titleFr = "Comprendre le bilan",
            objectiveAr = "تمييز الاستعمالات والموارد وربط الأصول بالخصوم.",
            objectiveFr = "Distinguer les emplois et les ressources et relier l'actif au passif.",
            explanationAr = "الميزانية جدول يصف وضعية المؤسسة في تاريخ محدد. الأصول تمثل ما تستعمله المؤسسة أو تملكه من ممتلكات وحقوق، والخصوم تمثل الموارد ومصادر التمويل والالتزامات. يجب أن يتساوى مجموع الأصول مع مجموع الخصوم.",
            explanationFr = "Le bilan présente la situation patrimoniale de l'entreprise à une date donnée. L'actif regroupe les emplois, biens et droits ; le passif regroupe les ressources et les dettes. Le total de l'actif est égal au total du passif.",
            terms = listOf(
                "Actif" to "الأصول / الاستعمالات",
                "Passif" to "الخصوم / الموارد",
                "Emploi" to "استعمال الموارد",
                "Ressource" to "مصدر التمويل"
            ),
            exampleFr = "Un terrain est un emploi : il figure à l'actif. Le capital est une ressource : il figure au passif.",
            exampleAr = "الأرض استعمال للموارد وتظهر في الأصول، بينما رأس المال مصدر تمويل ويظهر في الخصوم.",
            reviewQuestionsAr = listOf("ماذا يمثل الأكتيف؟", "لماذا يجب أن يتساوى مجموع الأصول والخصوم؟"),
            sourceLabel = "كتاب Plan comptable marocain + درس Bilan في مسار S1",
            sourceUrl = "https://www.youtube.com/watch?v=XwK_EbDQj0g"
        ),
        LearningLesson(
            id = "s1-plan",
            stageNumber = 3,
            titleAr = "قراءة دليل الحسابات المغربي",
            titleFr = "Lire le Plan comptable marocain",
            objectiveAr = "فهم الفئات والأقسام وعلاقة الحساب الأب بالحسابات الفرعية.",
            objectiveFr = "Comprendre les classes, les rubriques et la relation entre un compte parent et ses sous-comptes.",
            explanationAr = "يحتفظ التطبيق بالرقم والاسم الفرنسي كما وردا في كتاب Plan comptable marocain. مثلًا، 23 هو قسم الممتلكات الثابتة المادية، و231 هو قسم الأراضي، و2311 حساب فرعي للأراضي غير المبنية. الرقم لا يُفصل عن موقعه في الشجرة.",
            explanationFr = "L'application conserve le code et l'intitulé français du Plan comptable marocain. Par exemple, 23 désigne les immobilisations corporelles, 231 les terrains et 2311 les terrains nus. Le code doit être lu avec sa position dans l'arborescence.",
            terms = listOf(
                "Classe" to "الصنف",
                "Rubrique" to "القسم",
                "Compte" to "الحساب",
                "Sous-compte" to "الحساب الفرعي"
            ),
            exampleFr = "Classe 2 → 23 Immobilisations corporelles → 231 Terrains → 2311 Terrains nus.",
            exampleAr = "الصنف 2 ← 23 الممتلكات الثابتة المادية ← 231 الأراضي ← 2311 الأراضي غير المبنية.",
            reviewQuestionsAr = listOf("ما الفرق بين 231 و2311؟", "لماذا لا ندمج الحساب الأب مع الحساب الفرعي؟"),
            sourceLabel = "Plan comptable marocain، الصفحات 1 و3 من الملف المرفق",
            sourceUrl = null,
            relatedAccountCodes = listOf("2", "23", "231", "2311")
        ),
        LearningLesson(
            id = "s1-debit-credit",
            stageNumber = 4,
            titleAr = "المدين والدائن والقيود",
            titleFr = "Débit, Crédit et écritures comptables",
            objectiveAr = "تحليل العملية قبل توزيعها على المدين والدائن.",
            objectiveFr = "Analyser l'opération avant de la répartir entre débit et crédit.",
            explanationAr = "لا نختار المدين والدائن بحفظ جواب منفصل عن العملية. نحدد أولًا الحسابات التي تغيرت، ثم طبيعة كل حساب، ثم نسجل المبالغ في القيد مع التحقق من تساوي مجموع المدين والدائن.",
            explanationFr = "On ne choisit pas le débit et le crédit en mémorisant une réponse isolée. On identifie d'abord les comptes concernés, leur nature, puis on enregistre les montants en vérifiant l'égalité entre débit et crédit.",
            terms = listOf(
                "Débit" to "المدين",
                "Crédit" to "الدائن",
                "Écriture comptable" to "القيد المحاسبي",
                "Journal" to "اليومية"
            ),
            exampleFr = "L'achat comptant de fournitures augmente une charge et diminue la trésorerie : les deux comptes doivent être identifiés avant l'écriture.",
            exampleAr = "شراء لوازم نقدًا يزيد تكلفةً ويخفض الخزينة؛ نحدد الحسابين أولًا ثم نوزع المبلغ ونتحقق من التوازن.",
            reviewQuestionsAr = listOf("ما الخطوة التي تسبق اختيار المدين والدائن؟", "كيف نتحقق من توازن القيد؟"),
            sourceLabel = "Cours de comptabilité générale، فصول الحساب والقيد، بصياغة تعليمية",
            sourceUrl = "https://ofppt.info/comptabilite-generale-1/",
            relatedAccountCodes = listOf("6111", "7111", "3421", "4411")
        ),
        LearningLesson(
            id = "s1-operations",
            stageNumber = 5,
            titleAr = "العمليات والتطبيقات",
            titleFr = "Opérations et applications",
            objectiveAr = "الانتقال من فهم العملية إلى اختيار الحسابات وكتابة القيد.",
            objectiveFr = "Passer de la compréhension d'une opération au choix des comptes et à l'écriture.",
            explanationAr = "نتدرج من عمليات بسيطة إلى عمليات مركبة: شراء، بيع، أداء، أجور، وضريبة على القيمة المضافة. في كل حالة نقرأ نص العملية، نستخرج المعلومة المهمة، نحدد الحسابات، ثم نراجع القيد.",
            explanationFr = "On progresse des opérations simples vers les opérations composées : achats, ventes, règlements, salaires et TVA. Pour chaque cas, on lit l'énoncé, on extrait les informations, on choisit les comptes puis on contrôle l'écriture.",
            terms = listOf(
                "Achat" to "شراء",
                "Vente" to "بيع",
                "Règlement" to "أداء / تسوية",
                "TVA" to "الضريبة على القيمة المضافة"
            ),
            exampleFr = "Une vente à crédit oblige à distinguer le produit, la TVA facturée et la créance client.",
            exampleAr = "البيع بالأجل يتطلب التمييز بين الإيراد والضريبة المفوترة والحق المالي على الزبون.",
            reviewQuestionsAr = listOf("ما المعلومات التي نستخرجها من نص العملية؟", "لماذا قد تتضمن العملية أكثر من حسابين؟"),
            sourceLabel = "Cours opérations courantes وموارد Comptabilité générale S1",
            sourceUrl = "https://ofppt.info/comptabilite-generale-1/",
            relatedAccountCodes = listOf("3421", "4411", "4455", "34552", "5141")
        ),
        LearningLesson(
            id = "s1-french-exercises",
            stageNumber = 6,
            titleAr = "التدرب على الفرنسية المحاسبية",
            titleFr = "Exercices en français",
            objectiveAr = "فهم نص التمرين الفرنسي واستخراج المطلوب قبل الحل.",
            objectiveFr = "Comprendre l'énoncé français et identifier ce qui est demandé avant de résoudre.",
            explanationAr = "يتعلم الطالب قراءة الأفعال والمصطلحات التي تحدد المطلوب: enregistrer، calculer، établir، déterminer، justifier. نبدأ بتمييز المصطلحات، ثم نحدد العملية، ثم نكتب الحل ونشرح سبب كل حساب.",
            explanationFr = "L'étudiant apprend à repérer les verbes et les termes qui indiquent la consigne : enregistrer, calculer, établir, déterminer et justifier. On identifie les mots-clés, l'opération, puis on justifie chaque compte.",
            terms = listOf(
                "Enregistrer" to "تسجيل",
                "Calculer" to "حساب",
                "Établir" to "إعداد",
                "Justifier" to "تعليل"
            ),
            exampleFr = "Enregistrer l'achat, calculer la TVA et justifier le choix des comptes.",
            exampleAr = "سجّل عملية الشراء، احسب الضريبة على القيمة المضافة، وعلّل اختيار الحسابات.",
            reviewQuestionsAr = listOf("ما الفرق بين enregistrer وcalculer؟", "كيف نحدد المطلوب من نص التمرين؟"),
            sourceLabel = "مسار S1 المقترح اعتمادًا على موارد الدروس والتمارين",
            sourceUrl = "https://www.alcenter.ma/cours-eco/semestre-1_1/comptabilit%C3%A9-g%C3%A9n%C3%A9rale-s1"
        )
    )

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
                JournalEntryLine("6111", "Achats de marchandises", "مشتريات بضائع", debitAmount = 50000.0, note = "HT"),
                JournalEntryLine("34552", "Etat-TVA récupérable sur les charges", "الدولة - ضريبة قابلة للاسترجاع على التكاليف", debitAmount = 10000.0, note = "TVA 20%"),
                JournalEntryLine("4411", "Fournisseurs", "الموردون", creditAmount = 60000.0, note = "TTC")
            ),
            pedagogicalExplanationAr = "التكلفة تزيد في المدين (6111) بمبلغ HT. والضريبة المسترجعة حق للمقاولة في مدين الأصول (34552). والتزام المقاولة تجاه المورد يزيد في دائن الخصوم (4411) بمبلغ TTC كاملاً. توازن القيد: 50,000 + 10,000 = 60,000 درهم."
        ),
        JournalEntry(
            id = "entry_2",
            titleAr = "عملية 2: بيع بضائع مع قبض جزئي بالبنك والباقي على الحساب",
            titleFr = "Vente de marchandises avec règlement partiel banque et solde à crédit",
            descriptionAr = "في 15 أكتوبر، باعت المقاولة بضائع بمبلغ 80,000 درهم (HT)، نسبة TVA هي 20% (المجموع TTC = 96,000 درهم). دفع الزبون النصف بشيك بنكي والباقي على الحساب (فاتورة رقم V-44).",
            dateExample = "15/10/N",
            documentRef = "Facture n° V-44 / Chèque n° 8871",
            lines = listOf(
                JournalEntryLine("5141", "Banques (soldes débiteurs)", "البنوك (حساب جاري)", debitAmount = 48000.0, note = "50% TTC"),
                JournalEntryLine("3421", "Clients", "الزبائن", debitAmount = 48000.0, note = "50% TTC"),
                JournalEntryLine("7111", "Ventes de marchandises au Maroc", "مبيعات بضائع في المغرب", creditAmount = 80000.0, note = "HT"),
                JournalEntryLine("4455", "Etat, TVA facturée", "الدولة - ضريبة مفوترة", creditAmount = 16000.0, note = "TVA 20%")
            ),
            pedagogicalExplanationAr = "مبيعات البضائع إيراد يسجل في دائن الصنف 7 (7111). والـ TVA المحصلة دين للدولة على المقاولة في دائن الصنف 4 (4455). في المقابل زادت أموال البنك في المدين (5141) وزاد دين الزبائن في المدين (3421). المجموع: 96,000 مدين = 96,000 دائن."
        ),
        JournalEntry(
            id = "entry_3",
            titleAr = "عملية 3: أداء مستحقات المورد عن طريق تحويل بنكي",
            titleFr = "Règlement d'une dette fournisseur par virement bancaire",
            descriptionAr = "في 20 أكتوبر، قامت المقاولة بسداد فاتورة المورد السابقة بمبلغ 60,000 درهم بتحويل بنكي مباشر (إشعار تحويل رقم VIR-209).",
            dateExample = "20/10/N",
            documentRef = "Avis de virement n° VIR-209",
            lines = listOf(
                JournalEntryLine("4411", "Fournisseurs", "الموردون", debitAmount = 60000.0, note = "إنقاص الدين في المدين"),
                JournalEntryLine("5141", "Banques", "البنوك", creditAmount = 60000.0, note = "إنقاص رصيد البنك في الدائن")
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
                JournalEntryLine("61711", "Appointements et salaires", "الرواتب والتعويضات", debitAmount = 40000.0, note = "Salaire Brut"),
                JournalEntryLine("4441", "C.N.S.S.", "الصندوق الوطني للضمان الاجتماعي", creditAmount = 2500.0, note = "Cotisations salariales"),
                JournalEntryLine("44525", "Etat, I.R.", "الدولة - الضريبة على الدخل", creditAmount = 3500.0, note = "Retenue à la source"),
                JournalEntryLine("5141", "Banques", "البنوك", creditAmount = 34000.0, note = "Net payé par chèque")
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
                JournalEntryLine("6193", "D.E.A. des immobilisations corporelles", "مخصصات الاستغلال لاستهلاكات الأصول المادية", debitAmount = 25000.0, note = "Dotation de l'exercice"),
                JournalEntryLine("2834", "Amortissements du matériel de transport", "استهلاك معدات النقل والسيارات", creditAmount = 25000.0, note = "Cumul amortissement")
            ),
            pedagogicalExplanationAr = "قيد تسوية بدون خروج سيولة نقدية: المقاولة تسجل عبء الاستهلاك في مدين التكاليف (6193) لتخفيض ربحها الخاضع للضريبة، وفي المقابل ترفع مجمع الاستهلاك في دائن الأصل (2834) الذي يطرح في الميزانية من قيمة الشاحنات الأصلية."
        ),
        JournalEntry(
            id = "entry_6",
            titleAr = "عملية 6: شراء حواسيب للمكتب بشيك بنكي مع الضريبة",
            titleFr = "Acquisition d'ordinateurs de bureau par chèque bancaire",
            descriptionAr = "في 5 نونبر، اشترت المقاولة حواسيب وخوادم مكتبية بمبلغ 30,000 درهم (HT) خاضعة لـ TVA بنسبة 20% (6,000 درهم)، وسددت المبلغ كاملاً 36,000 درهم بشيك بنكي مسحوب على الفور.",
            dateExample = "05/11/N",
            documentRef = "Facture n° TECH-99 / Chèque n° 7761",
            lines = listOf(
                JournalEntryLine("2355", "Matériel informatique", "عتاد معلوماتي وحواسيب", debitAmount = 30000.0, note = "أصل ثابت مادي HT"),
                JournalEntryLine("34551", "Etat-TVA récupérable sur les immobilisations", "الدولة - ضريبة مسترجعة على الأصول الثابتة", debitAmount = 6000.0, note = "TVA récup/immo"),
                JournalEntryLine("5141", "Banques", "البنوك", creditAmount = 36000.0, note = "أداء فوري بشيك TTC")
            ),
            pedagogicalExplanationAr = "شراء الحواسيب ليس تكلفة جارية (صنف 6) بل استثمار دائم يسجل في الأصول الثابتة (2355). والضريبة على الأصول تسجل في حساب خاص (34551) يختلف عن ضريبة التكاليف (34552). والمبلغ الإجمالي يخرج من البنك (5141)."
        ),
        JournalEntry(
            id = "entry_7",
            titleAr = "عملية 7: إرجاع بضائع غير مطابقة للمورد مع فاتورة إنقاص (Avoir)",
            titleFr = "Retour de marchandises non conformes au fournisseur (Facture d'avoir)",
            descriptionAr = "في 18 أكتوبر، أرجعت المقاولة بضائع تالفة قيمتها 10,000 درهم (HT) إلى المورد، وتوصلت بفاتورة إنقاص (Avoir AV-12) مع استرجاع الـ TVA بنسبة 20%.",
            dateExample = "18/10/N",
            documentRef = "Facture d'avoir n° AV-12",
            lines = listOf(
                JournalEntryLine("4411", "Fournisseurs", "الموردون", debitAmount = 12000.0, note = "تخفيض الدين المستحق للمورد TTC"),
                JournalEntryLine("6111", "Achats de marchandises", "مشتريات بضائع", creditAmount = 10000.0, note = "عكس تكلفة المشتريات HT"),
                JournalEntryLine("34552", "Etat-TVA récupérable sur les charges", "الدولة - ضريبة قابلة للاسترجاع على التكاليف", creditAmount = 2000.0, note = "إلغاء الضريبة المسترجعة")
            ),
            pedagogicalExplanationAr = "قيد عكسي دقيق: دين المورد ينقص في المدين (4411) بمبلغ TTC، وتكلفة الشراء تنقص بتسجيلها في الدائن (6111) بمبلغ HT، والضريبة التي سجلت سابقاً كمسترجعة تلغى بتسجيلها في الدائن (34552)."
        ),
        JournalEntry(
            id = "entry_8",
            titleAr = "عملية 8: تكوين مؤونة لتدهور مستحقات زبون مشكوك فيه",
            titleFr = "Constitution d'une provision pour créance douteuse",
            descriptionAr = "في 31 دجنبر، تبيّن أن الزبون (كمال) يواجه صعوبات مالية، فقدرت المقاولة احتمال خسارة 40% من دينه البالغ 20,000 درهم، أي بمبلغ 8,000 درهم.",
            dateExample = "31/12/N",
            documentRef = "État des créances douteuses au 31/12",
            lines = listOf(
                JournalEntryLine("61964", "D.E.P. pour dépréciation des créances de l'actif circulant", "مخصصات الاستغلال لمؤونات تدهور ديون الأصول المتداولة", debitAmount = 8000.0, note = "التكلفة التقديرية المحتملة"),
                JournalEntryLine("3942", "Provisions pour dépréciation des clients et comptes rattachés", "مؤونات تدهور الزبائن والحسابات الملحقة", creditAmount = 8000.0, note = "مؤونة في دائن الصنف 39")
            ),
            pedagogicalExplanationAr = "تطبيق مبدأ الحيطة والحذر (Principe de prudence): المقاولة تسجل العبء المحتمل في مدين التكاليف (61964)، وتفتح حساب مؤونة ينتهي بالرقم 9 (3942) في الدائن ليطرح لاحقاً من قيمة دين الزبون في الميزانية."
        ),
        JournalEntry(
            id = "entry_9",
            titleAr = "عملية 9: قبول كمبيالة / ورقة تجارية واجبة الدفع للمورد",
            titleFr = "Acceptation d'une lettre de change au profit d'un fournisseur",
            descriptionAr = "في 22 أكتوبر، وافقت المقاولة على كمبيالة تجارية رقم LC-44 بمبلغ 45,000 درهم لصالح موردها تستحق الأداء بعد 60 يوماً.",
            dateExample = "22/10/N",
            documentRef = "Lettre de change n° LC-44",
            lines = listOf(
                JournalEntryLine("4411", "Fournisseurs", "الموردون (حساب عادي)", debitAmount = 45000.0, note = "إلغاء الدين في الحساب العادي"),
                JournalEntryLine("4415", "Fournisseurs - effets à payer", "الموردون - أوراق تجارية واجبة الدفع", creditAmount = 45000.0, note = "تحويل الدين إلى ورقة تجارية مؤكدة")
            ),
            pedagogicalExplanationAr = "عملية تبديل داخل نفس الصنف 4: الدين العادي للمورد يقفل في المدين (4411)، ويتحول إلى التزام تجاري مدعم بسند رسمي في الحساب 4415 في الدائن."
        ),
        JournalEntry(
            id = "entry_10",
            titleAr = "عملية 10: زيادة رأس مال الشركة بحصة نقدية مودعة في البنك",
            titleFr = "Augmentation du capital social par apport en numéraire",
            descriptionAr = "في 15 دجنبر، قرر الشركاء زيادة رأس مال الشركة بمبلغ 200,000 درهم تم إيداعها نقداً بالكامل في الحساب البنكي للشركة (عقد موثق).",
            dateExample = "15/12/N",
            documentRef = "Procès-verbal d'AGE / Avis de crédit banque",
            lines = listOf(
                JournalEntryLine("5141", "Banques", "البنوك (الحساب الجاري)", debitAmount = 200000.0, note = "دخول الأموال وزيادة رصيد البنك"),
                JournalEntryLine("1111", "Capital social", "رأس المال الاجتماعي", creditAmount = 200000.0, note = "زيادة الأموال الذاتية للشركة")
            ),
            pedagogicalExplanationAr = "أصل نقدي يزيد في المدين (5141) بمبلغ 200,000 درهم، وفي المقابل تزيد الأموال الذاتية والتمويل الدائم للشركة في دائن الصنف 1 (1111) بنفس المبلغ تماماً."
        )
    )

    val quizzes: List<QuizQuestion> = listOf(
        // Classe 1
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
            classNumber = 1,
            questionAr = "في أي جانب من الميزانية وبأي رصيد يظهر حساب رأس المال الاجتماعي (Capital social: 1111)؟",
            questionFr = "Dans quelle partie du bilan et avec quel solde figure le Capital social (1111) ?",
            optionsAr = listOf(
                "الميزانية - الخصوم (Passif)، برصيد دائن طبيعي",
                "الميزانية - الأصول (Actif)، برصيد مدين",
                "قائمة التكاليف (CPC)، برصيد مدين",
                "حسابات الخزينة، بدون رصيد"
            ),
            correctIndex = 0,
            explanationAr = "رأس المال هو التزام أصلي على الشركة تجاه مؤسسيها، لذا يظهر في خصوم الميزانية ضمن الأموال الذاتية برصيد دائن."
        ),
        QuizQuestion(
            id = 3,
            classNumber = 1,
            questionAr = "ما هو رقم الحساب المخصص للقروض البنكية طويلة الأجل لدى مؤسسات الائتمان؟",
            questionFr = "Quel est le numéro de compte des emprunts auprès des établissements de crédit ?",
            optionsAr = listOf("1481", "4411", "5141", "6141"),
            correctIndex = 0,
            explanationAr = "الحساب 1481 يندرج تحت الباب 14 (ديون التمويل) وهو مخصص للقروض البنكية التي تتجاوز مدة سدادها سنة واحدة."
        ),
        QuizQuestion(
            id = 4,
            classNumber = 1,
            questionAr = "إعانات الاستثمار التي تحصل عليها المقاولة لتمويل آلات مستدامة تسجل في الحساب:",
            questionFr = "Les subventions d'investissement reçues par l'entreprise sont enregistrées au compte :",
            optionsAr = listOf("1311 إعانات الاستثمار المتوصل بها", "7111 مبيعات بضائع", "3455 ضريبة مسترجعة", "6111 مشتريات بضائع"),
            correctIndex = 0,
            explanationAr = "الحساب 1311 يندرج تحت القسم 13 (الأموال الذاتية المماثلة Capitaux propres assimilés) في الصنف 1."
        ),

        // Classe 2
        QuizQuestion(
            id = 5,
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
            explanationAr = "الحساب 2332 يندرج تحت الباب 23 (الأصول المادية الملموسة). أما 2111 فهو أصل غير حقيقي و2230 أصل معنوي."
        ),
        QuizQuestion(
            id = 6,
            classNumber = 2,
            questionAr = "ماذا يعني وجود الرقم 8 في الخانة الثانية للصنف 2 (مثل 2834)؟",
            questionFr = "Que signifie le chiffre 8 en 2e position dans la classe 2 (ex: 2834) ?",
            optionsAr = listOf(
                "استهلاك متراكم للأصل المقابل (Amortissement)",
                "إيراد بيع الأصل",
                "قرض لشراء الأصل",
                "ضريبة على الأصل"
            ),
            correctIndex = 0,
            explanationAr = "في الصنف 2، الحسابات التي تبدأ بـ 28 تمثل دائماً استهلاك الأصل المقابل (فمثلاً 2834 استهلاك الحساب 2340 معدات النقل)."
        ),
        QuizQuestion(
            id = 7,
            classNumber = 2,
            questionAr = "سندات المساهمة في شركات أخرى للسيطرة أو التأثير الدائم تسجل في الحساب:",
            questionFr = "Les titres de participation sont comptabilisés dans le compte :",
            optionsAr = listOf("2510", "3500", "5141", "7111"),
            correctIndex = 0,
            explanationAr = "الحساب 2510 يندرج تحت الأصول المالية (25 Immobilisations financières) لأكثر من سنة، بخلاف سندات التوظيف قصيرة الأجل (3500)."
        ),
        QuizQuestion(
            id = 8,
            classNumber = 2,
            questionAr = "حساب مصاريف التأسيس (2111) يندرج تحت أي قسم من الأصول الثابتة؟",
            questionFr = "Le compte Frais préliminaires (2111) relève de quelle rubrique ?",
            optionsAr = listOf(
                "21 الأصول غير الحقيقية (Immobilisations en non-valeurs)",
                "22 الأصول المعنوية",
                "23 الأصول المادية",
                "24 الأصول المالية"
            ),
            correctIndex = 0,
            explanationAr = "مصاريف التأسيس ونفقات رفع رأس المال تسجل في القسم 21 لأنها ليست أصولاً قابلة للبيع ولا تملك قيمة تجارية ذاتية."
        ),

        // Classe 3
        QuizQuestion(
            id = 9,
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
            explanationAr = "ديون الزبائن تمثل حقوقاً للمقاولة على عملائها تسدد في الأجل القصير، لذا تظهر في أصول الميزانية برصيد مدين طبيعي."
        ),
        QuizQuestion(
            id = 10,
            classNumber = 3,
            questionAr = "ما هو الحساب المخصص لمخزون البضائع في المستودعات داخل الصنف 3؟",
            questionFr = "Quel est le compte de stocks de marchandises dans la classe 3 ?",
            optionsAr = listOf("3111", "6111", "7111", "4411"),
            correctIndex = 0,
            explanationAr = "3111 هو مخزون البضائع (أصل)، بينما 6111 هو مشتريات بضائع (تكلفة) و7111 مبيعات بضائع (إيراد)."
        ),
        QuizQuestion(
            id = 11,
            classNumber = 3,
            questionAr = "الضريبة على القيمة المضافة المسترجعة على التكاليف تسجل في الحساب:",
            questionFr = "La TVA récupérable sur les charges est enregistrée au compte :",
            optionsAr = listOf("34552", "4455", "4456", "6111"),
            correctIndex = 0,
            explanationAr = "الحساب 34552 يمثل حقاً للمقاولة لاسترجاع مبالغ الضريبة من الدولة، لذا فهو أصل متداول في الصنف 34."
        ),
        QuizQuestion(
            id = 12,
            classNumber = 3,
            questionAr = "سندات وقيم التوظيف (TVP) المقتناة للمضاربة وتحقيق ربح سريع في البورصة تسجل تحت:",
            questionFr = "Les Titres et Valeurs de Placement (TVP) sont enregistrés sous la rubrique :",
            optionsAr = listOf("35 سندات وقيم التوظيف", "25 سندات مساهمة", "51 الخزينة", "11 رأس المال"),
            correctIndex = 0,
            explanationAr = "القسم 35 مخصص للأسهم والسندات المشتراة لغرض توظيف السيولة الفائضة لمدة قصيرة تقل عن سنة."
        ),

        // Classe 4
        QuizQuestion(
            id = 13,
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
            id = 14,
            classNumber = 4,
            questionAr = "ما هو الحساب المخصص للضريبة على القيمة المضافة المفوترة على الزبائن؟",
            questionFr = "Quel est le compte de la TVA facturée aux clients ?",
            optionsAr = listOf("4455", "3455", "6111", "5141"),
            correctIndex = 0,
            explanationAr = "4455 (Etat, TVA facturée) دين في دائن الخصوم المتداولة لأن المقاولة ملزمة بتسليم هذه الضريبة لإدارة الضرائب."
        ),
        QuizQuestion(
            id = 15,
            classNumber = 4,
            questionAr = "ديون المقاولة المستحقة للصندوق الوطني للضمان الاجتماعي (CNSS) تسجل في الحساب:",
            questionFr = "Les dettes envers la C.N.S.S. sont comptabilisées dans le compte :",
            optionsAr = listOf("4441 C.N.S.S.", "6171 أجور", "3421 زبائن", "1481 قروض"),
            correctIndex = 0,
            explanationAr = "الحساب 4441 يندرج تحت الباب 444 (Organismes sociaux) ويمثل مستحقات اشتراكات الضمان الاجتماعي الواجب سدادها."
        ),
        QuizQuestion(
            id = 16,
            classNumber = 4,
            questionAr = "الكمبيالات والأوراق التجارية المسحوبة على المقاولة والواجبة الدفع للموردين تسجل في الحساب:",
            questionFr = "Les effets de commerce à payer aux fournisseurs sont inscrits au compte :",
            optionsAr = listOf("4415 Fournisseurs - effets à payer", "3425 زبائن كمبيالات", "5141 بنوك", "4411 موردون عاديون"),
            correctIndex = 0,
            explanationAr = "الحساب 4415 يفصل الديون الموثقة بسندات تجارية (كمبيالة/سند لأمر) عن الحساب الجاري المفتوح للمورد 4411."
        ),

        // Classe 5
        QuizQuestion(
            id = 17,
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
            explanationAr = "كلاهما من أصول الخزينة (الصنف 51)، لكن 5141 للأموال المودعة في الحساب البنكي، و5161 للنقود الملموسة داخل الصندوق."
        ),
        QuizQuestion(
            id = 18,
            classNumber = 5,
            questionAr = "عندما يكون الحساب البنكي مكشوفاً بسحب مالي مرخص (Facilité de caisse / Découvert)، أين يسجل؟",
            questionFr = "Où est comptabilisé un solde bancaire créditeur (découvert autorisé) ?",
            optionsAr = listOf(
                "5541 بنوك بسحب دائن (Trésorerie - Passif)",
                "5141 بنوك رصيد مدين",
                "1481 قروض طويلة الأجل",
                "6141 مصاريف بنكية"
            ),
            correctIndex = 0,
            explanationAr = "السحب البنكي على المكشوف دين نقدي قصير جداً يسجل في خزينة الخصوم (5541 Banques - soldes créditeurs)."
        ),
        QuizQuestion(
            id = 19,
            classNumber = 5,
            questionAr = "الشيكات البنكية المستلمة من الزبائن وفي انتظار إرسالها للتحصيل في البنك تقيد في الحساب:",
            questionFr = "Les chèques reçus des clients en portefeuille sont inscrits au compte :",
            optionsAr = listOf("51111 Chèques en portefeuille", "3421 زبائن", "5141 بنوك", "7111 مبيعات"),
            correctIndex = 0,
            explanationAr = "الحساب 51111 يستقبل الشيك الورقي من الزبون قبل قيده في الحساب البنكي الرسمي للمقاولة."
        ),
        QuizQuestion(
            id = 20,
            classNumber = 5,
            questionAr = "حساب التحويلات المالية الداخلية (Virements de fonds: 5900) يعتبر حساباً:",
            questionFr = "Le compte 5900 'Virements de fonds' est un compte :",
            optionsAr = listOf(
                "حساب وسيط تقني لتفادي تكرار القيد ويجب أن يقفل برصيد صفر",
                "حساب تكلفة بنكية",
                "حساب ربح دائم",
                "حساب دين للدولة"
            ),
            correctIndex = 0,
            explanationAr = "الحساب 5900 يستعمل كوسيط عند سحب أموال من البنك وإيداعها في الصندوق، لتفادي تسجيل القيد مرتين، ويجب أن يكون رصيده صفراً دائماً."
        ),

        // Classe 6
        QuizQuestion(
            id = 21,
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
            explanationAr = "في المخطط المحاسباتي المغربي، يشير الرقم 9 في الخانة الثالثة (619, 639, 659) دائماً إلى مخصصات الاستهلاك والمؤونات."
        ),
        QuizQuestion(
            id = 22,
            classNumber = 6,
            questionAr = "ما هو الحساب المخصص لتسجيل مصاريف إيجار وكراء المحلات والمخازن؟",
            questionFr = "Quel compte enregistre les charges de locations et charges locatives ?",
            optionsAr = listOf("6131", "6111", "2321", "4411"),
            correctIndex = 0,
            explanationAr = "الحساب 6131 يندرج تحت الخدمات الخارجية (Autres charges externes: 613/614)."
        ),
        QuizQuestion(
            id = 23,
            classNumber = 6,
            questionAr = "ما طبيعة رصيد الحساب 6119 (التخفيضات التجارية المحصل عليها على مشتريات البضائع)؟",
            questionFr = "Quelle est la nature du solde du compte 6119 (RRR obtenus sur achats) ?",
            optionsAr = listOf(
                "رصيد دائن استثنائي (يطرح من مشتريات الصنف 6)",
                "رصيد مدين دائماً",
                "رصيد متغير",
                "بدون رصيد"
            ),
            correctIndex = 0,
            explanationAr = "قاعدة الرقم 9 في الخانة الرابعة: يسجل في الاتجاه المعاكس لحسابات الصنف 6 لإنقاص عبء المشتريات (رصيد دائن)."
        ),
        QuizQuestion(
            id = 24,
            classNumber = 6,
            questionAr = "الفوائد البنكية ومصاريف خدمة القروض التي تدفعها المقاولة للبنك تسجل في الحساب:",
            questionFr = "Les intérêts des emprunts et dettes sont comptabilisés en :",
            optionsAr = listOf("6311 Charges d'intérêts", "6111 مشتريات", "5141 بنوك", "1481 قروض"),
            correctIndex = 0,
            explanationAr = "فوائد القروض تكلفة مالية تسجل في الباب 63 (Charges financières) في الحساب 6311."
        ),

        // Classe 7
        QuizQuestion(
            id = 25,
            classNumber = 7,
            questionAr = "في أي قائمة مالية وبأي رصيد تسجل حسابات الصنف 7 (COMPTES DE PRODUITS)؟",
            questionFr = "Dans quel état financier et quel solde sont inscrits les comptes de la classe 7 ?",
            optionsAr = listOf(
                "قائمة العائدات والتكاليف (C.P.C.) ضمن الإيرادات برصيد دائن",
                "الميزانية - الأصول برصيد مدين",
                "الميزانية - الخصوم برصيد مدين",
                "خارج الميزانية فقط"
            ),
            correctIndex = 0,
            explanationAr = "الصنف 7 يسجل كل إيرادات ومداخيل السنة في الطرف الدائن لحساب العائدات والتكاليف (C.P.C.)."
        ),
        QuizQuestion(
            id = 26,
            classNumber = 7,
            questionAr = "ما هو الحساب المخصص لمبيعات البضائع داخل المغرب في الصنف 7؟",
            questionFr = "Quel compte enregistre les ventes de marchandises au Maroc ?",
            optionsAr = listOf("7111", "6111", "3111", "3421"),
            correctIndex = 0,
            explanationAr = "الحساب 7111 هو حساب إيرادات المبيعات الرئيسية للمقاولات التجارية بالمغرب."
        ),
        QuizQuestion(
            id = 27,
            classNumber = 7,
            questionAr = "ماذا يعني وجود الرقم 9 في الخانة الثالثة للصنف 7 مثل 7194؟",
            questionFr = "Que signifie le chiffre 9 en 3e position dans la classe 7 (ex: 719) ?",
            optionsAr = listOf(
                "استرجاع المؤونات السابقة (Reprises sur amortissements et provisions)",
                "مخصصات تكاليف جديدة",
                "مبيعات استثنائية",
                "ضرائب مؤجلة"
            ),
            correctIndex = 0,
            explanationAr = "في الصنف 7، يشير الرقم 9 في الخانة الثالثة (719, 739, 759) دائماً إلى استرجاع المؤونات السابقة وإلغائها بعد زوال الخطر."
        ),
        QuizQuestion(
            id = 28,
            classNumber = 7,
            questionAr = "أرباح بيع شاحنة أو عقار من الأصول الثابتة (Produits de cessions des immobilisations) تسجل في الحساب:",
            questionFr = "Les produits des cessions d'immobilisations sont enregistrés au compte :",
            optionsAr = listOf("7512 / 7513", "7111", "6111", "5141"),
            correctIndex = 0,
            explanationAr = "بيع أصل ثابت ليس نشاط استغلال عادي، لذا يسجل كإيراد غير جارٍ (Produit non courant: 751)."
        ),

        // Classe 8
        QuizQuestion(
            id = 29,
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
            id = 30,
            classNumber = 8,
            questionAr = "ما هو المؤشر المحاسبي الذي يقيس الهامش التجاري الإجمالي للشركات التجارية؟",
            questionFr = "Quel solde mesure la marge brute commerciale dans l'ESG ?",
            optionsAr = listOf(
                "8110 Marge brute",
                "8140 Valeur ajoutée",
                "8171 EBE",
                "8800 Résultat net"
            ),
            correctIndex = 0,
            explanationAr = "الحساب 8110 يحسب الهامش التجاري: مبيعات البضائع (711) ناقص تكلفة شراء البضائع المبيعة (611)."
        ),
        QuizQuestion(
            id = 31,
            classNumber = 8,
            questionAr = "فائض الاستغلال الإجمالي (Excédent Brut d'Exploitation: 8171) يعبر عن:",
            questionFr = "L'E.B.E. (8171) exprime :",
            optionsAr = listOf(
                "السيولة التشغيلية الصافية الناتجة عن نشاط المقاولة قبل التمويل والاستهلاك والضرائب",
                "مجموع ديون المقاولة",
                "رأس مال الشركة الجديد",
                "قيمة العقارات المباعة"
            ),
            correctIndex = 0,
            explanationAr = "E.B.E هو المورد النقدي الأساسي الذي تولده دورة الاستغلال لسداد الديون ومكافأة رأس المال ودفع الضرائب."
        ),
        QuizQuestion(
            id = 32,
            classNumber = 8,
            questionAr = "النتيجة الصافية للسنة المحاسبية (الربح النهائي القابل للتوزيع) تسجل في الحساب:",
            questionFr = "Le résultat net de l'exercice est centralisé dans le compte :",
            optionsAr = listOf("8800 Résultat net de l'exercice", "8100", "8110", "1111"),
            correctIndex = 0,
            explanationAr = "الحساب 8800 يجمع خلاصة جميع إيرادات وتكاليف السنة ويحدد الربح أو الخسارة الصافية."
        ),

        // Classe 9
        QuizQuestion(
            id = 33,
            classNumber = 9,
            questionAr = "ما هو الهدف الأساسي من الصنف 9 (المحاسبة التحليلية للتسيير CAGE)؟",
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
            id = 34,
            classNumber = 9,
            questionAr = "ما هو الدور الرئيسي لحسابات القسم 90 (الحسابات المنعكسة Comptes réfléchis)؟",
            questionFr = "Quel est le rôle des comptes réfléchis (Rubrique 90) ?",
            optionsAr = listOf(
                "استنساخ تكاليف وإيرادات المحاسبة العامة في النظام التحليلي دون تغيير الحسابات الرسمية",
                "دفع الضرائب",
                "حساب الاستهلاك",
                "تسجيل رأس المال"
            ),
            correctIndex = 0,
            explanationAr = "تعمل كمرآة تنقل الأرقام من الصنفين 6 و7 إلى الصنف 9 للتحليل المستقل."
        ),
        QuizQuestion(
            id = 35,
            classNumber = 9,
            questionAr = "الحسابات المخصصة لمراكز التكلفة والأقسام الإنتاجية (Sections analytiques) تندرج تحت:",
            questionFr = "Les sections analytiques relèvent de quelle rubrique ?",
            optionsAr = listOf("92 أقسام التكلفة (Sections analytiques)", "90", "98", "99"),
            correctIndex = 0,
            explanationAr = "القسم 92 يوزع التكاليف غير المباشرة على الورشات والأقسام الإدارية والتجارية."
        ),
        QuizQuestion(
            id = 36,
            classNumber = 9,
            questionAr = "حساب سعر التكلفة الإجمالي للمنتجات المبيعة (Coût de revient des produits vendus) يندرج تحت:",
            questionFr = "Le coût de revient des produits vendus est regroupé sous :",
            optionsAr = listOf("95 تكاليف العودة وسعر التكلفة", "91", "93", "90"),
            correctIndex = 0,
            explanationAr = "القسم 95 مخصص لحساب سعر التكلفة النهائي لكل منتج لمقارنته بسعر البيع."
        ),

        // Classe 0
        QuizQuestion(
            id = 37,
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
        ),
        QuizQuestion(
            id = 38,
            classNumber = 0,
            questionAr = "الحساب المستعمل في 1 يناير لإعادة فتح أرصدة الأصول والخصوم من ميزانية العام الماضي هو:",
            questionFr = "Le compte utilisé pour la réouverture des comptes de bilan au 1er janvier est :",
            optionsAr = listOf("01 ميزانية الافتتاح (Bilan d'ouverture)", "1111", "5141", "6111"),
            correctIndex = 0,
            explanationAr = "القسم 01 يستعمل كوسيط تقني لإعادة قيد الأرصدة الافتتاحية في أول يوم من السنة المالية."
        ),
        QuizQuestion(
            id = 39,
            classNumber = 0,
            questionAr = "الكفالات والضمانات البنكية الممنوحة من المقاولة لضمان صفقات تجارية تسجل تحت:",
            questionFr = "Les avals et cautions donnés par l'entreprise relèvent de :",
            optionsAr = listOf(
                "03 التزامات معطاة خارج الميزانية (Engagements donnés)",
                "4411 موردون",
                "1481 قروض",
                "6131 كراء"
            ),
            correctIndex = 0,
            explanationAr = "القسم 03 مخصص للضمانات والتعهدات التي قدمتها المقاولة للغير والتي قد تتحول إلى التزام مالي في حال عجز الطرف المكفول."
        ),
        QuizQuestion(
            id = 40,
            classNumber = 0,
            questionAr = "عقود الإيجار المالي والتأجير التمويلي (Crédit-bail / Leasing) تسجل التزاماتها المتبقية في الصنف:",
            questionFr = "Les engagements de crédit-bail restant à courir sont enregistrés en classe :",
            optionsAr = listOf(
                "05 التزامات التأجير التمويلي (Engagements sur opérations de crédit-bail)",
                "23 معدات",
                "61 تكاليف",
                "51 سيولة"
            ),
            correctIndex = 0,
            explanationAr = "الأصل المؤجر بالليزينغ لا تملكه المقاولة قانونياً حتى سداد القسط الأخير، لذا تسجل أقساطه المتبقية خارج الميزانية في القسم 05."
        )
    )
}
