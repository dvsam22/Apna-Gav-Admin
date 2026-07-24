package com.example.apnagavadmin.util

import com.example.apnagavadmin.data.model.*
import com.example.apnagavadmin.data.repository.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DummyDataGenerator {
    private val villageRepo = VillageRepository()
    private val labourRepo = LabourRepository()
    private val constructionRepo = ConstructionRepository()
    private val transportRepo = TransportRepository()
    private val mandiRepo = MandiRepository()
    private val healthRepo = HealthRepository()
    private val newsRepo = NewsBannerRepository()
    private val familyRepo = FamilyFunctionRepository()

    fun generateAllData() {
        CoroutineScope(Dispatchers.IO).launch {
            val commonDist = LocalizedString("Maharajganj", "महाराजगंज")
            val commonState = LocalizedString("Uttar Pradesh", "उत्तर प्रदेश")
            val commonPin = "273310"
            val commonLat = 26.9975
            val commonLng = 83.4634

            val villageRawData = listOf(
                listOf("Ahirauli", "अहीरौली", "GANESH PRASAD", "9415814562"),
                listOf("Allahabad", "इलाहाबाद", "DHARMENDRA", "8565974389"),
                listOf("Arbarahawa", "अरबरहवा", "", ""),
                listOf("Aurahia", "औरहिया", "BHAGIRATHI", "9839333131"),
                listOf("Ausani", "औसानी", "ANEETA DEVI", "9984262746"),
                listOf("Baharampur", "बहरामपुर", "BATTISHA DEVI", "9450512948"),
                listOf("Baida", "बैदा", "KAILASH PATI", "9453117247"),
                listOf("Baiju Dehara", "बैजू डेहरा", "OMKAR SINGH", "9648139363"),
                listOf("Bairia", "बैरिया", "", ""),
                listOf("Barbas", "बरबस", "", ""),
                listOf("Barhawa Chandan Chakhi", "बरहवा चंदन चाखी", "", ""),
                listOf("Barwar", "बरवार", "CHANDRA BHUSHAN SINGH", "9648273759"),
                listOf("Basdila", "बसडीला", "VIRENDRA KUMAR", "8574288840"),
                listOf("Belawa Araji", "बेलवा आराजी", "", ""),
                listOf("Beltikara", "बेलटीकरा", "SURSATI", "9559451157"),
                listOf("Beniganj", "बेनीगंज", "DURGAWATI DEVI", "9919731171"),
                listOf("Bharara", "भरारा", "", ""),
                listOf("Bhawanipur", "भवनीपुर", "ASHA", "8874153260"),
                listOf("Bindawalia", "बिंदवलिया", "", ""),
                listOf("Bishunpura", "बिशुनपुरा", "MAHESH", "9795192165"),
                listOf("Bramhpur", "ब्रह्मपुर", "RAMLALIT", "9918268498"),
                listOf("Chandan Chaki", "चंदन चाकी", "RAM JATAN", "9838209523"),
                listOf("Chunawatia", "चुनवतिया", "JAMALUDDIN", "9839033381"),
                listOf("Damari Giri", "दमरी गिरी", "", ""),
                listOf("Devipur", "देवीपुर", "GAYTRI DEVI", "9839160235"),
                listOf("Dhankhari", "धनखरी", "ANJU", "8948592166"),
                listOf("Diguri", "डिगुरी", "ARCHANA", "9919504904"),
                listOf("Domara", "डोमरा", "RAMDAWAN", "9651842776"),
                listOf("Gangi", "गंगी", "SHRAWAN", "9838588700"),
                listOf("Gehuana", "गेहुआना", "RAJKUMAR", "9956821883"),
                listOf("Girgitia", "गिरगिटिया", "NOORTARA", "8948597838"),
                listOf("Gonaha", "गोनहा", "INDRAJEET SINGH", "9838482227"),
                listOf("Harakhpura", "हरखपुरा", "RAMANAND", "9415039173"),
                listOf("Hariramphr", "हरिरामपुर", "RAMESH", "8726333798"),
                listOf("Haskhori", "हसखोरी", "MURATI", "7408335848"),
                listOf("Hemchhapar", "हेमछापर", "MEWA DEVI", "9450711192"),
                listOf("Jarar", "जरार", "HEMLATA", "8948444652"),
                listOf("Jungal Baki Tukara No14", "जंगल बाकी टुकड़ा नं. 14", "LALJEE", "9984943820"),
                listOf("Jungal Barahara", "जंगल बरहारा", "SARITA DEVI", "9918752568"),
                listOf("Jungal Jaralaha Urf Anantpur", "जंगल जरलाहा उर्फ अनंतपुर", "RAJMATI", "9984581653"),
                listOf("Jungal Jaralaha Urf Araji", "जंगल जरलाहा उर्फ आराजी", "", ""),
                listOf("Jungal Jaralaha Urf Barhra", "जंगल जरलाहा उर्फ बरहरा", "RADHESHYAM", "9956436499"),
                listOf("Jungal Jaralaha Urf Kanawa Doy", "जंगल जरलाहा उर्फ कनवा दोय", "", ""),
                listOf("Jungal Jaralaha Urf Kanawa I", "जंगल जरलाहा उर्फ कनवा प्रथम", "", ""),
                listOf("Jungal Jaralaha Urf Tendulahiy", "जंगल जरलाहा उर्फ टेंडुलहिया", "SANTEERA", "8009555401"),
                listOf("Jungal Jaralahi Urf Jardi", "जंगल जरलाही उर्फ जरदी", "AWADHESH", "9452309964"),
                listOf("Jungal Jarlaha Urf Suchitpur", "जंगल जरलाहा उर्फ सुचितपुर", "GANGA", "9559952274"),
                listOf("Kamasin Buzurg", "कमासिन बुजुर्ग", "MAHENDRA", "9721095392"),
                listOf("Kamasin Khurd", "कमासिन खुर्द", "DILIP", "9838418382"),
                listOf("Kamata Buzurg", "कमता बुजुर्ग", "PRAMOD", "8874697542"),
                listOf("Kamata Khurd", "कमता खुर्द", "", ""),
                listOf("Kanaila", "कनैला", "GOVINDRI", "7388003403"),
                listOf("Khaincha", "खैंचा", "JAYPRAKASH", "9918027432"),
                listOf("Khajuria", "खजुरिया", "MO SARIF", "9838162886"),
                listOf("Kuana Chap", "कुआना छाप", "KALPNATH", "9792142829"),
                listOf("Lalakarpur", "लालकारपुर", "", ""),
                listOf("Laxmipur", "लक्ष्मीपुर", "KANTI", "9621408050"),
                listOf("Madhonagar", "माधोनगर", "MEGHANATH", "8127670280"),
                listOf("Mahdeia", "महदेइया", "GYANMATI", "9198718284"),
                listOf("Mahuawa", "महुआवा", "DEENA NATH", "9628412478"),
                listOf("Mansurganj", "मंसूरगंज", "AKSHAI BER", "9554555396"),
                listOf("Maulaganj", "मौलागंज", "DHANESH", "8948303771"),
                listOf("Mithaura", "मिठौरा", "URMILA", "9792049230"),
                listOf("Mohiuddinpur", "मोहीउद्दीनपुर", "YOGENDRA YADAV", "8009990720"),
                listOf("Mujuri", "मुजुरी", "LILAWATI", "9839270371"),
                listOf("Murila Chaudhari", "मुरीला चौधरी", "CHANDRIKA SINGH", "7525857035"),
                listOf("Nabiganj", "नबीगंज", "", ""),
                listOf("Narakataha", "नरकटहा", "SONMATI", "9415393802"),
                listOf("Nasirabad", "नासिराबाद", "", ""),
                listOf("Newas Pokhar", "नेवास पोखर", "VINDRAWATI", "9670569886"),
                listOf("Paniyara", "पनियरा", "AMRAWATI PAL", "9936703967"),
                listOf("Pipara Baksh", "पिपरा बख्श", "", ""),
                listOf("Pipara Dargah", "पिपरा दरगाह", "", ""),
                listOf("Pipara Khurd", "पिपरा खुर्द", "RAMSUNDER", "9793893045"),
                listOf("Piparia", "पिपरिया", "GEETA", "9792513851"),
                listOf("Rajaura Kala", "रजौरा कला", "MOHARABANI", "9956359410"),
                listOf("Rajaura Khurd", "रजौरा खुर्द", "MOHARBANI", "9956359410"),
                listOf("Rajaura Panjum", "रजौरा पंजुम", "UDAYRAJ", "9565936103"),
                listOf("Rajmandir", "राजमंदिर", "JAGDEESH", "9839429518"),
                listOf("Ramnagar", "रामनगर", "KALAWATI", "9415921548"),
                listOf("Rampur", "रामपुर", "MANISHA", "9838764240"),
                listOf("Rampur Bodarahia", "रामपुर बोदरहिया", "", ""),
                listOf("Ranipur", "रानीपुर", "AMARJEET", "9936723778"),
                listOf("Rapur Khurd", "रापुर खुर्द", "RAMAWADH", "9919653248"),
                listOf("Ratanpurawa", "रतनपुरवा", "BHAGAWAT", "9864555701"),
                listOf("Rudalapur", "रुदलापुर", "SATYAPAL", "9450879468"),
                listOf("Satguru", "सतगुरु", "SUNEETA", "7379232567"),
                listOf("Sauraha", "सौराहा", "POONAM", "9919284889"),
                listOf("Shikarpur", "शिकारपुर", "", ""),
                listOf("Soharauna Tiwari", "सोहरौना तिवारी", "MANOJ PASWAN", "9721861011"),
                listOf("Sohas Khas", "सोहस खास", "ANSUIYA", "8574205117"),
                listOf("Sonabarasa", "सोनबरसा", "RAMPRASAD", "9198916389"),
                listOf("Uska", "उस्का", "RAMBHAROSH", "9721328721")
            )

            villageRawData.forEach { data ->
                val enName = data[0]
                val hiName = data[1]
                val pradhanName = data[2].ifEmpty { "Not found" }
                val pradhanPhone = data[3].ifEmpty { "000000000" }
                val vId = "v_${enName.lowercase().replace(" ", "_").replace("(", "").replace(")", "")}"

                val village = Village(
                    id = vId,
                    villageName = LocalizedString(enName, hiName),
                    sarpanchName = LocalizedString(pradhanName, if(pradhanName == "Not found") "नहीं मिला" else pradhanName),
                    sarpanchPhone = pradhanPhone,
                    district = commonDist,
                    state = commonState,
                    pincode = commonPin,
                    lat = commonLat,
                    lng = commonLng,
                    image = "https://picsum.photos/seed/$vId/400/300",
                    isActive = true
                )

                villageRepo.updateVillage(village)

                // 1. Labour Hub (Multiple categories)
                labourRepo.saveProvider(vId, LabourProvider("l1_$vId", LocalizedString("Mistri Ji", "मिस्त्री जी"), "9876543201", "5", LocalizedString("Village Square", "गांव का चौराहा"), LocalizedString("₹700/day", "₹700/दिन"), LocalizedString("Rajmistri", "राजमिस्त्री"), "", vId, "rajmistri"))
                labourRepo.saveProvider(vId, LabourProvider("l2_$vId", LocalizedString("Ramesh Plumber", "रमेश प्लंबर"), "9876543202", "3", LocalizedString("Near Tank", "टंकी के पास"), LocalizedString("₹500/day", "₹500/दिन"), LocalizedString("Plumbing", "प्लंबिंग"), "", vId, "plumber"))
                labourRepo.saveProvider(vId, LabourProvider("l3_$vId", LocalizedString("Shyamu Bijliwala", "श्यामु बिजलीवाला"), "9876543203", "4", LocalizedString("Main Market", "मुख्य बाज़ार"), LocalizedString("₹600/day", "₹600/दिन"), LocalizedString("Electrician", "इलेक्ट्रीशियन"), "", vId, "electrician"))
                labourRepo.saveProvider(vId, LabourProvider("l4_$vId", LocalizedString("Tarkeshwar Carpenter", "तारकेश्वर बढ़ई"), "9876543204", "10", LocalizedString("Near Mandir", "मंदिर के पास"), LocalizedString("₹800/day", "₹800/दिन"), LocalizedString("Wood Work", "लकड़ी का काम"), "", vId, "carpenter"))
                labourRepo.saveProvider(vId, LabourProvider("l5_$vId", LocalizedString("Master Tailor", "मास्टर टेलर"), "9876543205", "15", LocalizedString("Bazar", "बाज़ार"), LocalizedString("₹300/Suit", "₹300/सूट"), LocalizedString("Stitching", "सिलाई"), "", vId, "tailor"))
                labourRepo.saveProvider(vId, LabourProvider("l6_$vId", LocalizedString("Hardworking Labour", "मेहनती मजदूर"), "9876543206", "2", LocalizedString("Anywhere", "कहीं भी"), LocalizedString("₹400/day", "₹400/दिन"), LocalizedString("Helping", "सहायता"), "", vId, "labour"))
                
                // 2. Construction Hub (Multiple categories)
                constructionRepo.saveHub(vId, ConstructionHub("c1_$vId", LocalizedString("Local Bricks", "स्थानीय ईंटें"), LocalizedString("Sharma Bricks", "शर्मा ब्रिक्स"), "9123456701", LocalizedString("Main Road", "मुख्य सड़क"), listOf(
                    ConstructionProduct(LocalizedString("Lal Eint", "लाल ईंट"), "7", LocalizedString("Piece", "पीस"))
                ), "", vId, "bricks"))
                constructionRepo.saveHub(vId, ConstructionHub("c2_$vId", LocalizedString("Global Hardware", "ग्लोबल हार्डवेयर"), LocalizedString("Singh Hardware", "सिंह हार्डवेयर"), "9123456702", LocalizedString("Bazar Road", "बाज़ार रोड"), listOf(
                    ConstructionProduct(LocalizedString("Cement", "सीमेंट"), "450", LocalizedString("Bag", "बोरी")),
                    ConstructionProduct(LocalizedString("Sariya", "सरिया"), "70", LocalizedString("Kg", "किलो"))
                ), "", vId, "hardware_shops"))
                constructionRepo.saveHub(vId, ConstructionHub("c3_$vId", LocalizedString("Material Center", "मटेरियल सेंटर"), LocalizedString("Gupta Materials", "गुप्ता मटेरियल"), "9123456703", LocalizedString("Near Canal", "नहर के पास"), listOf(
                    ConstructionProduct(LocalizedString("Morang", "मोरंग"), "80", LocalizedString("CFT", "CFT")),
                    ConstructionProduct(LocalizedString("Gitti", "गिट्टी"), "90", LocalizedString("CFT", "CFT"))
                ), "", vId, "material_shops"))

                // 3. Transport Hub (Multiple categories)
                transportRepo.saveHub(vId, TransportHub("t1_$vId", LocalizedString("Tractor Seva", "ट्रैक्टर सेवा"), LocalizedString("Tractor with Trolley", "ट्रॉली के साथ ट्रैक्टर"), "9234567801", LocalizedString("Near Pond", "पोखरे के पास"), "", vId, "tractor"))
                transportRepo.saveHub(vId, TransportHub("t2_$vId", LocalizedString("Car for Rent", "किराये की कार"), LocalizedString("Swift Dzire", "स्विफ्ट डिजायर"), "9234567802", LocalizedString("Main Stand", "मेन स्टैंड"), "", vId, "car"))
                transportRepo.saveHub(vId, TransportHub("t3_$vId", LocalizedString("Pickup Truck", "पिकअप ट्रक"), LocalizedString("Bolero Pickup", "बोलेरो पिकअप"), "9234567803", LocalizedString("Market", "बाज़ार"), "", vId, "pickup"))
                transportRepo.saveHub(vId, TransportHub("t4_$vId", LocalizedString("Loader Service", "लोडर सेवा"), LocalizedString("Tata Ace", "टाटा एस"), "9234567804", LocalizedString("Bypass", "बायपास"), "", vId, "loader"))
                transportRepo.saveHub(vId, TransportHub("t5_$vId", LocalizedString("JCB Earthmover", "जेसीबी अर्थमूवर"), LocalizedString("JCB 3DX", "जेसीबी 3DX"), "9234567805", LocalizedString("Industrial Area", "औद्योगिक क्षेत्र"), "", vId, "jcb"))

                // 4. Mandi Prices (Multiple categories)
                mandiRepo.savePrice(vId, MandiPrice("m1_$vId", LocalizedString("Wheat (Gehun)", "गेहूं"), 2450.0, LocalizedString("1 Quintal", "1 क्विंटल"), LocalizedString(), "", LocalizedString(), System.currentTimeMillis(), "stable", vId, "prices"))
                mandiRepo.savePrice(vId, MandiPrice("m2_$vId", LocalizedString("Potato", "आलू"), 1200.0, LocalizedString("1 Quintal", "1 क्विंटल"), LocalizedString(), "", LocalizedString(), System.currentTimeMillis(), "up", vId, "market"))
                mandiRepo.savePrice(vId, MandiPrice("m3_$vId", LocalizedString("Rice (Chawal)", "चावल"), 3500.0, LocalizedString("1 Quintal", "1 क्विंटल"), LocalizedString("Kisan Trading", "किसान ट्रेडिंग"), "9345678901", LocalizedString("Mandi Gate", "मंडी गेट"), System.currentTimeMillis(), "stable", vId, "buyers"))

                // 5. Health Hub (Multiple categories)
                healthRepo.saveHub(vId, HealthHub("h1_$vId", LocalizedString("Dr. Sharma", "डॉ. शर्मा"), LocalizedString("Main Chauraha", "मुख्य चौराहा"), "9456789001", LocalizedString("General Physician", "सामान्य चिकित्सक"), LocalizedString("10AM - 5PM", "10AM - 5PM"), LocalizedString(), LocalizedString(), LocalizedString(), "", vId, "doctors"))
                healthRepo.saveHub(vId, HealthHub("h2_$vId", LocalizedString("Village Clinic", "ग्राम क्लिनिक"), LocalizedString("Panchayat Bhawan", "पंचायत भवन"), "9456789002", LocalizedString(), LocalizedString("24 Hours", "24 घंटे"), LocalizedString("Government", "सरकारी"), LocalizedString("General Checkup", "सामान्य जांच"), LocalizedString(), "", vId, "hospitals"))
                healthRepo.saveHub(vId, HealthHub("h3_$vId", LocalizedString("Life Medicals", "लाइफ मेडिकल्स"), LocalizedString("Near Hospital", "अस्पताल के पास"), "9456789003", LocalizedString(), LocalizedString("8AM - 10PM", "8AM - 10PM"), LocalizedString(), LocalizedString(), LocalizedString("All Medicines", "सभी दवाएं"), "", vId, "pharmacy"))
                healthRepo.saveHub(vId, HealthHub("h4_$vId", LocalizedString("Ambulance", "एम्बुलेंस"), LocalizedString("Emergency Seva", "आपातकालीन सेवा"), "102", LocalizedString(), LocalizedString("24 Hours", "24 घंटे"), LocalizedString(), LocalizedString(), LocalizedString(), "", vId, "ambulance"))
                healthRepo.saveHub(vId, HealthHub("h5_$vId", LocalizedString("Police Station", "थाना"), LocalizedString("Town Area", "टाउन एरिया"), "112", LocalizedString(), LocalizedString("24 Hours", "24 घंटे"), LocalizedString(), LocalizedString(), LocalizedString(), "", vId, "police"))

                // 6. News & Notifications (Multiple categories)
                newsRepo.saveNews(vId, News("n1_$vId", LocalizedString("Village Cleanliness Drive", "गांव स्वच्छता अभियान"), LocalizedString("Join us tomorrow at 8 AM for village cleaning.", "कल सुबह 8 बजे गांव की सफाई के लिए हमसे जुड़ें।"), "", System.currentTimeMillis(), vId, "news"))
                newsRepo.saveNews(vId, News("n2_$vId", LocalizedString("Vaccination Notice", "टीकाकरण सूचना"), LocalizedString("Polio drops will be given on Sunday at school.", "रविवार को स्कूल में पोलियो की दवा पिलाई जाएगी।"), "", System.currentTimeMillis(), vId, "notice"))

                // 7. Banners (Multiple categories)
                newsRepo.saveBanner(vId, Banner("b1_$vId", "https://picsum.photos/seed/b1_$vId/800/400", LocalizedString("Fresh Vegetables Offer", "ताजी सब्जियों पर ऑफर"), "20% OFF", "", vId))
                newsRepo.saveBanner(vId, Banner("b2_$vId", "https://picsum.photos/seed/b2_$vId/800/400", LocalizedString("New Health Camp", "नया स्वास्थ्य शिविर"), "FREE", "", vId))

                // 8. Family Functions (Multiple categories)
                familyRepo.saveHub(vId, FamilyFunctionHub("f1_$vId", LocalizedString("Village Tent House", "गांव टेंट हाउस"), LocalizedString("Market", "बाजार"), "9567890101", LocalizedString("Basic Pandal & Lights", "बेसिक पंडाल और लाइट"), LocalizedString("₹5,000", "₹5,000"), "", vId, "tent"))
                familyRepo.saveHub(vId, FamilyFunctionHub("f2_$vId", LocalizedString("Desi Catering", "देसी कैटरिंग"), LocalizedString("Near Canal", "नहर के पास"), "9567890102", LocalizedString("Halwai & Helpers", "हलवाई और हेल्पर"), LocalizedString("₹200/Plate", "₹200/प्लेट"), "", vId, "catering"))
                familyRepo.saveHub(vId, FamilyFunctionHub("f3_$vId", LocalizedString("Smile Photo Studio", "स्माइल फोटो स्टूडियो"), LocalizedString("Bus Stand", "बस स्टैंड"), "9567890103", LocalizedString("Full HD Video & Photos", "फुल एचडी वीडियो और फोटो"), LocalizedString("₹8,000", "₹8,000"), "", vId, "photo"))
                familyRepo.saveHub(vId, FamilyFunctionHub("f4_$vId", LocalizedString("DJ Rakesh", "डीजे राकेश"), LocalizedString("Main Chauraha", "मुख्य चौराहा"), "9567890104", LocalizedString("Sound System & Lights", "साउंड सिस्टम और लाइट"), LocalizedString("₹6,000", "₹6,000"), "", vId, "dj"))
                familyRepo.saveHub(vId, FamilyFunctionHub("f5_$vId", LocalizedString("Marriage Lawn", "मैरिज लॉन"), LocalizedString("Highway", "हाईवे"), "9567890105", LocalizedString("Full Area for 500 People", "500 लोगों के लिए पूरी जगह"), LocalizedString("₹25,000", "₹25,000"), "", vId, "marriage_halls"))
            }
        }
    }
}
