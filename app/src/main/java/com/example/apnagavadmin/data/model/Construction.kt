package com.example.apnagavadmin.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ConstructionProduct(
    val name: LocalizedString = LocalizedString(),
    val price: String = "",
    val unit: LocalizedString = LocalizedString()
)

@Serializable
data class ConstructionHub(
    val id: String = "",
    val name: LocalizedString = LocalizedString(),
    val shopName: LocalizedString = LocalizedString(),
    val contact: String = "",
    val address: LocalizedString = LocalizedString(),
    val products: List<ConstructionProduct> = emptyList(),
    val image: String = "",
    val villageId: String = "",
    val categoryId: String = "" // "bricks" or "material_shops"
)
