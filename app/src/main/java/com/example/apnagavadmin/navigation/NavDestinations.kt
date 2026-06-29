package com.example.apnagavadmin.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavRoute {
    @Serializable
    data object VillageList : NavRoute

    @Serializable
    data class VillageDetails(val villageId: String) : NavRoute

    @Serializable
    data class LabourHub(val villageId: String) : NavRoute

    @Serializable
    data class ConstructionHub(val villageId: String) : NavRoute

    @Serializable
    data class TransportHub(val villageId: String) : NavRoute

    @Serializable
    data class MandiHub(val villageId: String) : NavRoute

    @Serializable
    data class HealthHub(val villageId: String) : NavRoute

    @Serializable
    data class NewsHub(val villageId: String) : NavRoute

    @Serializable
    data class BannerHub(val villageId: String) : NavRoute
}
