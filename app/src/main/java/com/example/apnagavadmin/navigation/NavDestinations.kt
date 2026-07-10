package com.example.apnagavadmin.navigation

import com.example.apnagavadmin.data.model.*
import kotlinx.serialization.Serializable

@Serializable
sealed interface NavRoute {
    @Serializable
    data object VillageList : NavRoute

    @Serializable
    data class VillageDetails(val villageId: String) : NavRoute

    @Serializable
    data class LabourScreen(val villageId: String) : NavRoute

    @Serializable
    data class ConstructionScreen(val villageId: String) : NavRoute

    @Serializable
    data class TransportScreen(val villageId: String) : NavRoute

    @Serializable
    data class MandiScreen(val villageId: String) : NavRoute

    @Serializable
    data class HealthScreen(val villageId: String) : NavRoute

    @Serializable
    data class NewsScreen(val villageId: String) : NavRoute

    @Serializable
    data class BannerScreen(val villageId: String) : NavRoute

    @Serializable
    data class NotificationScreen(val villageId: String) : NavRoute

    @Serializable
    data class UpsertLabour(val villageId: String, val categoryId: String, val provider: LabourProvider? = null) : NavRoute

    @Serializable
    data class UpsertConstruction(val villageId: String, val categoryId: String, val model: ConstructionHub? = null) : NavRoute

    @Serializable
    data class UpsertTransport(val villageId: String, val categoryId: String, val model: TransportHub? = null) : NavRoute

    @Serializable
    data class UpsertMandi(val villageId: String, val categoryId: String, val model: MandiPrice? = null) : NavRoute

    @Serializable
    data class UpsertHealth(val villageId: String, val categoryId: String, val model: HealthHub? = null) : NavRoute

    @Serializable
    data class UpsertNews(val villageId: String, val model: News? = null) : NavRoute

    @Serializable
    data class UpsertBanner(val villageId: String, val model: Banner? = null) : NavRoute

    @Serializable
    data class UpsertNotification(val villageId: String, val model: AppNotification? = null) : NavRoute

    @Serializable
    data class UpsertVillage(val model: Village? = null) : NavRoute
}
