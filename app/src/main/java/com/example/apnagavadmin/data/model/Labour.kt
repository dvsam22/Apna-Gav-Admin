package com.example.apnagavadmin.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LabourCategory(
    val id: String = "",
    val name: String = "",
    val icon: String = ""
)

@Serializable
data class LabourProvider(
    val id: String = "",
    val name: String = "",
    val contact: String = "",
    val experience: String = "",
    val location: String = "",
    val charges: String = "",
    val skills: String = "", // Changed to String for simplicity in Add/Edit as per mockup
    val image: String = "",
    val villageId: String = "",
    val categoryId: String = ""
)
