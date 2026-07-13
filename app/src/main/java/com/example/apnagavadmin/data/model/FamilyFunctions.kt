package com.example.apnagavadmin.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FamilyFunctionHub(
    val id: String = "",
    val name: LocalizedString = LocalizedString(),
    val address: LocalizedString = LocalizedString(),
    val contact: String = "",
    val services: LocalizedString = LocalizedString(), // e.g., "Waterproof Pandal, Light & Seating"
    val startingPrice: LocalizedString = LocalizedString(), // e.g., "₹15,000"
    val image: String = "",
    override val villageId: String = "",
    val categoryId: String = "" // "tent", "catering", "photo", "dj", "marriage_halls"
) : HubItem
