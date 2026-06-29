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
    val date: Long = System.currentTimeMillis(),
    val trend: String = "stable", // up, down, stable
    val villageId: String = ""
)

@Serializable
data class HealthHub(
    val id: String = "",
    val doctorName: String = "",
    val specialty: String = "",
    val hospitalName: String = "",
    val contact: String = "",
    val address: String = "",
    val image: String = "",
    val villageId: String = ""
)

@Serializable
data class News(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val date: Long = System.currentTimeMillis(),
    val villageId: String = ""
)

@Serializable
data class Banner(
    val id: String = "",
    val imageUrl: String = "",
    val link: String = "",
    val villageId: String = ""
)
