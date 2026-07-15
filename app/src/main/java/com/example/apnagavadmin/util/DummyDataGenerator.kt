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
            val villages = listOf(
                Village("village_maharajganj", LocalizedString("Maharajganj", "महाराजगंज"), LocalizedString("Ram Sewak", "राम सेवक"), "9876543210", LocalizedString("Raebareli", "रायबरेली"), LocalizedString("Uttar Pradesh", "उत्तर प्रदेश"), "229101", 26.38, 81.25, "https://picsum.photos/id/10/400/300"),
                Village("village_rampur", LocalizedString("Rampur", "रामपुर"), LocalizedString("Sunita Devi", "सुनीता देवी"), "9876543211", LocalizedString("Ghazipur", "गाज़ीपुर"), LocalizedString("Uttar Pradesh", "उत्तर प्रदेश"), "233001", 25.58, 83.57, "https://picsum.photos/id/11/400/300")
            )

            villages.forEach { village ->
                val vId = village.id
                villageRepo.updateVillage(village)

                // 1. Labour Hub
                labourRepo.saveProvider(vId, LabourProvider("l1_$vId", LocalizedString("Anil Kumar", "अनिल कुमार"), "9876543201", "5", LocalizedString("Main Market", "मुख्य बाज़ार"), LocalizedString("₹500/day", "₹500/दिन"), LocalizedString("Plumbing", "प्लंबिंग"), "", vId, "plumber"))
                labourRepo.saveProvider(vId, LabourProvider("l2_$vId", LocalizedString("Suresh Mistri", "सुरेश मिस्त्री"), "9876543202", "10", LocalizedString("Near School", "स्कूल के पास"), LocalizedString("₹800/day", "₹800/दिन"), LocalizedString("Rajmistri", "राजमिस्त्री"), "", vId, "rajmistri"))
                labourRepo.saveProvider(vId, LabourProvider("l3_$vId", LocalizedString("Mahesh", "महेश"), "9876543203", "3", LocalizedString("Panchayat Bhawan", "पंचायत भवन"), LocalizedString("₹600/day", "₹600/दिन"), LocalizedString("Electrician", "इलेक्ट्रीशियन"), "", vId, "electrician"))

                // 2. Construction Hub
                constructionRepo.saveHub(vId, ConstructionHub("c1_$vId", LocalizedString("Bricks", "ईंटें"), LocalizedString("Durga Bricks", "दुर्गा ब्रिक्स"), "9123456701", LocalizedString("NH-24 Road", "NH-24 रोड"), listOf(
                    ConstructionProduct(LocalizedString("Lal Eint", "लाल ईंट"), "7", LocalizedString("Piece", "पीस")),
                    ConstructionProduct(LocalizedString("Peeli Eint", "पीली ईंट"), "6", LocalizedString("Piece", "पीस"))
                ), "", vId, "bricks"))
                
                constructionRepo.saveHub(vId, ConstructionHub("c2_$vId", LocalizedString("Hardware", "हार्डवेयर"), LocalizedString("Singh Hardware", "सिंह हार्डवेयर"), "9123456702", LocalizedString("Market Road", "बाज़ार रोड"), listOf(
                    ConstructionProduct(LocalizedString("Cement (ACC)", "सीमेंट (ACC)"), "410", LocalizedString("Bag", "बोरी")),
                    ConstructionProduct(LocalizedString("Sariya (TATA)", "सरिया (टाटा)"), "74", LocalizedString("Kg", "किलो"))
                ), "", vId, "hardware_shops"))

                // 3. Transport Hub
                transportRepo.saveHub(vId, TransportHub("t1_$vId", LocalizedString("Ram Singh", "राम सिंह"), LocalizedString("Tractor with Trolley", "ट्रॉली के साथ ट्रैक्टर"), "9234567801", LocalizedString("Near Pond", "पोखरे के पास"), "", vId, "tractor"))
                transportRepo.saveHub(vId, TransportHub("t2_$vId", LocalizedString("Shyamu Loader", "श्यामु लोडर"), LocalizedString("Tata Ace", "टाटा एस"), "9234567803", LocalizedString("Market Yard", "मार्केट यार्ड"), "", vId, "loader"))
                transportRepo.saveHub(vId, TransportHub("t3_$vId", LocalizedString("JCB Earthmover", "JCB अर्थमूवर"), LocalizedString("JCB 3DX", "जेसीबी 3DX"), "9234567804", LocalizedString("Industrial Area", "औद्योगिक क्षेत्र"), "", vId, "jcb"))

                // 4. Mandi Prices
                mandiRepo.savePrice(vId, MandiPrice("m1_$vId", LocalizedString("Wheat (Gehun)", "गेहूं"), 2450.0, LocalizedString("1 Quintal", "1 क्विंटल"), LocalizedString(), "", LocalizedString(), System.currentTimeMillis(), "stable", vId, "prices"))
                mandiRepo.savePrice(vId, MandiPrice("m2_$vId", LocalizedString("Tomato", "टमाटर"), 35.0, LocalizedString("1 Kg", "1 किलो"), LocalizedString(), "", LocalizedString(), System.currentTimeMillis(), "down", vId, "market"))
                mandiRepo.savePrice(vId, MandiPrice("m3_$vId", LocalizedString("Vegetables", "सब्जियां"), 0.0, LocalizedString(), LocalizedString("Ramchand Buyer", "रामचंद खरीदार"), "9345678901", LocalizedString("Maharajganj", "महाराजगंज"), System.currentTimeMillis(), "stable", vId, "buyers"))

                // 5. Health Hub
                healthRepo.saveHub(vId, HealthHub("h1_$vId", LocalizedString("Dr. Anil Sharma", "डॉ. अनिल शर्मा"), LocalizedString("Raebareli Road", "रायबरेली रोड"), "9456789001", LocalizedString("General Physician", "सामान्य चिकित्सक"), LocalizedString("09:30AM - 04:00PM", "09:30AM - 04:00PM"), LocalizedString(), LocalizedString(), LocalizedString(), "https://picsum.photos/seed/doc/200", vId, "doctors"))
                healthRepo.saveHub(vId, HealthHub("h2_$vId", LocalizedString("Ambulance", "एम्बुलेंस"), LocalizedString("Emergency Seva", "आपातकालीन सेवा"), "102", LocalizedString(), LocalizedString("24 Hours", "24 घंटे"), LocalizedString(), LocalizedString(), LocalizedString(), "", vId, "ambulance"))

                // 6. News & Notifications
                newsRepo.saveNews(vId, News("n1_$vId", LocalizedString("Government Increases MSP", "सरकार ने MSP बढ़ाई"), LocalizedString("The central government announced a 10% increase in MSP for Kharif crops this season.", "केंद्र सरकार ने इस सीजन में खरीफ फसलों के लिए एमएसपी में 10% की वृद्धि की घोषणा की।"), "https://picsum.photos/seed/news1/800/400", System.currentTimeMillis(), vId, "news"))
                newsRepo.saveNotification(vId, AppNotification("not1_$vId", LocalizedString("Vaccination Camp", "टीकाकरण शिविर"), LocalizedString("Free Polio vaccination tomorrow at school.", "कल स्कूल में मुफ्त पोलियो टीकाकरण।"), System.currentTimeMillis(), vId))

                // 7. Banners
                newsRepo.saveBanner(vId, Banner("b1_$vId", "https://picsum.photos/seed/veg/400/200", LocalizedString("Fresh Vegetables", "ताजी सब्जियां"), "20", "https://example.com", vId))

                // 8. Family Functions
                familyRepo.saveHub(vId, FamilyFunctionHub("f1_$vId", LocalizedString("Baba Amarnath Tent House", "बाबा अमरनाथ टेंट हाउस"), LocalizedString("Kalyanpur (Near Block Office)", "कल्याणपुर (ब्लॉक ऑफिस के पास)"), "9567890101", LocalizedString("Waterproof Pandal, Light & Seating", "वाटरप्रूफ पंडाल, लाइट और बैठने की व्यवस्था"), LocalizedString("₹15,000", "₹15,000"), "", vId, "tent"))
                familyRepo.saveHub(vId, FamilyFunctionHub("f2_$vId", LocalizedString("Shree Ram Flowers & Decorators", "श्री राम फ्लावर्स एंड डेकोरेटर्स"), LocalizedString("Rampur Village (Near Shiv Mandir)", "रामपुर गांव (शिव मंदिर के पास)"), "9567890102", LocalizedString("Stage & Gate Floral Decoration", "स्टेज और गेट फूलों की सजावट"), LocalizedString("₹5,000", "₹5,000"), "", vId, "tent"))
                familyRepo.saveHub(vId, FamilyFunctionHub("f3_$vId", LocalizedString("Gupta Ji Halwai", "गुप्ता जी हलवाई"), LocalizedString("Main Market", "मुख्य बाजार"), "9567890103", LocalizedString("Pure Veg Catering, Special Sweets", "शुद्ध शाकाहारी खान-पान, विशेष मिठाइयाँ"), LocalizedString("₹250/Plate", "₹250/प्लेट"), "", vId, "catering"))
                familyRepo.saveHub(vId, FamilyFunctionHub("f4_$vId", LocalizedString("Digital Photo Studio", "डिजिटल फोटो स्टूडियो"), LocalizedString("Bus Stand Road", "बस स्टैंड रोड"), "9567890104", LocalizedString("HD Photography & Cinematic Video", "HD फोटोग्राफी और सिनेमैटिक वीडियो"), LocalizedString("₹10,000", "₹10,000"), "", vId, "photo"))
                familyRepo.saveHub(vId, FamilyFunctionHub("f5_$vId", LocalizedString("Rocking Star DJ", "रॉकिंग स्टार डीजे"), LocalizedString("Station Road", "स्टेशन रोड"), "9567890105", LocalizedString("High Bass Sound, LED Lights", "हाई बेस साउंड, एलईडी लाइट्स"), LocalizedString("₹8,000", "₹8,000"), "", vId, "dj"))
                familyRepo.saveHub(vId, FamilyFunctionHub("f6_$vId", LocalizedString("Panchayat Bhawan Lawn", "पंचायत भवन लॉन"), LocalizedString("Civil Lines", "सिविल लाइन्स"), "9567890106", LocalizedString("Large Open Area, Parking Available", "बड़ा खुला क्षेत्र, पार्किंग उपलब्ध"), LocalizedString("₹20,000", "₹20,000"), "", vId, "marriage_halls"))
            }
        }
    }
}
