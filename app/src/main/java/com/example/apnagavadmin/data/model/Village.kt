package com.example.apnagavadmin.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Village(
    val id: String = "",
    val villageName: LocalizedString = LocalizedString(),
    val sarpanchName: LocalizedString = LocalizedString(),
    val sarpanchPhone: String = "",
    val district: LocalizedString = LocalizedString(),
    val state: LocalizedString = LocalizedString(),
    val pincode: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val image: String = "",
    val isActive: Boolean = true
)
