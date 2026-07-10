package com.example.apnagavadmin.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.rememberViewModelStoreProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.ViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.SaveableStateHolderNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.navigationevent.NavigationEventDispatcher
import androidx.navigationevent.NavigationEventDispatcherOwner
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import com.example.apnagavadmin.data.model.*
import com.example.apnagavadmin.navigation.NavRoute
import com.example.apnagavadmin.ui.details.VillageDetailsScreen
import com.example.apnagavadmin.ui.hubs.*
import com.example.apnagavadmin.ui.village.VillageListScreen
import com.example.apnagavadmin.ui.village.VillageViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ApnaGavNavHost(
    backStack: SnapshotStateList<NavRoute>
) {
    val saveableStateHolder = rememberSaveableStateHolder()
    val viewModelStoreProvider = rememberViewModelStoreProvider()

    // Global Village State
    val villageViewModel: VillageViewModel = viewModel()
    val villageState by villageViewModel.state.collectAsState()
    val villages = villageState.villages

    val onVillageChange: (String) -> Unit = { newVillageId ->
        val last = backStack.lastOrNull()
        if (last != null) {
            val updatedRoute = when (last) {
                is NavRoute.LabourScreen -> last.copy(villageId = newVillageId)
                is NavRoute.ConstructionScreen -> last.copy(villageId = newVillageId)
                is NavRoute.TransportScreen -> last.copy(villageId = newVillageId)
                is NavRoute.MandiScreen -> last.copy(villageId = newVillageId)
                is NavRoute.HealthScreen -> last.copy(villageId = newVillageId)
                is NavRoute.NewsScreen -> last.copy(villageId = newVillageId)
                is NavRoute.BannerScreen -> last.copy(villageId = newVillageId)
                is NavRoute.NotificationScreen -> last.copy(villageId = newVillageId)
                is NavRoute.VillageDetails -> last.copy(villageId = newVillageId)
                else -> last
            }
            if (updatedRoute != last) {
                backStack[backStack.size - 1] = updatedRoute
            }
        }
    }

    val decorators = remember(saveableStateHolder, viewModelStoreProvider) {
        listOf(
            SaveableStateHolderNavEntryDecorator<NavRoute>(saveableStateHolder),
            ViewModelStoreNavEntryDecorator<NavRoute>(viewModelStoreProvider)
        )
    }

    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val directive = remember(windowAdaptiveInfo) {
        calculatePaneScaffoldDirective(windowAdaptiveInfo)
            .copy(horizontalPartitionSpacerSize = 0.dp)
    }
    val listDetailStrategy = rememberListDetailSceneStrategy<NavRoute>(directive = directive)
    
    val dispatcher = remember { NavigationEventDispatcher() }
    val owner = remember(dispatcher) {
        object : NavigationEventDispatcherOwner {
            override val navigationEventDispatcher = dispatcher
        }
    }

    CompositionLocalProvider(LocalNavigationEventDispatcherOwner provides owner) {
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = decorators,
            sceneStrategy = listDetailStrategy,
            entryProvider = entryProvider {
                entry<NavRoute.VillageList>(
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
                entry<NavRoute.UpsertVillage>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                    VillageEditScreen(
                        village = route.model,
                        onBack = { backStack.removeLastOrNull() },
                        onSave = { 
                            if (route.model == null) villageViewModel.addVillage(it) else villageViewModel.updateVillage(it)
                            backStack.removeLastOrNull()
                        }
                    )
                }
                entry<NavRoute.VillageDetails>(
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
                                "news" -> NavRoute.NewsScreen(route.villageId)
                                "banners" -> NavRoute.BannerScreen(route.villageId)
                                "notifications" -> NavRoute.NotificationScreen(route.villageId)
                                else -> null
                            }
                            nextRoute?.let { backStack.add(it) }
                        }
                    )
                }
                entry<NavRoute.LabourScreen>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
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
                entry<NavRoute.UpsertLabour>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                    val vm: LabourViewModel = viewModel(key = "Labour_${route.villageId}") { LabourViewModel(villageId = route.villageId) }
                    LabourEditScreen(
                        provider = route.provider,
                        onBack = { backStack.removeLastOrNull() },
                        onSave = { 
                            vm.saveProvider(it)
                            backStack.removeLastOrNull()
                        }
                    )
                }
                entry<NavRoute.ConstructionScreen>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
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
                entry<NavRoute.UpsertConstruction>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
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
                entry<NavRoute.TransportScreen>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
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
                entry<NavRoute.UpsertTransport>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
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
                entry<NavRoute.MandiScreen>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
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
                entry<NavRoute.UpsertMandi>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
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
                entry<NavRoute.HealthScreen>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
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
                entry<NavRoute.UpsertHealth>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
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
                entry<NavRoute.NewsScreen>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
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
                entry<NavRoute.UpsertNews>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
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
                entry<NavRoute.BannerScreen>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
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
                entry<NavRoute.UpsertBanner>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
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
                entry<NavRoute.NotificationScreen>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
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
                entry<NavRoute.UpsertNotification>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
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
        )
    }
}
