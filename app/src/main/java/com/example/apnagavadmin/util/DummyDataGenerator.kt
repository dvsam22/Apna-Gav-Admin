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

    fun generateAllData() {
        CoroutineScope(Dispatchers.IO).launch {
            // --- Village: Maharajganj ---
            val v1Id = "village_maharajganj"
            val maharajganj = Village(
                id = v1Id,
                villageName = "Maharajganj",
                sarpanchName = "Ram Sewak",
                district = "Raebareli",
                state = "Uttar Pradesh",
                pincode = "229101",
                lat = 26.38,
                lng = 81.25
            )
            villageRepo.updateVillage(maharajganj)

            // ---------------- Labourers (20 entries) ----------------
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l1_v1", name = "Anil Kumar", location = "Main Chauraha", skills = "Plumbing", charges = "₹500/day", categoryId = "plumber", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l2_v1", name = "Suresh Mistri", location = "Near School", skills = "Rajmistri", charges = "₹800/day", categoryId = "rajmistri", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l3_v1", name = "Mahesh Yadav", location = "Panchayat Bhawan", skills = "Wiring, Repair", charges = "₹400/day", categoryId = "electrician", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l4_v1", name = "Rakesh Kumar", location = "Bus Stand Road", skills = "Plumbing, Motor Fitting", charges = "₹550/day", categoryId = "plumber", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l5_v1", name = "Bablu Rajmistri", location = "Ram Mandir Road", skills = "Rajmistri, Plastering", charges = "₹850/day", categoryId = "rajmistri", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l6_v1", name = "Sanjay Verma", location = "Bazar Chowk", skills = "House Wiring, Fan Repair", charges = "₹450/day", categoryId = "electrician", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l7_v1", name = "Ramesh Tailor", location = "Near Post Office", skills = "Stitching, Alteration", charges = "₹250/Suit", categoryId = "tailor", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l8_v1", name = "Geeta Devi", location = "Mahila Mandal Road", skills = "Blouse & Saree Stitching", charges = "₹200/Piece", categoryId = "tailor", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l9_v1", name = "Om Prakash", location = "Lakadi Bazar", skills = "Furniture Making, Carpentry", charges = "₹600/day", categoryId = "carpenter", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l10_v1", name = "Rajendra Badhai", location = "Station Road", skills = "Door & Window Fitting", charges = "₹650/day", categoryId = "carpenter", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l11_v1", name = "Vijay Painter", location = "Tiraha Chauraha", skills = "Wall Painting, Distemper", charges = "₹500/day", categoryId = "painter", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l12_v1", name = "Ashok Kumar", location = "Nai Basti", skills = "Texture Painting, POP", charges = "₹700/day", categoryId = "painter", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l13_v1", name = "Naresh Welder", location = "Loha Mandi", skills = "Gate & Grill Welding", charges = "₹600/day", categoryId = "welder", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l14_v1", name = "Deepak Kumar", location = "Sabzi Mandi Road", skills = "Steel Fabrication", charges = "₹650/day", categoryId = "welder", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l15_v1", name = "Santosh Kumar", location = "Kanha Gaushala Road", skills = "Foundation, Brickwork", charges = "₹800/day", categoryId = "rajmistri", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l16_v1", name = "Munna Lal", location = "Idgah Road", skills = "AC & Fridge Repair", charges = "₹450/visit", categoryId = "electrician", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l17_v1", name = "Pappu Barber", location = "Hair Cutting Salon, Bazar", skills = "Hair Cutting, Shaving", charges = "₹50/service", categoryId = "barber", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l18_v1", name = "Chhotu Cook", location = "Barat Ghar Road", skills = "Catering, Cooking for Events", charges = "₹300/day", categoryId = "cook", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l19_v1", name = "Ramautar Driver", location = "Bus Adda", skills = "Tractor & Car Driving", charges = "₹400/day", categoryId = "driver", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l20_v1", name = "Kallu Mistri", location = "Nala Road", skills = "Tiles Fitting, Marble Work", charges = "₹750/day", categoryId = "rajmistri", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l21_v1", name = "Ramu Loader", location = "Sabzi Mandi Road", skills = "Material Loading/Unloading", charges = "₹350/day", categoryId = "labour", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l22_v1", name = "Shivpal Mazdoor", location = "Bus Adda Road", skills = "General Construction Labour", charges = "₹350/day", categoryId = "labour", villageId = v1Id))
            labourRepo.saveProvider(v1Id, LabourProvider(id = "l23_v1", name = "Fool Chand", location = "Nala Road", skills = "Digging, Earthwork", charges = "₹380/day", categoryId = "labour", villageId = v1Id))

            // ---------------- Construction (20 entries) ----------------
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c1_v1", shopName = "Durga Bricks", address = "NH-24, Maharajganj", categoryId = "bricks", products = listOf(ConstructionProduct("Lal Eint", "7", "Piece"), ConstructionProduct("Peeli Eint", "6", "Piece"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c2_v1", shopName = "Singh Hardware", address = "Market Road", categoryId = "hardware_shops", products = listOf(ConstructionProduct("Cement (ACC)", "400", "Bag"), ConstructionProduct("Sariya (TATA)", "72", "Kg"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c3_v1", shopName = "Bharat Bricks Bhandar", address = "NH-24 Bypass", categoryId = "bricks", products = listOf(ConstructionProduct("Lal Eint No.1", "7.5", "Piece"), ConstructionProduct("Fly Ash Eint", "6.5", "Piece"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c4_v1", shopName = "Maa Vindhyavasini Cement Store", address = "Bazar Chauraha", categoryId = "cement", products = listOf(ConstructionProduct("Ultratech Cement", "410", "Bag"), ConstructionProduct("Ambuja Cement", "395", "Bag"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c5_v1", shopName = "Shiv Shakti Cement Agency", address = "Station Road", categoryId = "cement", products = listOf(ConstructionProduct("JK Lakshmi Cement", "390", "Bag"), ConstructionProduct("ACC Gold", "415", "Bag"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c6_v1", shopName = "Ganga Sand Suppliers", address = "River Ghat Road", categoryId = "sand_grit", products = listOf(ConstructionProduct("Nadi Ki Balu", "45", "CFT"), ConstructionProduct("Grit (Gitti)", "50", "CFT"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c7_v1", shopName = "Krishna Sand & Morang", address = "NH-24, Maharajganj", categoryId = "sand_grit", products = listOf(ConstructionProduct("Chhanni Balu", "48", "CFT"), ConstructionProduct("Morang", "42", "CFT"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c8_v1", shopName = "Singh Hardware", address = "Market Road", categoryId = "hardware_shops", products = listOf(ConstructionProduct("Sariya (TATA)", "72", "Kg"), ConstructionProduct("Tar (Bitumen)", "60", "Kg"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c9_v1", shopName = "Jai Mata Di Hardware", address = "Bazar Road", categoryId = "hardware_shops", products = listOf(ConstructionProduct("Nails & Screws", "90", "Kg"), ConstructionProduct("PVC Pipe 4 inch", "180", "Piece"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c10_v1", shopName = "Aggarwal Steel Traders", address = "Loha Mandi", categoryId = "steel", products = listOf(ConstructionProduct("TMT Bar 8mm", "68", "Kg"), ConstructionProduct("TMT Bar 12mm", "70", "Kg"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c11_v1", shopName = "Bhagwati Steel Corner", address = "NH-24", categoryId = "steel", products = listOf(ConstructionProduct("Angle Iron", "65", "Kg"), ConstructionProduct("Steel Pipe", "80", "Kg"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c12_v1", shopName = "Rana Tiles Gallery", address = "Market Road", categoryId = "tiles", products = listOf(ConstructionProduct("Floor Tiles 2x2", "45", "Sq.Ft"), ConstructionProduct("Wall Tiles", "35", "Sq.Ft"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c13_v1", shopName = "Kajaria Tiles Showroom", address = "Bypass Road", categoryId = "tiles", products = listOf(ConstructionProduct("Vitrified Tiles", "60", "Sq.Ft"), ConstructionProduct("Bathroom Tiles", "40", "Sq.Ft"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c14_v1", shopName = "Asian Paints Depot", address = "Chauraha Bazar", categoryId = "paints", products = listOf(ConstructionProduct("Asian Emulsion", "280", "Litre"), ConstructionProduct("Primer", "220", "Litre"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c15_v1", shopName = "Berger Paints Store", address = "Station Road", categoryId = "paints", products = listOf(ConstructionProduct("Berger Weathercoat", "310", "Litre"), ConstructionProduct("Wood Enamel", "250", "Litre"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c16_v1", shopName = "Sharma Sanitary Store", address = "Market Road", categoryId = "sanitary", products = listOf(ConstructionProduct("Wash Basin", "1200", "Piece"), ConstructionProduct("Commode", "3500", "Piece"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c17_v1", shopName = "Jindal Sanitaryware", address = "Bazar Chauraha", categoryId = "sanitary", products = listOf(ConstructionProduct("Bathroom Fittings Set", "2500", "Set"), ConstructionProduct("PVC Tank 1000L", "4500", "Piece"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c18_v1", shopName = "Modern Glass & Aluminium", address = "NH-24", categoryId = "glass_aluminium", products = listOf(ConstructionProduct("Aluminium Window Frame", "350", "Sq.Ft"), ConstructionProduct("Glass Sheet 5mm", "90", "Sq.Ft"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c19_v1", shopName = "Bharat Timber Depot", address = "Lakadi Bazar", categoryId = "timber", products = listOf(ConstructionProduct("Sal Wood", "1800", "Cft"), ConstructionProduct("Plywood 19mm", "95", "Sq.Ft"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c20_v1", shopName = "Laxmi Timber Traders", address = "Station Road", categoryId = "timber", products = listOf(ConstructionProduct("Teak Wood", "2500", "Cft"), ConstructionProduct("Flush Door", "2200", "Piece"))))

            // ---------------- Material Shop (Balu, Cement, Gitti, Chad) ----------------
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c21_v1", shopName = "Maa Durga Material Store", address = "NH-24, Maharajganj", categoryId = "material_shops", products = listOf(ConstructionProduct("Balu (Sand)", "45", "CFT"), ConstructionProduct("Cement (Ultratech)", "410", "Bag"), ConstructionProduct("Gitti (Grit)", "50", "CFT"), ConstructionProduct("Chad (Tin Sheet)", "550", "Piece"))))
            constructionRepo.saveHub(v1Id, ConstructionHub(id = "c22_v1", shopName = "Ram Naresh Material Suppliers", address = "Bazar Chauraha", categoryId = "material_shops", products = listOf(ConstructionProduct("Balu (Sand)", "42", "CFT"), ConstructionProduct("Cement (ACC)", "400", "Bag"), ConstructionProduct("Gitti (Grit)", "48", "CFT"), ConstructionProduct("Chad (Asbestos Sheet)", "480", "Piece"))))

            // ---------------- Transport (20 entries) ----------------
            transportRepo.saveHub(v1Id, TransportHub(id = "t1_v1", name = "Ram Singh", vehicleType = "Tractor with Trolley", contact = "9876543210", categoryId = "tractor", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t2_v1", name = "Shyamu Loader", vehicleType = "Tata Ace", contact = "9988776655", categoryId = "loader", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t3_v1", name = "Balram Yadav", vehicleType = "Swaraj Tractor with Trolley", contact = "9871122334", categoryId = "tractor", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t4_v1", name = "Chhote Lal", vehicleType = "Mahindra Tractor", contact = "9812233445", categoryId = "tractor", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t5_v1", name = "Guddu Kumar", vehicleType = "Tata 407 Mini Truck", contact = "9876123456", categoryId = "pickup", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t6_v1", name = "Rinku Yadav", vehicleType = "Eicher Mini Truck", contact = "9765432109", categoryId = "pickup", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t7_v1", name = "Manoj Kumar", vehicleType = "Mahindra Pickup Loader", contact = "9654321098", categoryId = "loader", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t8_v1", name = "Rajesh Kashyap", vehicleType = "Ashok Leyland Truck", contact = "9543210987", categoryId = "pickup", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t9_v1", name = "Sanjeev Yadav", vehicleType = "Tata 1109 Truck", contact = "9432109876", categoryId = "pickup", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t10_v1", name = "Vinay Kumar", vehicleType = "Bajaj Auto Rickshaw", contact = "9321098765", categoryId = "pickup", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t11_v1", name = "Sonu Yadav", vehicleType = "Piaggio Tempo", contact = "9210987654", categoryId = "pickup", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t12_v1", name = "Rakesh Chaudhary", vehicleType = "Mahindra Jeeto Tempo", contact = "9109876543", categoryId = "pickup", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t13_v1", name = "Anand Kumar", vehicleType = "JCB 3DX", contact = "9098765432", categoryId = "jcb", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t14_v1", name = "Devendra Singh", vehicleType = "JCB Backhoe Loader", contact = "8987654321", categoryId = "jcb", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t15_v1", name = "Pramod Kumar", vehicleType = "Escorts Tractor with Trolley", contact = "8876543210", categoryId = "tractor", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t16_v1", name = "Ravindra Yadav", vehicleType = "Bolero Pickup", contact = "8765432109", categoryId = "loader", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t17_v1", name = "Satyendra Singh", vehicleType = "Maruti Omni Auto", contact = "8654321098", categoryId = "pickup", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t18_v1", name = "Dharmendra Kumar", vehicleType = "Tata 909 Truck", contact = "8543210987", categoryId = "pickup", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t19_v1", name = "Umesh Chandra", vehicleType = "Force Tempo Traveller", contact = "8432109876", categoryId = "pickup", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t20_v1", name = "Jitendra Prasad", vehicleType = "New Holland Tractor with Trolley", contact = "8321098765", categoryId = "tractor", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t21_v1", name = "Rajkumar Singh", vehicleType = "Maruti Swift Dzire (Car)", contact = "8210987654", categoryId = "car", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t22_v1", name = "Mahesh Chaudhary", vehicleType = "Mahindra Bolero Pickup", contact = "8109876543", categoryId = "pickup", villageId = v1Id))
            transportRepo.saveHub(v1Id, TransportHub(id = "t23_v1", name = "Sunil Kumar", vehicleType = "Tata Ace Pickup Van", contact = "7998765432", categoryId = "pickup", villageId = v1Id))

            // ---------------- Mandi (20 entries) ----------------
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m1_v1", cropName = "Wheat (Gehun)", price = 2450.0, unit = "1 Quintal", categoryId = "prices", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m2_v1", cropName = "Paddy (Rice)", price = 2320.0, unit = "1 Quintal", categoryId = "prices", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m3_v1", cropName = "Tomato", price = 35.0, unit = "1 Kg", categoryId = "market", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m4_v1", cropName = "Potato", price = 28.0, unit = "1 Kg", categoryId = "market", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m5_v1", buyerName = "Premchand", cropName = "Fruits", address = "Rampur Road", categoryId = "buyers", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m6_v1", cropName = "Mustard (Sarson)", price = 6350.0, unit = "1 Quintal", categoryId = "prices", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m7_v1", cropName = "Gram (Chana)", price = 5100.0, unit = "1 Quintal", categoryId = "prices", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m8_v1", cropName = "Sugarcane (Ganna)", price = 350.0, unit = "1 Quintal", categoryId = "prices", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m9_v1", cropName = "Maize (Makka)", price = 2100.0, unit = "1 Quintal", categoryId = "prices", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m10_v1", cropName = "Arhar (Tur Dal)", price = 9800.0, unit = "1 Quintal", categoryId = "prices", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m11_v1", cropName = "Onion", price = 22.0, unit = "1 Kg", categoryId = "market", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m12_v1", cropName = "Lady Finger (Bhindi)", price = 40.0, unit = "1 Kg", categoryId = "market", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m13_v1", cropName = "Cauliflower", price = 25.0, unit = "1 Kg", categoryId = "market", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m14_v1", cropName = "Green Chilli", price = 50.0, unit = "1 Kg", categoryId = "market", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m15_v1", cropName = "Brinjal (Baingan)", price = 30.0, unit = "1 Kg", categoryId = "market", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m16_v1", cropName = "Banana", price = 45.0, unit = "1 Dozen", categoryId = "market", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m17_v1", buyerName = "Rajkumar Traders", cropName = "Wheat & Rice", address = "Gudri Bazar", categoryId = "buyers", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m18_v1", buyerName = "Suresh Anaj Bhandar", cropName = "Mustard & Gram", address = "Station Road", categoryId = "buyers", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m19_v1", buyerName = "Naveen Vegetable Traders", cropName = "Vegetables (Bulk)", address = "Sabzi Mandi", categoryId = "buyers", villageId = v1Id))
            mandiRepo.savePrice(v1Id, MandiPrice(id = "m20_v1", buyerName = "Om Prakash Fruit Mandi", cropName = "Fruits (Bulk)", address = "NH-24 Road", categoryId = "buyers", villageId = v1Id))

            // ---------------- Health (20 entries) ----------------
            healthRepo.saveHub(v1Id, HealthHub(id = "h1_v1", name = "Dr. Anil Sharma", specialisation = "General Physician", availability = "09:30AM - 04:00PM", address = "Maharajganj Clinic", categoryId = "doctors", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h2_v1", name = "Community Health Center", type = "Government Hospital", facilities = "OPD, General Ward", availability = "24 Hours", address = "Near Bus Stand", categoryId = "hospitals", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h3_v1", name = "Dr. Sunita Verma", specialisation = "Gynaecologist", availability = "10:00AM - 02:00PM", address = "Bazar Road Clinic", categoryId = "doctors", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h4_v1", name = "Dr. Rajesh Gupta", specialisation = "Child Specialist", availability = "11:00AM - 05:00PM", address = "Station Road", categoryId = "doctors", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h5_v1", name = "Dr. Manoj Tiwari", specialisation = "Orthopaedic", availability = "09:00AM - 01:00PM", address = "Near Panchayat Bhawan", categoryId = "doctors", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h6_v1", name = "Dr. Kavita Singh", specialisation = "Dentist", availability = "10:00AM - 06:00PM", address = "Market Chowk", categoryId = "doctors", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h7_v1", name = "Dr. Ashok Pandey", specialisation = "Ayurvedic Physician", availability = "09:00AM - 03:00PM", address = "Nai Basti Road", categoryId = "doctors", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h8_v1", name = "Primary Health Centre", type = "Government PHC", facilities = "OPD, Vaccination, Ante-natal Care", availability = "08:00AM - 02:00PM", address = "PHC Road", categoryId = "hospitals", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h9_v1", name = "Shanti Nursing Home", type = "Private Hospital", facilities = "OPD, IPD, Minor Surgery", availability = "24 Hours", address = "NH-24, Maharajganj", categoryId = "hospitals", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h10_v1", name = "Jeevan Jyoti Hospital", type = "Private Hospital", facilities = "Maternity, General Ward, Pathology", availability = "24 Hours", address = "Bypass Road", categoryId = "hospitals", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h11_v1", name = "Sharma Medical Store", type = "Pharmacy", facilities = "Medicines, First Aid", availability = "08:00AM - 10:00PM", address = "Bazar Chauraha", categoryId = "pharmacy", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h12_v1", name = "Apollo Pharmacy Franchise", type = "Pharmacy", facilities = "Medicines, Health Products", availability = "24 Hours", address = "Station Road", categoryId = "pharmacy", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h13_v1", name = "Jan Aushadhi Kendra", type = "Government Pharmacy", facilities = "Generic Medicines", availability = "09:00AM - 06:00PM", address = "Near CHC", categoryId = "pharmacy", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h14_v1", name = "Dr. Vikram Yadav", specialisation = "Skin Specialist", availability = "12:00PM - 06:00PM", address = "Market Road", categoryId = "doctors", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h15_v1", name = "Dr. Neelam Mishra", specialisation = "Homeopathy", availability = "10:00AM - 04:00PM", address = "Idgah Road", categoryId = "doctors", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h16_v1", name = "Dr. Sanjay Dubey", specialisation = "ENT Specialist", availability = "11:00AM - 03:00PM", address = "Bus Stand Road", categoryId = "doctors", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h17_v1", name = "Sathi Diagnostic Centre", type = "Pathology Lab", facilities = "Blood Test, X-Ray, Ultrasound", availability = "07:00AM - 09:00PM", address = "Near Bus Stand", categoryId = "hospitals", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h18_v1", name = "Aayush Ayurvedic Kendra", type = "Ayurvedic Centre", facilities = "Panchakarma, Herbal Medicine", availability = "09:00AM - 05:00PM", address = "Ram Mandir Road", categoryId = "hospitals", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h19_v1", name = "Dr. Poonam Rai", specialisation = "Eye Specialist", availability = "10:00AM - 02:00PM", address = "Tiraha Chauraha", categoryId = "doctors", villageId = v1Id))
            healthRepo.saveHub(v1Id, HealthHub(id = "h20_v1", name = "108 Ambulance Service", type = "Emergency Service", facilities = "Free Ambulance, Emergency Transport", availability = "24 Hours", address = "CHC Maharajganj", categoryId = "hospitals", villageId = v1Id))

            // ---------------- News, Banners & Notifications (20 entries) ----------------
            newsRepo.saveNews(v1Id, News(id = "n1_v1", title = "Rain alert in Raebareli", description = "Light to moderate rainfall is expected tomorrow across the block. Farmers are advised to take care of harvested crops.", category = "news", image = "https://example.com/rain.jpg", villageId = v1Id))
            newsRepo.saveBanner(v1Id, Banner(id = "b1_v1", title = "Special Discount on Seeds", discountText = "15", imageUrl = "https://example.com/seeds.jpg", villageId = v1Id))
            newsRepo.saveNews(v1Id, News(id = "n2_v1", title = "Gram Panchayat Meeting on Sunday", description = "A gram sabha meeting will be held to discuss road repair and drainage works in the village.", category = "news", image = "https://picsum.photos/seed/panchayat/600/400", villageId = v1Id))
            newsRepo.saveNews(v1Id, News(id = "n3_v1", title = "Free Health Checkup Camp", description = "A free health checkup camp will be organized at the Community Health Center this Friday for all villagers.", category = "news", image = "https://picsum.photos/seed/healthcamp/600/400", villageId = v1Id))
            newsRepo.saveNews(v1Id, News(id = "n4_v1", title = "PM Kisan Samman Nidhi Installment Released", description = "The next installment of PM Kisan Samman Nidhi has been released. Farmers can check their bank accounts.", category = "news", image = "https://picsum.photos/seed/pmkisan/600/400", villageId = v1Id))
            newsRepo.saveNews(v1Id, News(id = "n5_v1", title = "New Ration Card Distribution", description = "New ration cards will be distributed at the Panchayat Bhawan starting next Monday.", category = "news", image = "https://picsum.photos/seed/rationcard/600/400", villageId = v1Id))
            newsRepo.saveNews(v1Id, News(id = "n6_v1", title = "Road Repair Work Begins", description = "Repair work on the Maharajganj to Raebareli main road has started and may cause minor traffic delays.", category = "news", image = "https://picsum.photos/seed/roadrepair/600/400", villageId = v1Id))
            newsRepo.saveNews(v1Id, News(id = "n7_v1", title = "Electricity Maintenance Notice", description = "Power supply will remain off for maintenance work between 10 AM and 2 PM tomorrow.", category = "news", image = "https://picsum.photos/seed/electricity/600/400", villageId = v1Id))
            newsRepo.saveNews(v1Id, News(id = "n8_v1", title = "School Admission Drive", description = "The government primary school is conducting an admission drive for the new academic session.", category = "news", image = "https://picsum.photos/seed/schooladmission/600/400", villageId = v1Id))
            newsRepo.saveNews(v1Id, News(id = "n9_v1", title = "Pulse Polio Vaccination Camp", description = "A pulse polio vaccination camp will be held at the Anganwadi Kendra for children below 5 years.", category = "news", image = "https://picsum.photos/seed/poliocamp/600/400", villageId = v1Id))
            newsRepo.saveNews(v1Id, News(id = "n10_v1", title = "Krishi Mela in Raebareli", description = "A district level Krishi Mela showcasing new farming equipment and seeds will be held next week.", category = "news", image = "https://picsum.photos/seed/krishimela/600/400", villageId = v1Id))
            newsRepo.saveBanner(v1Id, Banner(id = "b2_v1", title = "Fertilizer Sale - Limited Stock", discountText = "10", imageUrl = "https://example.com/fertilizer.jpg", villageId = v1Id))
            newsRepo.saveBanner(v1Id, Banner(id = "b3_v1", title = "Diwali Offer on Hardware Items", discountText = "20", imageUrl = "https://example.com/hardware.jpg", villageId = v1Id))
            newsRepo.saveBanner(v1Id, Banner(id = "b4_v1", title = "Monsoon Sale on Tractor Parts", discountText = "12", imageUrl = "https://example.com/tractorparts.jpg", villageId = v1Id))
            newsRepo.saveBanner(v1Id, Banner(id = "b5_v1", title = "New Year Offer on Cement", discountText = "8", imageUrl = "https://example.com/cement.jpg", villageId = v1Id))
            newsRepo.saveNotification(v1Id, AppNotification(id = "not1_v1", title = "Mandi Closed", message = "Mandi will remain closed on Tuesday due to local festival.", villageId = v1Id))
            newsRepo.saveNotification(v1Id, AppNotification(id = "not2_v1", title = "Panchayat Office Closed", message = "The Panchayat office will remain closed tomorrow on account of a public holiday.", villageId = v1Id))
            newsRepo.saveNotification(v1Id, AppNotification(id = "not3_v1", title = "Water Supply Disruption", message = "Water supply will be disrupted for a few hours tomorrow due to pipeline repair work.", villageId = v1Id))
            newsRepo.saveNotification(v1Id, AppNotification(id = "not4_v1", title = "Vaccination Reminder", message = "Reminder: Second dose of vaccination is due for children registered last month.", villageId = v1Id))
            newsRepo.saveNotification(v1Id, AppNotification(id = "not5_v1", title = "Gram Sabha Notice", message = "All villagers are requested to attend the Gram Sabha meeting at the Panchayat Bhawan on Sunday.", villageId = v1Id))
        }
    }
}