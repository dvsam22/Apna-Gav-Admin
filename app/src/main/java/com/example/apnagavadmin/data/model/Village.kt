package com.example.apnagavadmin.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Village(
    val id: String = "",
    val villageName: String = "",
    val sarpanchName: String = "",
    val district: String = "",
    val state: String = "",
    val pincode: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val image: String = "",
    val isActive: Boolean = true
)
