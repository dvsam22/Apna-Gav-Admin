package com.example.apnagavadmin.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TransportHub(
    val id: String = "",
    val name: LocalizedString = LocalizedString(),
    val vehicleType: LocalizedString = LocalizedString(),
    val contact: String = "",
    val location: LocalizedString = LocalizedString(),
    val image: String = "",
    override val villageId: String = "",
    val categoryId: String = "" // "tractor", "car", "pickup"
) : HubItem

@Serializable
data class MandiPrice(
    val id: String = "",
    val cropName: LocalizedString = LocalizedString(),
    val price: Double = 0.0,
    val unit: LocalizedString = LocalizedString(),
    val buyerName: LocalizedString = LocalizedString(), // Added for Local Buyers
    val contact: String = "",   // Added for Local Buyers
    val address: LocalizedString = LocalizedString(),   // Added for Local Buyers
    val date: Long = System.currentTimeMillis(),
    val trend: String = "stable", // up, down, stable
    override val villageId: String = "",
    val categoryId: String = "prices" // "prices", "market", "buyers"
) : HubItem

@Serializable
data class HealthHub(
    val id: String = "",
    val name: LocalizedString = LocalizedString(),
    val address: LocalizedString = LocalizedString(),
    val contact: String = "",
    val specialisation: LocalizedString = LocalizedString(), // For Doctors
    val availability: LocalizedString = LocalizedString(),   // For Doctors/Hospitals/Pharmacy (e.g., "09:30AM - 04:00PM", "24 Hours")
    val type: LocalizedString = LocalizedString(),           // For Hospitals (e.g., "Multi Speciality Hospital")
    val facilities: LocalizedString = LocalizedString(),     // For Hospitals (e.g., "OPD, Emergency...")
    val services: LocalizedString = LocalizedString(),       // For Pharmacy (e.g., "All Medicines Available")
    val image: String = "",
    override val villageId: String = "",
    val categoryId: String = "doctors" // "doctors", "hospitals", "pharmacy", "ambulance", "police"
) : HubItem

@Serializable
data class News(
    val id: String = "",
    val title: LocalizedString = LocalizedString(),
    val description: LocalizedString = LocalizedString(),
    val image: String = "",
    val date: Long = System.currentTimeMillis(),
    override val villageId: String = "",
    val category: String = "news" // "news" (Breaking News), "notice" (Notices)
) : HubItem

@Serializable
data class Banner(
    val id: String = "",
    val imageUrl: String = "",
    val title: LocalizedString = LocalizedString(),       // e.g., "Today's vegetables in market offer price"
    val discountText: String = "", // e.g., "10"
    val link: String = "",
    override val villageId: String = ""
) : HubItem

@Serializable
data class AppNotification(
    val id: String = "",
    val title: LocalizedString = LocalizedString(),
    val message: LocalizedString = LocalizedString(),
    val date: Long = System.currentTimeMillis(),
    override val villageId: String = ""
) : HubItem
