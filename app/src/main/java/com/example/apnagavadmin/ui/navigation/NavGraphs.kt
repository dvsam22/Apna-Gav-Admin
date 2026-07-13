package com.example.apnagavadmin.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.EntryProviderScope
import com.example.apnagavadmin.data.model.Village
import com.example.apnagavadmin.navigation.NavRoute
import com.example.apnagavadmin.ui.details.VillageDetailsScreen
import com.example.apnagavadmin.ui.hubs.*
import com.example.apnagavadmin.ui.village.*

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun EntryProviderScope<NavRoute>.mainGraph(
    backStack: SnapshotStateList<NavRoute>,
    villageViewModel: VillageViewModel
) {
    addEntryProvider(
        key = NavRoute.VillageList,
        metadata = ListDetailSceneStrategy.listPane(
            detailPlaceholder = { Text("Select a village to see details") }
        )
    ) {
        VillageListScreen(
            viewModel = villageViewModel,
            onNavigateToDetails = { backStack.add(NavRoute.VillageDetails(it)) },
            onNavigateToAddVillage = { backStack.add(NavRoute.UpsertVillage()) },
            onNavigateToEditVillage = { backStack.add(NavRoute.UpsertVillage(it)) }
        )
    }
    addEntryProvider(
        clazz = NavRoute.UpsertVillage::class,
        metadata = ListDetailSceneStrategy.detailPane()
    ) { route ->
        VillageEditScreen(
            village = route.model,
            onBack = { backStack.removeLastOrNull() },
            onSave = { 
                if (route.model == null) villageViewModel.addVillage(it) else villageViewModel.updateVillage(it)
                backStack.removeLastOrNull()
            }
        )
    }
    addEntryProvider(
        clazz = NavRoute.VillageDetails::class,
        metadata = ListDetailSceneStrategy.detailPane()
    ) { route ->
        VillageDetailsScreen(
            villageId = route.villageId,
            onBack = { backStack.removeLastOrNull() },
            onNavigateToHub = { hub ->
                val nextRoute = when(hub) {
                    "labour" -> NavRoute.LabourScreen(route.villageId)
                    "construction" -> NavRoute.ConstructionScreen(route.villageId)
                    "transport" -> NavRoute.TransportScreen(route.villageId)
                    "mandi" -> NavRoute.MandiScreen(route.villageId)
                    "health" -> NavRoute.HealthScreen(route.villageId)
                    "family" -> NavRoute.FamilyFunctionScreen(route.villageId)
                    "news" -> NavRoute.NewsScreen(route.villageId)
                    "banners" -> NavRoute.BannerScreen(route.villageId)
                    "notifications" -> NavRoute.NotificationScreen(route.villageId)
                    else -> null
                }
                nextRoute?.let { backStack.add(it) }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun EntryProviderScope<NavRoute>.hubGraph(
    backStack: SnapshotStateList<NavRoute>,
    villages: List<Village>,
    onVillageChange: (String) -> Unit
) {
    // Labour
    addEntryProvider(clazz = NavRoute.LabourScreen::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        LabourScreen(
            viewModel = viewModel(key = "Labour_${route.villageId}") { LabourViewModel(villageId = route.villageId) },
            onBack = { backStack.removeLastOrNull() },
            villages = villages,
            selectedVillageId = route.villageId,
            onVillageChange = onVillageChange,
            onNavigateToEdit = { categoryId, provider -> 
                backStack.add(NavRoute.UpsertLabour(route.villageId, categoryId, provider))
            }
        )
    }
    addEntryProvider(clazz = NavRoute.UpsertLabour::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        val vm: LabourViewModel = viewModel(key = "Labour_${route.villageId}") { LabourViewModel(villageId = route.villageId) }
        LabourEditScreen(
            provider = route.provider,
            onBack = { backStack.removeLastOrNull() },
            onSave = { 
                vm.save(it)
                backStack.removeLastOrNull()
            }
        )
    }

    // Construction
    addEntryProvider(clazz = NavRoute.ConstructionScreen::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        ConstructionScreen(
            viewModel = viewModel(key = "Construction_${route.villageId}") { ConstructionViewModel(villageId = route.villageId) },
            onBack = { backStack.removeLastOrNull() },
            villages = villages,
            selectedVillageId = route.villageId,
            onVillageChange = onVillageChange,
            onNavigateToEdit = { categoryId, hub ->
                backStack.add(NavRoute.UpsertConstruction(route.villageId, categoryId, hub))
            }
        )
    }
    addEntryProvider(clazz = NavRoute.UpsertConstruction::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        val vm: ConstructionViewModel = viewModel(key = "Construction_${route.villageId}") { ConstructionViewModel(villageId = route.villageId) }
        ConstructionEditScreen(
            hub = route.model,
            onBack = { backStack.removeLastOrNull() },
            onSave = {
                vm.save(it)
                backStack.removeLastOrNull()
            }
        )
    }

    // Transport
    addEntryProvider(clazz = NavRoute.TransportScreen::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        TransportScreen(
            viewModel = viewModel(key = "Transport_${route.villageId}") { TransportViewModel(villageId = route.villageId) },
            onBack = { backStack.removeLastOrNull() },
            villages = villages,
            selectedVillageId = route.villageId,
            onVillageChange = onVillageChange,
            onNavigateToEdit = { categoryId, hub ->
                backStack.add(NavRoute.UpsertTransport(route.villageId, categoryId, hub))
            }
        )
    }
    addEntryProvider(clazz = NavRoute.UpsertTransport::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        val vm: TransportViewModel = viewModel(key = "Transport_${route.villageId}") { TransportViewModel(villageId = route.villageId) }
        TransportEditScreen(
            hub = route.model,
            onBack = { backStack.removeLastOrNull() },
            onSave = {
                vm.save(it)
                backStack.removeLastOrNull()
            }
        )
    }

    // Mandi
    addEntryProvider(clazz = NavRoute.MandiScreen::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        MandiScreen(
            viewModel = viewModel(key = "Mandi_${route.villageId}") { MandiViewModel(villageId = route.villageId) },
            onBack = { backStack.removeLastOrNull() },
            villages = villages,
            selectedVillageId = route.villageId,
            onVillageChange = onVillageChange,
            onNavigateToEdit = { categoryId, item ->
                backStack.add(NavRoute.UpsertMandi(route.villageId, categoryId, item))
            }
        )
    }
    addEntryProvider(clazz = NavRoute.UpsertMandi::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        val vm: MandiViewModel = viewModel(key = "Mandi_${route.villageId}") { MandiViewModel(villageId = route.villageId) }
        MandiEditScreen(
            priceItem = route.model,
            selectedCategory = route.categoryId,
            onBack = { backStack.removeLastOrNull() },
            onSave = {
                vm.save(it)
                backStack.removeLastOrNull()
            }
        )
    }

    // Health
    addEntryProvider(clazz = NavRoute.HealthScreen::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        HealthScreen(
            viewModel = viewModel(key = "Health_${route.villageId}") { HealthViewModel(villageId = route.villageId) },
            onBack = { backStack.removeLastOrNull() },
            villages = villages,
            selectedVillageId = route.villageId,
            onVillageChange = onVillageChange,
            onNavigateToEdit = { categoryId, hub ->
                backStack.add(NavRoute.UpsertHealth(route.villageId, categoryId, hub))
            }
        )
    }
    addEntryProvider(clazz = NavRoute.UpsertHealth::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        val vm: HealthViewModel = viewModel(key = "Health_${route.villageId}") { HealthViewModel(villageId = route.villageId) }
        HealthEditScreen(
            hub = route.model,
            selectedCategory = route.categoryId,
            onBack = { backStack.removeLastOrNull() },
            onSave = {
                vm.save(it)
                backStack.removeLastOrNull()
            }
        )
    }

    // Family Functions
    addEntryProvider(clazz = NavRoute.FamilyFunctionScreen::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        FamilyFunctionScreen(
            viewModel = viewModel(key = "Family_${route.villageId}") { FamilyFunctionViewModel(villageId = route.villageId) },
            onBack = { backStack.removeLastOrNull() },
            villages = villages,
            selectedVillageId = route.villageId,
            onVillageChange = onVillageChange,
            onNavigateToEdit = { categoryId, hub ->
                backStack.add(NavRoute.UpsertFamilyFunction(route.villageId, categoryId, hub))
            }
        )
    }
    addEntryProvider(clazz = NavRoute.UpsertFamilyFunction::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        val vm: FamilyFunctionViewModel = viewModel(key = "Family_${route.villageId}") { FamilyFunctionViewModel(villageId = route.villageId) }
        FamilyFunctionEditScreen(
            hub = route.model,
            onBack = { backStack.removeLastOrNull() },
            onSave = {
                vm.save(it)
                backStack.removeLastOrNull()
            }
        )
    }

    // News, Banners, Notifications
    addEntryProvider(clazz = NavRoute.NewsScreen::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        NewsScreen(
            viewModel = viewModel(key = "News_${route.villageId}") { NewsViewModel(villageId = route.villageId) },
            onBack = { backStack.removeLastOrNull() },
            villages = villages,
            selectedVillageId = route.villageId,
            onVillageChange = onVillageChange,
            onNavigateToEdit = { news ->
                backStack.add(NavRoute.UpsertNews(route.villageId, news))
            }
        )
    }
    addEntryProvider(clazz = NavRoute.UpsertNews::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        val vm: NewsViewModel = viewModel(key = "News_${route.villageId}") { NewsViewModel(villageId = route.villageId) }
        NewsEditScreen(
            newsItem = route.model,
            onBack = { backStack.removeLastOrNull() },
            onSave = {
                vm.save(it)
                backStack.removeLastOrNull()
            }
        )
    }
    addEntryProvider(clazz = NavRoute.BannerScreen::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        BannerScreen(
            viewModel = viewModel(key = "Banner_${route.villageId}") { BannerViewModel(villageId = route.villageId) },
            onBack = { backStack.removeLastOrNull() },
            villages = villages,
            selectedVillageId = route.villageId,
            onVillageChange = onVillageChange,
            onNavigateToEdit = { banner ->
                backStack.add(NavRoute.UpsertBanner(route.villageId, banner))
            }
        )
    }
    addEntryProvider(clazz = NavRoute.UpsertBanner::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        val vm: BannerViewModel = viewModel(key = "Banner_${route.villageId}") { BannerViewModel(villageId = route.villageId) }
        BannerEditScreen(
            banner = route.model,
            onBack = { backStack.removeLastOrNull() },
            onSave = {
                vm.save(it)
                backStack.removeLastOrNull()
            }
        )
    }
    addEntryProvider(clazz = NavRoute.NotificationScreen::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        NotificationScreen(
            viewModel = viewModel(key = "Notification_${route.villageId}") { NotificationViewModel(villageId = route.villageId) },
            onBack = { backStack.removeLastOrNull() },
            villages = villages,
            selectedVillageId = route.villageId,
            onVillageChange = onVillageChange,
            onNavigateToEdit = { notification ->
                backStack.add(NavRoute.UpsertNotification(route.villageId, notification))
            }
        )
    }
    addEntryProvider(clazz = NavRoute.UpsertNotification::class, metadata = ListDetailSceneStrategy.detailPane()) { route ->
        val vm: NotificationViewModel = viewModel(key = "Notification_${route.villageId}") { NotificationViewModel(villageId = route.villageId) }
        NotificationEditScreen(
            notification = route.model,
            onBack = { backStack.removeLastOrNull() },
            onSave = {
                vm.save(it)
                backStack.removeLastOrNull()
            }
        )
    }
}
