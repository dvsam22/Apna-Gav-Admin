package com.example.apnagavadmin.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LabourCategory(
    val id: String = "",
    val name: LocalizedString = LocalizedString(),
    val icon: String = ""
)

@Serializable
data class LabourProvider(
    val id: String = "",
    val name: LocalizedString = LocalizedString(),
    val contact: String = "",
    val experience: String = "",
    val location: LocalizedString = LocalizedString(),
    val charges: LocalizedString = LocalizedString(),
    val skills: LocalizedString = LocalizedString(), // Changed to String for simplicity in Add/Edit as per mockup
    val image: String = "",
    override val villageId: String = "",
    val categoryId: String = ""
) : HubItem
