package com.example.apnagavadmin.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TransportHub(
    val id: String = "",
    val name: String = "",
    val vehicleType: String = "",
    val contact: String = "",
    val location: String = "",
    val image: String = "",
    val villageId: String = "",
    val categoryId: String = "" // "tractor", "car", "pickup"
)

@Serializable
data class MandiPrice(
    val id: String = "",
    val cropName: String = "",
    val price: Double = 0.0,
    val unit: String = "Quintal",
    val buyerName: String = "", // Added for Local Buyers
    val contact: String = "",   // Added for Local Buyers
    val address: String = "",   // Added for Local Buyers
    val date: Long = System.currentTimeMillis(),
    val trend: String = "stable", // up, down, stable
    val villageId: String = "",
    val categoryId: String = "prices" // "prices", "market", "buyers"
)

@Serializable
data class HealthHub(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val contact: String = "",
    val specialisation: String = "", // For Doctors
    val availability: String = "",   // For Doctors/Hospitals/Pharmacy (e.g., "09:30AM - 04:00PM", "24 Hours")
    val type: String = "",           // For Hospitals (e.g., "Multi Speciality Hospital")
    val facilities: String = "",     // For Hospitals (e.g., "OPD, Emergency...")
    val services: String = "",       // For Pharmacy (e.g., "All Medicines Available")
    val image: String = "",
    val villageId: String = "",
    val categoryId: String = "doctors" // "doctors", "hospitals", "pharmacy", "ambulance", "police"
)

@Serializable
data class News(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val date: Long = System.currentTimeMillis(),
    val villageId: String = "",
    val category: String = "news" // "news" (Breaking News), "notice" (Notices)
)

@Serializable
data class Banner(
    val id: String = "",
    val imageUrl: String = "",
    val title: String = "",       // e.g., "Today's vegetables in market offer price"
    val discountText: String = "", // e.g., "10"
    val link: String = "",
    val villageId: String = ""
)

@Serializable
data class AppNotification(
    val id: String = "",
    val title: String = "",
    val message: String = "",
    val date: Long = System.currentTimeMillis(),
    val villageId: String = ""
)
