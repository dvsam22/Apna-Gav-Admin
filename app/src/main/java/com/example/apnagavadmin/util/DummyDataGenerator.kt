package com.example.apnagavadmin.util

import com.example.apnagavadmin.data.model.*
import com.example.apnagavadmin.data.repository.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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
                listOf("Brahmpur", "ब्रह्मपुर", "RAMLALIT", "9918268498"),
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
                listOf("Harirampur", "हरिरामपुर", "RAMESH", "8726333798"),
                listOf("Haskhori", "हसखोरी", "MURATI", "7408335848"),
                listOf("Hemchhapar", "हेमछापर", "MEWA DEVI", "9450711192"),
                listOf("Jarar", "जरार", "HEMLATA", "8948444652"),
                listOf("Jungal Baki Tukda No 14", "जंगल बाकी टुकड़ा नं. 14", "LALJEE", "9984943820"),
                listOf("Jungal Barahara", "जंगल बरहारा", "SARITA DEVI", "9918752568"),
                listOf("Jungal Jaralaha Urf Anantpur", "जंगल जरलाहा उर्फ अनंतपुर", "RAJMATI", "9984581653"),
                listOf("Jungal Jaralaha Urf Araji", "जंगल जरलाहा उर्फ आराजी", "", ""),
                listOf("Jungal Jaralaha Urf Barhara", "जंगल जरलाहा उर्फ बरहरा", "RADHESHYAM", "9956436499"),
                listOf("Jungal Jaralaha Urf Kanawa Doy", "जंगल जरलाहा उर्फ कनवा दोय", "", ""),
                listOf("Jungal Jaralaha Urf Kanawa I", "जंगल जरलाहा उर्फ कनवा प्रथम", "", ""),
                listOf("Jungal Jaralaha Urf Tendulahia", "जंगल जरलाहा उर्फ टेंडुलहिया", "SANTEERA", "8009555401"),
                listOf("Jungal Jaralaha Urf Jardi", "जंगल जरलाहा उर्फ जरदी", "AWADHESH", "9452309964"),
                listOf("Jungal Jaralaha Urf Suchitpur", "जंगल जरलाहा उर्फ सुचितपुर", "GANGA", "9559952274"),
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
                listOf("Rampur Khurd", "रामपुर खुर्द", "RAMAWADH", "9919653248"),
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
                // Only process Ausani village; skip all other villages so server/production data is untouched
                if (!enName.equals("Ausani", ignoreCase = true)) return@forEach

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
                    image = if (enName.equals("Ausani", ignoreCase = true)) "https://images.unsplash.com/photo-1542601906990-b4d3fb778b09?w=800&auto=format&fit=crop" else "https://picsum.photos/seed/$vId/400/300",
                    isActive = true
                )

                villageRepo.updateVillage(village)

                // Add real data ONLY for Ausani village
                if (enName.equals("Ausani", ignoreCase = true)) {
                    // Clear old fake/sample data in Firestore for Ausani before inserting real data
                    val firestore = com.google.firebase.firestore.FirebaseFirestore.getInstance()
                    val collectionsToClear = listOf("labour", "construction", "transport", "health", "family_functions", "mandi", "news", "banners", "notifications")
                    for (col in collectionsToClear) {
                        try {
                            val snapshot = firestore.collection("villages").document(vId).collection(col).get().await()
                            snapshot.documents.forEach { doc ->
                                doc.reference.delete().await()
                            }
                        } catch (e: Exception) {
                            android.util.Log.e("DummyDataGenerator", "Error clearing $col for $vId: ${e.message}")
                        }
                    }

                    // 1. Labour Board (Self-added first-person entries for Ausani with realistic rural dialogue)
                    val realLabour = listOf(
                        LabourProvider("l1_$vId", LocalizedString("Pintu Paswan", "पिंटू पासवान"), "8601150422", "5", LocalizedString("Ausani", "औसानी"), LocalizedString("₹700 per day (ya call karke tay hoga)", "₹700 प्रति दिन (या कॉल करके तय होगा)"), LocalizedString("Main lakdi ka har tarah ka kaam karta hoon. Ek baar mauka de ke dekhiye bhaiya, aapka kaam 1 number hoga!", "मैं लकड़ी का हर तरह का काम करता हूँ। एक बार मौका दे के देखिये भैया, आपका काम 1 नंबर होगा!"), "", vId, "carpenter"),
                        LabourProvider("l2_$vId", LocalizedString("Umesh Paswan", "उमेश पासवान"), "9621923332", "5", LocalizedString("Ausani", "औसानी"), LocalizedString("Call karke rate fix kar lein", "कॉल करके रेट तय कर लें"), LocalizedString("Main kapda silne ka kaam karta hoon. Ek baar silwa ke dekhein bhaiya, fitting 1 number milegi!", "मैं कपड़ा सिलने का काम करता हूँ। एक बार सिलवा के देखें भैया, फिटिंग 1 नंबर मिलेगी!"), "", vId, "tailor"),
                        LabourProvider("l3_$vId", LocalizedString("Sangam", "संगम"), "9839405430", "3", LocalizedString("Ausani", "औसानी"), LocalizedString("Phone par baat karke tay hoga", "फोन पर बातचीत करके तय होगा"), LocalizedString("Main JCB chalane ka kaam karta hoon. Ek baar mauka de ke dekhiye bhaiya, dhalaai aur khudai 1 number hogi!", "मैं जेसीबी (JCB) चलाने का काम करता हूँ। एक बार मौका दे के देखिये भैया, ढाई और खुदाई 1 नंबर होगी!"), "", vId, "labour"),
                        LabourProvider("l4_$vId", LocalizedString("Vishal Paswan", "विशाल पासवान"), "9935483218", "4", LocalizedString("Ausani", "औसानी"), LocalizedString("Kaam dekh kar rate batayenge", "काम देखकर रेट बताएंगे"), LocalizedString("Main electric aur bijli ka pura kaam karta hoon. Bijli ka koi bhi kaam ho bhaiya, 1 call karein kaam top class hoga!", "मैं इलेक्ट्रिक और बिजली का पूरा काम करता हूँ। बिजली का कोई भी काम हो भैया, 1 कॉल करें काम टॉप क्लास होगा!"), "", vId, "electrician"),
                        LabourProvider("l5_$vId", LocalizedString("Bageshwar", "बागेश्वर"), "9305006617", "10", LocalizedString("Ausani", "औसानी"), LocalizedString("₹700 per day (baat kar lein)", "₹700 प्रति दिन (बात कर लें)"), LocalizedString("Main lakdi ka pura kaam karta hoon. Chhat, darwaza, khidki sab badiya banayenge, ek baar sewa ka mauka dein!", "मैं लकड़ी का पूरा काम करता हूँ। छत, दरवाजा, खिड़की सब बढ़िया बनाएंगे, एक बार सेवा का मौका दें!"), "", vId, "carpenter"),
                        LabourProvider("l6_$vId", LocalizedString("Chandan", "चंदन"), "8766475181", "2", LocalizedString("Ausani", "औसानी"), LocalizedString("₹400 per day (ya kaam ke hisab se)", "₹400 प्रति दिन (या काम के हिसाब से)"), LocalizedString("Main kisi bhi kaam ke liye helper ke roop mein uplabdh hoon. Ek baar mauka dein bhaiya, mehnat se kaam karenge!", "मैं किसी भी काम के लिए हेल्पर के रूप में उपलब्ध हूँ। एक बार मौका दें भैया, मेहनत से काम करेंगे!"), "", vId, "labour"),
                        LabourProvider("l7_$vId", LocalizedString("Sheetal", "शीतल"), "8766475181", "2", LocalizedString("Ausani", "औसानी"), LocalizedString("₹400 per day (call par baat karein)", "₹400 प्रति दिन (कॉल पर बात करें)"), LocalizedString("Main kisi bhi kaam ke liye helper ke roop mein uplabdh hoon. Ek baar sewa ka mauka dein bhaiya!", "मैं किसी भी काम के लिए हेल्पर के रूप में उपलब्ध हूँ। एक बार सेवा का मौका दें भैया!"), "", vId, "labour"),
                        LabourProvider("l8_$vId", LocalizedString("Sudhir", "सुधीर"), "7234034368", "4", LocalizedString("Ausani", "औसानी"), LocalizedString("Call par rate confirm karein", "कॉल पर रेट कन्फर्म कर लें"), LocalizedString("Main gadi chalane (driving) ka kaam karta hoon. Safe driving aur sahi samay par pahunchayenge bhaiya!", "मैं गाड़ी चलाने (ड्राइविंग) का काम करता हूँ। सेफ ड्राइविंग और सही समय पर पहुंचाएंगे भैया!"), "", vId, "labour"),
                        LabourProvider("l9_$vId", LocalizedString("Ranjeet", "रंजीत"), "7081538149", "5", LocalizedString("Ausani", "औसानी"), LocalizedString("Call karke rate pooch sakte hain", "कॉल करके रेट पूछ सकते हैं"), LocalizedString("Main plumbing aur nal fitting ka kaam karta hoon. Ek baar mauka de ke dekhiye bhaiya, kaam 1 number hoga!", "मैं प्लंबर और नल फिटिंग का काम करता हूँ। एक बार मौका दे के देखिये भैया, काम 1 नंबर होगा!"), "", vId, "plumber"),
                        LabourProvider("l10_$vId", LocalizedString("Shivprakash", "शिवप्रकाश"), "9076519082", "3", LocalizedString("Ausani", "औसानी"), LocalizedString("Kaam ke hisab se sahi rate lagega", "काम के हिसाब से सही रेट लगेगा"), LocalizedString("Main gatta aur packing ka kaam karta hoon. Ek baar kaam de ke dekhein bhaiya, safai se packing karke denge!", "मैं गट्टा और पैकिंग का काम करता हूँ। एक बार काम दे के देखें भैया, सफाई से पैकिंग करके देंगे!"), "", vId, "labour"),
                        LabourProvider("l11_$vId", LocalizedString("Rahul", "राहुल"), "7704066562", "4", LocalizedString("Ausani", "औसानी"), LocalizedString("Baat karke rate fix hoga", "बातचीत करके रेट फिक्स होगा"), LocalizedString("Main electric aur wiring ka kaam karta hoon. Ek baar sewa ka mauka zaroor dein bhaiya, wiring ekdam safe hogi!", "मैं इलेक्ट्रिक और वायरिंग का काम करता हूँ। एक बार सेवा का मौका जरूर दें भैया, वायरिंग एकदम सेफ होगी!"), "", vId, "electrician"),
                        LabourProvider("l12_$vId", LocalizedString("Tuntun", "टुनटुन"), "9119635374", "8", LocalizedString("Ausani", "औसानी"), LocalizedString("₹700 per day (baat kar lein)", "₹700 प्रति दिन (बात कर लें)"), LocalizedString("Main lakdi ka har tarah ka kaam karta hoon. Furniture aur darwaze ka kaam 1 number karke denge bhaiya!", "मैं लकड़ी का हर तरह का काम करता हूँ। फर्नीचर और दरवाजे का काम 1 नंबर करके देंगे भैया!"), "", vId, "carpenter"),
                        LabourProvider("l13_$vId", LocalizedString("Manish", "मनीष"), "6390123110", "5", LocalizedString("Ausani", "औसानी"), LocalizedString("Direct call par rate fix karein", "डायरेक्ट कॉल करके रेट तय करें"), LocalizedString("Main loha aur welding ka kaam karta hoon. Ek baar sewa ka mauka dein, majboot kaam karke denge bhaiya!", "मैं लोहा और वेल्डिंग का काम करता हूँ। एक बार सेवा का मौका दें, मजबूत काम करके देंगे भैया!"), "", vId, "electrician"),
                        LabourProvider("l14_$vId", LocalizedString("Nitesh", "नितेश"), "8879966641", "4", LocalizedString("Ausani", "औसानी"), LocalizedString("Phone par baat karke tay hoga", "फोन पर बातचीत करके तय होगा"), LocalizedString("Main tiles aur marble lagane ka kaam karta hoon. Ek baar mauka dein bhaiya, kaam me koi shikayat nahi milegi!", "मैं टाइल और मार्बल लगाने का काम करता हूँ। एक बार मौका दें भैया, काम में कोई शिकायत नहीं मिलेगी!"), "", vId, "plumber"),
                        LabourProvider("l15_$vId", LocalizedString("Manish", "मनीष"), "8874276141", "6", LocalizedString("Ausani", "औसानी"), LocalizedString("Kaam dekh kar rate batayenge", "काम देखकर रेट बताएंगे"), LocalizedString("Main electric ka pura kaam karta hoon. Ek baar call karke dekhein bhaiya, kaam 100% safai se karke denge!", "मैं इलेक्ट्रिक का पूरा काम करता हूँ। एक बार कॉल करके देखें भैया, काम 100% सफाई से करके देंगे!"), "", vId, "electrician"),
                        LabourProvider("l16_$vId", LocalizedString("Akash", "आकाश"), "6386325079", "3", LocalizedString("Ausani", "औसानी"), LocalizedString("₹700 per day (call par baat kar lein)", "₹700 प्रति दिन (कॉल पर बात कर लें)"), LocalizedString("Main lakdi ka har tarah ka kaam karta hoon. Ek baar mauka de ke dekhiye bhaiya, aapka kaam 1 number hoga!", "मैं लकड़ी का हर तरह का काम करता हूँ। एक बार मौका दे के देखिये भैया, आपका काम 1 नंबर होगा!"), "", vId, "carpenter"),
                        LabourProvider("l17_$vId", LocalizedString("Sukhdev", "सुखदेव"), "7068228823", "7", LocalizedString("Ausani", "औसानी"), LocalizedString("Call karke rate fix kar lein", "कॉल करके रेट तय कर लें"), LocalizedString("Main plumbing aur nal fitting ka kaam karta hoon. Aapka kaam bilkul sahi aur majboot karke denge bhaiya!", "मैं प्लंबर मिस्त्री का काम करता हूँ। आपका काम बिल्कुल सही और मजबूत करके देंगे भैया!"), "", vId, "plumber"),
                        LabourProvider("l18_$vId", LocalizedString("Rajendra", "राजेन्द्र"), "9284738311", "10", LocalizedString("Ausani", "औसानी"), LocalizedString("₹700 per day (baat kar lein)", "₹700 प्रति दिन (बात कर लें)"), LocalizedString("Main lakdi ka har tarah ka kaam karta hoon. Ek baar sewa ka mauka zaroor dein bhaiya!", "मैं लकड़ी का हर तरह का काम करता हूँ। एक बार सेवा का मौका जरूर दें भैया!"), "", vId, "carpenter"),
                        LabourProvider("l19_$vId", LocalizedString("Arun", "अरुण"), "9044535493", "4", LocalizedString("Ausani", "औसानी"), LocalizedString("Call par rate confirm karein", "कॉल पर रेट कन्फर्म कर लें"), LocalizedString("Main gadi chalane (driving) ka kaam karta hoon. Safe driving aur aaramdayak safar milega bhaiya!", "मैं गाड़ी चलाने (ड्राइविंग) का काम करता हूँ। सेफ ड्राइविंग और आरामदायक सफर मिलेगा भैया!"), "", vId, "labour"),
                        LabourProvider("l20_$vId", LocalizedString("Dharmraj", "धर्मराज"), "9392390928", "8", LocalizedString("Ausani", "औसानी"), LocalizedString("₹700 per day (call par baat kar lein)", "₹700 प्रति दिन (कॉल पर बात कर लें)"), LocalizedString("Main lakdi ka har tarah ka kaam karta hoon. Ek baar mauka de ke dekhiye bhaiya, kaam top class hoga!", "मैं लकड़ी का हर तरह का काम करता हूँ। एक बार मौका दे के देखिये भैया, काम टॉप क्लास होगा!"), "", vId, "carpenter"),
                        LabourProvider("l21_$vId", LocalizedString("Nikil", "निखिल"), "9506172593", "3", LocalizedString("Ausani", "औसानी"), LocalizedString("Phone par baat karke tay hoga", "फोन पर बातचीत करके तय होगा"), LocalizedString("Main gadi chalane (driving) ka kaam karta hoon. Ek baar call karke dekhein bhaiya, sahi se gadi chalayenge!", "मैं गाड़ी चलाने (ड्राइविंग) का काम करता हूँ। एक बार कॉल करके देखें भैया, सही से गाड़ी चलाएंगे!"), "", vId, "labour"),
                        LabourProvider("l22_$vId", LocalizedString("Munna", "मुन्ना"), "7905576531", "6", LocalizedString("Ausani", "औसानी"), LocalizedString("Kaam aur samay ke hisab se tay hoga", "काम और समय के हिसाब से तय होगा"), LocalizedString("Main plumbing aur nal fitting ka kaam karta hoon. Ek baar sewa ka mauka dein bhaiya!", "मैं प्लंबर मिस्त्री का काम करता हूँ। एक बार सेवा का मौका दें भैया!"), "", vId, "plumber"),
                        LabourProvider("l23_$vId", LocalizedString("Nageshver", "नागेश्वर"), "7394484734", "9", LocalizedString("Ausani", "औसानी"), LocalizedString("₹700 per day (baat kar lein)", "₹700 प्रति दिन (बात कर लें)"), LocalizedString("Main lakdi ka har tarah ka kaam karta hoon. Ek baar mauka de ke dekhiye bhaiya, aapka kaam 1 number hoga!", "मैं लकड़ी का हर तरह का काम करता हूँ। एक बार मौका दे के देखिये भैया, आपका काम 1 नंबर होगा!"), "", vId, "carpenter"),
                        LabourProvider("l24_$vId", LocalizedString("Nanhe", "नन्हें"), "7800581082", "7", LocalizedString("Ausani", "औसानी"), LocalizedString("₹700 per day (call par baat kar lein)", "₹700 प्रति दिन (कॉल पर बात कर लें)"), LocalizedString("Main lakdi ka har tarah ka kaam karta hoon. Furniture ka kaam majboot karke denge bhaiya!", "मैं लकड़ी का हर तरह का काम करता हूँ। फर्नीचर का काम मजबूत करके देंगे भैया!"), "", vId, "carpenter"),
                        LabourProvider("l25_$vId", LocalizedString("Nilesh", "निलेश"), "9621923310", "3", LocalizedString("Ausani", "औसानी"), LocalizedString("₹400 per day (ya baat karke tay hoga)", "₹400 प्रति दिन (या बातचीत करके तय होगा)"), LocalizedString("Main kisi bhi kaam ke liye helper ke roop mein uplabdh hoon. Ek baar sewa ka mauka zaroor dein!", "मैं किसी भी काम के लिए हेल्पर के रूप में उपलब्ध हूँ। एक बार सेवा का मौका जरूर दें!"), "", vId, "labour"),
                        LabourProvider("l26_$vId", LocalizedString("Parbhu", "प्रभु"), "9054726131", "5", LocalizedString("Ausani", "औसानी"), LocalizedString("Call karke rate fix kar lein", "कॉल करके रेट तय कर लें"), LocalizedString("Main crane chalane ka kaam karta hoon. Bhaari kaam bhi aasaani se hoga bhaiya, ek baar call karein!", "मैं क्रेन चलाने का काम करता हूँ। भारी काम भी आसानी से होगा भैया, एक बार कॉल करें!"), "", vId, "labour"),
                        LabourProvider("l27_$vId", LocalizedString("Rampravesh", "रामप्रवेश"), "8808380542", "6", LocalizedString("Ausani", "औसानी"), LocalizedString("Kaam dekh kar rate batayenge", "काम देखकर रेट बताएंगे"), LocalizedString("Main lakdi pe polish ka kaam karta hoon. Furniture chamka denge bhaiya, ek baar mauka dein!", "मैं लकड़ी की पॉलिश का काम करता हूँ। फर्नीचर चमका देंगे भैया, एक बार मौका दें!"), "", vId, "carpenter"),
                        LabourProvider("l28_$vId", LocalizedString("Chandan", "चंदन"), "9721517177", "4", LocalizedString("Ausani", "औसानी"), LocalizedString("Call par rate confirm karein", "कॉल पर रेट कन्फर्म कर लें"), LocalizedString("Main gadi chalane (driving) ka kaam karta hoon. Safe driving aur sahi time par pahunchayenge bhaiya!", "मैं गाड़ी चलाने (ड्राइविंग) का काम करता हूँ। सेफ ड्राइविंग और सही टाइम पर पहुंचाएंगे भैया!"), "", vId, "labour"),
                        LabourProvider("l29_$vId", LocalizedString("Sajivan", "सजीवन"), "6307687593", "5", LocalizedString("Ausani", "औसानी"), LocalizedString("Call karke rate pooch sakte hain", "कॉल करके रेट पूछ सकते हैं"), LocalizedString("Main plumbing aur nal fitting ka kaam karta hoon. Ek baar mauka de ke dekhiye bhaiya, kaam 1 number hoga!", "मैं प्लंबर का पूरा काम करता हूँ। एक बार मौका दे के देखिये भैया, काम 1 नंबर होगा!"), "", vId, "plumber"),
                        LabourProvider("l30_$vId", LocalizedString("Abhishek", "अभिषेक"), "9696071188", "4", LocalizedString("Ausani", "औसानी"), LocalizedString("₹500 se ₹600 per day (baat kar lein)", "₹500 से ₹600 प्रति दिन (बात कर लें)"), LocalizedString("Main painting aur putty ka kaam karta hoon. Ek baar mauka de ke dekhiye bhaiya, ghar 1 number chamka denge!", "मैं पेंटिंग और पुट्टी का काम करता हूँ। एक बार मौका दे के देखिये भैया, घर 1 नंबर चमका देंगे!"), "", vId, "painter"),
                        LabourProvider("l31_$vId", LocalizedString("Monu", "मोनू"), "9670435840", "3", LocalizedString("Ausani", "औसानी"), LocalizedString("₹500 se ₹600 per day (call par baat kar lein)", "₹500 से ₹600 प्रति दिन (कॉल पर बात कर लें)"), LocalizedString("Main painting aur putty ka kaam karta hoon. Safai se painting karke denge bhaiya, ek baar mauka dein!", "मैं पेंटिंग और पुट्टी का काम करता हूँ। सफाई से पेंटिंग करके देंगे भैया, एक बार मौका दें!"), "", vId, "painter"),
                        LabourProvider("l32_$vId", LocalizedString("Chandrsekhar", "चंद्रशेखर"), "6301628401", "5", LocalizedString("Ausani", "औसानी"), LocalizedString("₹500 se ₹600 per day (baat karke tay hoga)", "₹500 से ₹600 प्रति दिन (बातचीत करके तय होगा)"), LocalizedString("Main painting aur putty ka kaam karta hoon. Ek baar sewa ka mauka dein bhaiya, kaam 100% sahi hoga!", "मैं पेंटिंग और पुट्टी का काम करता हूँ। एक बार सेवा का मौका दें भैया, काम 100% सही होगा!"), "", vId, "painter"),
                        LabourProvider("l33_$vId", LocalizedString("Rajkumar", "राजकुमार"), "9305673816", "6", LocalizedString("Ausani", "औसानी"), LocalizedString("Kaam ke hisab se sahi rate lagega", "काम के हिसाब से सही रेट लगेगा"), LocalizedString("Main electric aur wiring ka pura kaam karta hoon. Ek baar call karein bhaiya, wiring 100% safe hogi!", "मैं लाइट और वायरिंग का पूरा काम करता हूँ। एक बार कॉल करें भैया, वायरिंग 100% सेफ होगी!"), "", vId, "electrician"),
                        LabourProvider("l34_$vId", LocalizedString("Nitish", "नितीश"), "9278472358", "2", LocalizedString("Ausani", "औसानी"), LocalizedString("₹400 per day (ya call par baat kar lein)", "₹400 प्रति दिन (या कॉल पर बात कर लें)"), LocalizedString("Main kisi bhi kaam ke liye helper ke roop mein uplabdh hoon. Ek baar mauka de ke dekhiye bhaiya!", "मैं किसी भी काम के लिए हेल्पर के रूप में उपलब्ध हूँ। एक बार मौका दे के देखिये भैया!"), "", vId, "labour"),
                        LabourProvider("l35_$vId", LocalizedString("Arvind", "अरविंद"), "9721517177", "5", LocalizedString("Ausani", "औसानी"), LocalizedString("Phone par baat karke tay hoga", "फोन पर बातचीत करके तय होगा"), LocalizedString("Main electric aur wiring ka pura kaam karta hoon. Ek baar sewa ka mauka zaroor dein bhaiya!", "मैं लाइट और वायरिंग का पूरा काम करता हूँ। एक बार सेवा का मौका जरूर दें भैया!"), "", vId, "electrician"),
                        LabourProvider("l36_$vId", LocalizedString("Vikas", "विकास"), "8127932254", "3", LocalizedString("Ausani", "औसानी"), LocalizedString("₹400 per day (call karke tay kar lein)", "₹400 प्रति दिन (कॉल करके तय कर लें)"), LocalizedString("Main kisi bhi kaam ke liye helper ke roop mein uplabdh hoon. Mehnat se kaam karenge bhaiya!", "मैं किसी भी काम के लिए हेल्पर के रूप में उपलब्ध हूँ। मेहनत से काम करेंगे भैया!"), "", vId, "labour"),
                        LabourProvider("l37_$vId", LocalizedString("Amarraj", "अमरराज"), "8467834269", "2", LocalizedString("Ausani", "औसानी"), LocalizedString("₹400 per day (baat kar lein)", "₹400 प्रति दिन (बात कर लें)"), LocalizedString("Main kisi bhi kaam ke liye helper ke roop mein uplabdh hoon. Ek baar mauka dein bhaiya!", "मैं किसी भी काम के लिए हेल्पर के रूप में उपलब्ध हूँ। एक बार मौका दें भैया!"), "", vId, "labour"),
                        LabourProvider("l38_$vId", LocalizedString("Nikhil", "निखिल"), "9277081864", "2", LocalizedString("Ausani", "औसानी"), LocalizedString("₹400 per day (call par rate confirm karein)", "₹400 प्रति दिन (कॉल पर रेट कन्फर्म करें)"), LocalizedString("Main kisi bhi kaam ke liye helper ke roop mein uplabdh hoon. Ek baar sewa ka mauka zaroor dein!", "मैं किसी भी काम के लिए हेल्पर के रूप में उपलब्ध हूँ। एक बार सेवा का मौका जरूर दें!"), "", vId, "labour"),
                        LabourProvider("l39_$vId", LocalizedString("Parduman", "प्रद्युम्न"), "8795779604", "4", LocalizedString("Ausani", "औसानी"), LocalizedString("₹500 se ₹600 per day (baat karke fix hoga)", "₹500 से ₹600 प्रति दिन (बातचीत करके फिक्स होगा)"), LocalizedString("Main painting aur putty ka kaam karta hoon. Ek baar mauka de ke dekhiye bhaiya, ghar 1 number chamka denge!", "मैं पेंटिंग और पुट्टी का काम करता हूँ। एक बार मौका दे के देखिये भैया, घर 1 नंबर चमका देंगे!"), "", vId, "painter"),
                        LabourProvider("l40_$vId", LocalizedString("Pardeep", "प्रदीप"), "9838903687", "8", LocalizedString("Ausani", "औसानी"), LocalizedString("₹700 per day (call karke tay kar lein)", "₹700 प्रति दिन (कॉल करके तय कर लें)"), LocalizedString("Main lakdi ka har tarah ka kaam karta hoon. Ek baar mauka de ke dekhiye bhaiya, aapka kaam 1 number hoga!", "मैं लकड़ी का हर तरह का काम करता हूँ। एक बार मौका दे के देखिये भैया, आपका काम 1 नंबर होगा!"), "", vId, "carpenter"),
                        LabourProvider("l41_$vId", LocalizedString("Sonu", "सोनू"), "9336492711", "4", LocalizedString("Ausani", "औसानी"), LocalizedString("Direct call par rate fix karein", "डायरेक्ट कॉल करके रेट तय करें"), LocalizedString("Main gadi chalane (driving) ka kaam karta hoon. Safe driving aur sahi time par pahunchayenge bhaiya!", "मैं गाड़ी चलाने (ड्राइविंग) का काम करता हूँ। सेफ ड्राइविंग और सही टाइम पर पहुंचाएंगे भैया!"), "", vId, "labour"),
                        LabourProvider("l42_$vId", LocalizedString("Ramsajivan", "रामसजीवन"), "9742079450", "6", LocalizedString("Ausani", "औसानी"), LocalizedString("₹500 se ₹600 per day (baat kar lein)", "₹500 से ₹600 प्रति दिन (बात कर लें)"), LocalizedString("Main painting aur putty ka pura kaam karta hoon. Ek baar sewa ka mauka dein bhaiya!", "मैं पेंटिंग और पुट्टी का पूरा काम करता हूँ। एक बार सेवा का मौका दें भैया!"), "", vId, "painter"),
                        LabourProvider("l43_$vId", LocalizedString("Shubham", "शुभम"), "9335972819", "2", LocalizedString("Ausani", "औसानी"), LocalizedString("₹400 per day (call karke tay kar lein)", "₹400 प्रति दिन (कॉल करके तय कर लें)"), LocalizedString("Main kisi bhi kaam ke liye helper ke roop mein uplabdh hoon. Ek baar mauka de ke dekhiye bhaiya!", "मैं किसी भी काम के लिए हेल्पर के रूप में उपलब्ध हूँ। एक बार मौका दे के देखिये भैया!"), "", vId, "labour"),
                        LabourProvider("l44_$vId", LocalizedString("Sandip", "संदीप"), "8564805902", "4", LocalizedString("Ausani", "औसानी"), LocalizedString("₹500 se ₹600 per day (call par baat karein)", "₹500 से ₹600 प्रति दिन (कॉल पर बात करें)"), LocalizedString("Main painting aur putty ka kaam karta hoon. Ek baar mauka de ke dekhiye bhaiya!", "मैं पेंटिंग और पुट्टी का काम करता हूँ। एक बार मौका दे के देखिये भैया!"), "", vId, "painter"),
                        LabourProvider("l45_$vId", LocalizedString("Anil", "अनिल"), "7800826610", "5", LocalizedString("Ausani", "औसानी"), LocalizedString("Kaam dekh kar rate batayenge", "काम देखकर रेट बताएंगे"), LocalizedString("Main lakdi pe polish ka kaam karta hoon. Furniture naya jaisa chamka denge bhaiya!", "मैं लकड़ी की पॉलिश का काम करता हूँ। फर्नीचर नया जैसा चमका देंगे भैया!"), "", vId, "carpenter"),
                        LabourProvider("l46_$vId", LocalizedString("Akash", "आकाश"), "6392555972", "4", LocalizedString("Ausani", "औसानी"), LocalizedString("₹700 per day (baat kar lein)", "₹700 प्रति दिन (बात कर लें)"), LocalizedString("Main lakdi ka har tarah ka kaam karta hoon. Ek baar mauka de ke dekhiye bhaiya, kaam 1 number hoga!", "मैं लकड़ी का हर तरह का काम करता हूँ। एक बार मौका दे के देखिये भैया, काम 1 नंबर होगा!"), "", vId, "carpenter"),
                        LabourProvider("l47_$vId", LocalizedString("Abhishek", "अभिषेक"), "8467912315", "3", LocalizedString("Ausani", "औसानी"), LocalizedString("₹500 se ₹600 per day (call karke tay kar lein)", "₹500 से ₹600 प्रति दिन (कॉल करके तय कर लें)"), LocalizedString("Main painting aur putty ka kaam karta hoon. Ek baar mauka de ke dekhiye bhaiya!", "मैं पेंटिंग और पुट्टी का काम करता हूँ। एक बार मौका दे के देखिये भैया!"), "", vId, "painter"),
                        LabourProvider("l48_$vId", LocalizedString("Akhilesh", "अखिलेश"), "9565109719", "3", LocalizedString("Ausani", "औसानी"), LocalizedString("₹400 per day (call par rate confirm karein)", "₹400 प्रति दिन (कॉल पर रेट कन्फर्म करें)"), LocalizedString("Main kisi bhi kaam ke liye helper ke roop mein uplabdh hoon. Ek baar sewa ka mauka zaroor dein!", "मैं किसी भी काम के लिए हेल्पर के रूप में उपलब्ध हूँ। एक बार सेवा का मौका जरूर दें!"), "", vId, "labour"),
                        LabourProvider("l49_$vId", LocalizedString("Ravindra", "रवीन्द्र"), "9764821748", "7", LocalizedString("Ausani", "औसानी"), LocalizedString("₹700 per day (call par baat kar lein)", "₹700 प्रति दिन (कॉल पर बात कर लें)"), LocalizedString("Main lakdi ka har tarah ka kaam karta hoon. Ek baar mauka de ke dekhiye bhaiya, kaam 1 number hoga!", "मैं लकड़ी का हर तरह का काम करता हूँ। एक बार मौका दे के देखिये भैया, काम 1 नंबर होगा!"), "", vId, "carpenter"),
                        LabourProvider("l50_$vId", LocalizedString("Dharmveer", "धर्मवीर"), "6307240517", "6", LocalizedString("Ausani", "औसानी"), LocalizedString("Call karke rate fix kar lein", "कॉल करके रेट तय कर लें"), LocalizedString("Main plumbing aur nal fitting ka kaam karta hoon. Ek baar mauka dein bhaiya, kaam me koi shikayat nahi milegi!", "मैं प्लंबर का पूरा काम करता हूँ। एक बार मौका दें भैया, काम में कोई शिकायत नहीं मिलेगी!"), "", vId, "plumber"),
                        LabourProvider("l51_$vId", LocalizedString("Durgvijay", "दुर्गविजय"), "9620651005", "4", LocalizedString("Ausani", "औसानी"), LocalizedString("₹400 per day (ya baat karke tay hoga)", "₹400 प्रति दिन (या बातचीत करके तय होगा)"), LocalizedString("Main kisi bhi kaam ke liye helper ke roop mein uplabdh hoon. Ek baar mauka de ke dekhiye bhaiya!", "मैं किसी भी काम के लिए हेल्पर के रूप में उपलब्ध हूँ। एक बार मौका दे के देखिये भैया!"), "", vId, "labour"),
                        LabourProvider("l52_$vId", LocalizedString("Ajay", "अजय"), "8197994181", "5", LocalizedString("Ausani", "औसानी"), LocalizedString("₹700 per day (baat kar lein)", "₹700 प्रति दिन (बात कर लें)"), LocalizedString("Main lakdi ka har tarah ka kaam karta hoon. Ek baar sewa ka mauka zaroor dein bhaiya!", "मैं लकड़ी का हर तरह का काम करता हूँ। एक बार सेवा का मौका जरूर दें भैया!"), "", vId, "carpenter"),
                        LabourProvider("l53_$vId", LocalizedString("Angad", "अंगद"), "6393080459", "4", LocalizedString("Ausani", "औसानी"), LocalizedString("Kaam dekh kar rate batayenge", "काम देखकर रेट बताएंगे"), LocalizedString("Main lakdi pe polish ka kaam karta hoon. Ek baar mauka dein bhaiya, furniture 1 number chamka denge!", "मैं लकड़ी की पॉलिश का काम करता हूँ। एक बार मौका दें भैया, फर्नीचर 1 नंबर चमका देंगे!"), "", vId, "carpenter"),
                        LabourProvider("l54_$vId", LocalizedString("Sharvan", "श्रवण"), "9554778125", "3", LocalizedString("Ausani", "औसानी"), LocalizedString("Phone par baat karke tay hoga", "फोन पर बातचीत करके तय होगा"), LocalizedString("Main gadi chalane (driving) ka kaam karta hoon. Safe driving aur sahi time par pahunchayenge bhaiya!", "मैं गाड़ी चलाने (ड्राइविंग) का काम करता हूँ। सेफ ड्राइविंग और सही टाइम पर पहुंचाएंगे भैया!"), "", vId, "labour")
                    )
                    realLabour.forEach { labourRepo.saveProvider(vId, it) }
                    
                    // 2. Construction Hub (Real data for Ausani)
                    constructionRepo.saveHub(vId, ConstructionHub("c1_$vId", LocalizedString("Sunil Shuttering", "सुनील शटरिंग"), LocalizedString("Sunil Shuttering", "सुनील शटरिंग"), "6353954840", LocalizedString("Ausani", "औसानी"), listOf(
                        ConstructionProduct(LocalizedString("Main chhat dhalai aur shuttering ka kaam karta hoon. Ek baar mauka de ke dekhiye bhaiya, majboot dhalai hogi!", "मैं छत ढलाई और शटरिंग का काम करता हूँ। एक बार मौका दे के देखिये भैया, मजबूत ढलाई होगी!"), "Area ke hisab se", LocalizedString("Per Sq.Ft (call par baat kar lein)", "क्षेत्रफल के हिसाब से (कॉल पर बात कर लें)"))
                    ), "", vId, "shuttering"))

                    // 3. Transport Hub (Real data for Ausani)
                    transportRepo.saveHub(vId, TransportHub("t1_$vId", LocalizedString("Sunil Bolero", "सुनील बोलेरो"), LocalizedString("Meri Bolero booking aur satta bhade ke liye uplabdh hai. Ek baar mauka dein bhaiya, aaramdayak safar hoga!", "मेरी बोलेरो बुकिंग और सट्टा भाड़े के लिए उपलब्ध है। एक बार मौका दें भैया, आरामदायक सफर होगा!"), "6353954840", LocalizedString("Ausani", "औसानी"), "", vId, "car"))
                    transportRepo.saveHub(vId, TransportHub("t2_$vId", LocalizedString("Munil Pickup", "मुनील पिकअप"), LocalizedString("Meri Pickup loader gadi bhade ke liye uplabdh hai. Mal surakshit pahunchayenge bhaiya!", "मेरी पिकअप माल लोडर गाड़ी भाड़े के लिए उपलब्ध है। माल सुरक्षित पहुंचाएंगे भैया!"), "9265005615", LocalizedString("Ausani", "औसानी"), "", vId, "pickup"))
                    transportRepo.saveHub(vId, TransportHub("t3_$vId", LocalizedString("Satish Tractor", "सतीश ट्रैक्टर"), LocalizedString("Mera Tractor aur trolley kisi bhi kaam/bhade ke liye uplabdh hai. Kheti aur bhade ke liye ek baar call karein bhaiya!", "मेरा ट्रैक्टर और ट्रॉली किसी भी काम/भाड़े के लिए उपलब्ध है। खेती और भाड़े के लिए एक बार कॉल करें भैया!"), "8897161640", LocalizedString("Ausani", "औसानी"), "", vId, "tractor"))
                    transportRepo.saveHub(vId, TransportHub("t4_$vId", LocalizedString("Sanjay Tractor", "संजय ट्रैक्टर"), LocalizedString("Mera Tractor aur trolley kisi bhi kaam/bhade ke liye uplabdh hai. Ek baar sewa ka mauka dein bhaiya!", "मेरा ट्रैक्टर और ट्रॉली किसी भी काम/भाड़े के लिए उपलब्ध है। एक बार सेवा का मौका दें भैया!"), "6394903029", LocalizedString("Ausani", "औसानी"), "", vId, "tractor"))

                    // 4. Health Hub (Real data for Ausani)
                    healthRepo.saveHub(vId, HealthHub("h1_$vId", LocalizedString("Ram Lalit Clinic", "राम ललित क्लिनिक"), LocalizedString("Ausani", "औसानी"), "9919599328", LocalizedString("General Physician", "सामान्य चिकित्सक"), LocalizedString("Dava ke hisab se (call karke pooch lein)", "दवा के हिसाब से (कॉल करके पूछ लें)"), LocalizedString(), LocalizedString(), LocalizedString("Main dava, sui aur samanya ilaj karta hoon. Ek baar clinic aayein bhaiya, badiya dekhbhal hogi!", "मैं दवा, सुई और सामान्य बीमारियों का इलाज करता हूँ। एक बार क्लिनिक आएं भैया, बढ़िया देखभाल होगी!"), "", vId, "doctors"))

                    // 5. Family Functions (Real data for Ausani)
                    familyRepo.saveHub(vId, FamilyFunctionHub("f1_$vId", LocalizedString("Dipchand Catering", "दीपचंद कैटरिंग"), LocalizedString("Ausani", "औसानी"), "8564832127", LocalizedString("Main bhandari aur catering ka pura kaam karta hoon. Ek baar mauka de ke dekhiye bhaiya, khana 1 number swadisht banega!", "मैं भंडारी और कैटरिंग का पूरा काम करता हूँ। एक बार मौका दे के देखिये भैया, खाना 1 नंबर स्वादिष्ट बनेगा!"), LocalizedString("Phone par baat karke tay hoga", "फोन पर बातचीत करके तय होगा"), "", vId, "catering"))

                    // 6. News & Notifications for Ausani
                    newsRepo.saveNews(vId, News("n1_$vId", LocalizedString("Ausani me Apna Gav App Launch!", "औसानी गांव में 'अपना गांव' ऐप का हुआ शुभ शुभारंभ! 🎉"), LocalizedString("Aapke gaon Ausani me sabhi majdoor, mistri, plumber, electrician, catering, doctor aur gaari ki suvidha ab mobile par uplabdh hai. Abhi check karein aur seedhe call karke baat karein!", "आपके गांव औसानी में सभी मजदूर, कारपेंटर, प्लंबर, इलेक्ट्रिशियन, कैटरिंग, डॉक्टर और गाड़ियों की सुविधा अब मोबाइल पर उपलब्ध है। अभी ऐप खोलें और सीधे कॉल करके बातचीत करें!"), "https://images.unsplash.com/photo-1500382017468-9049fed747ef?w=800&auto=format&fit=crop", System.currentTimeMillis(), vId, "news"))

                    newsRepo.saveNews(vId, News("n2_$vId", LocalizedString("Ausani ke sabhi Karigar aur Vyapari Judein", "औसानी गांव के सभी कारीगर और व्यवसायी जुड़ें! 🚀"), LocalizedString("Kya aap mistri, driver, dukandar ya service provider hain? Apne gav me apni service list karein aur grahak payein. Bilkul muft registration!", "क्या आप मिस्त्री, ड्राइवर, दुकानदार या सर्विस प्रदाता हैं? अपने गांव में अपनी सेवाएं दर्ज करें और सीधे ग्राहक पाएं। बिल्कुल मुफ्त रजिस्ट्रेशन!"), "https://images.unsplash.com/photo-1596785236245-48fa064dd785?w=800&auto=format&fit=crop", System.currentTimeMillis() - 3600000, vId, "notice"))

                    newsRepo.saveNotification(vId, AppNotification("notif1_$vId", LocalizedString("Badhai Ho Ausani Grasiyon!", "बधाई हो औसानी ग्रामवासियों! 🎉"), LocalizedString("Aapke gaon me Apna Gav app launch ho gaya hai. Ab sabhi mistri, gadi aur suvidhaayein ek click me payein!", "आपके गांव में 'अपना गांव' ऐप लाइव हो गया है। अब सभी मिस्त्री, कारपेंटर, प्लंबर, गाड़ियां और सभी सुविधाएं एक क्लिक में पाएं!"), System.currentTimeMillis(), vId))

                    newsRepo.saveNotification(vId, AppNotification("notif2_$vId", LocalizedString("Ausani Digital Gaon Abhiyan", "औसानी डिजिटल गांव अभियान 🚀"), LocalizedString("Apne gaon ke sabhi dukan, mistri aur services ki jankari dekhein aur call karein.", "अपने गांव के सभी कारीगरों, मिस्त्रियों, ट्रांसपोर्ट और सेवाओं की जानकारी देखें और सीधे संपर्क करें।"), System.currentTimeMillis() - 7200000, vId))

                    // 7. Banners for Ausani
                    newsRepo.saveBanner(vId, Banner("b1_$vId", "https://picsum.photos/seed/ausani_banner1/800/400", LocalizedString("Apna Gav App Live in Ausani", "अपना गांव ऐप - औसानी में हुआ लाइव! 📱"), "Muft Service", "", vId))

                    newsRepo.saveBanner(vId, Banner("b2_$vId", "https://picsum.photos/seed/ausani_banner2/800/400", LocalizedString("Sabhi Mistri & Suvidhaayein Ab Ek Jagah", "सभी मिस्त्री, गाड़ियां और सुविधाएं अब एक जगह"), "Gaon Ka Vikas", "", vId))
                }
            }
        }
    }
}
