package com.example.apnagavadmin.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ConstructionProduct(
    val name: String = "",
    val price: String = "",
    val unit: String = ""
)

@Serializable
data class ConstructionHub(
    val id: String = "",
    val name: String = "",
    val shopName: String = "",
    val contact: String = "",
    val address: String = "",
    val products: List<ConstructionProduct> = emptyList(),
    val image: String = "",
    val villageId: String = "",
    val categoryId: String = "" // "bricks" or "material_shops"
)
