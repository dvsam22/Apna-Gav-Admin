package com.example.apnagavadmin.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
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
                is NavRoute.LabourHub -> last.copy(villageId = newVillageId)
                is NavRoute.ConstructionHub -> last.copy(villageId = newVillageId)
                is NavRoute.TransportHub -> last.copy(villageId = newVillageId)
                is NavRoute.MandiHub -> last.copy(villageId = newVillageId)
                is NavRoute.HealthHub -> last.copy(villageId = newVillageId)
                is NavRoute.NewsHub -> last.copy(villageId = newVillageId)
                is NavRoute.BannerHub -> last.copy(villageId = newVillageId)
                is NavRoute.NotificationHub -> last.copy(villageId = newVillageId)
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
                    viewModel = viewModel(),
                    onNavigateToDetails = { backStack.add(NavRoute.VillageDetails(it)) }
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
                            "labour" -> NavRoute.LabourHub(route.villageId)
                            "construction" -> NavRoute.ConstructionHub(route.villageId)
                            "transport" -> NavRoute.TransportHub(route.villageId)
                            "mandi" -> NavRoute.MandiHub(route.villageId)
                            "health" -> NavRoute.HealthHub(route.villageId)
                            "news" -> NavRoute.NewsHub(route.villageId)
                            "banners" -> NavRoute.BannerHub(route.villageId)
                            "notifications" -> NavRoute.NotificationHub(route.villageId)
                            else -> null
                        }
                        nextRoute?.let { backStack.add(it) }
                    }
                )
            }
            entry<NavRoute.LabourHub>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                LabourScreen(
                    viewModel = viewModel(key = "Labour_${route.villageId}") { LabourViewModel(villageId = route.villageId) },
                    onBack = { backStack.removeLastOrNull() },
                    villages = villages,
                    selectedVillageId = route.villageId,
                    onVillageChange = onVillageChange
                )
            }
            entry<NavRoute.ConstructionHub>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                ConstructionScreen(
                    viewModel = viewModel(key = "Construction_${route.villageId}") { ConstructionViewModel(villageId = route.villageId) },
                    onBack = { backStack.removeLastOrNull() },
                    villages = villages,
                    selectedVillageId = route.villageId,
                    onVillageChange = onVillageChange
                )
            }
            entry<NavRoute.TransportHub>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                TransportScreen(
                    viewModel = viewModel(key = "Transport_${route.villageId}") { TransportViewModel(villageId = route.villageId) },
                    onBack = { backStack.removeLastOrNull() },
                    villages = villages,
                    selectedVillageId = route.villageId,
                    onVillageChange = onVillageChange
                )
            }
            entry<NavRoute.MandiHub>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                MandiScreen(
                    viewModel = viewModel(key = "Mandi_${route.villageId}") { MandiViewModel(villageId = route.villageId) },
                    onBack = { backStack.removeLastOrNull() },
                    villages = villages,
                    selectedVillageId = route.villageId,
                    onVillageChange = onVillageChange
                )
            }
            entry<NavRoute.HealthHub>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                HealthScreen(
                    viewModel = viewModel(key = "Health_${route.villageId}") { HealthViewModel(villageId = route.villageId) },
                    onBack = { backStack.removeLastOrNull() },
                    villages = villages,
                    selectedVillageId = route.villageId,
                    onVillageChange = onVillageChange
                )
            }
            entry<NavRoute.NewsHub>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                NewsScreen(
                    viewModel = viewModel(key = "News_${route.villageId}") { NewsViewModel(villageId = route.villageId) },
                    onBack = { backStack.removeLastOrNull() },
                    villages = villages,
                    selectedVillageId = route.villageId,
                    onVillageChange = onVillageChange
                )
            }
            entry<NavRoute.BannerHub>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                BannerScreen(
                    viewModel = viewModel(key = "Banner_${route.villageId}") { BannerViewModel(villageId = route.villageId) },
                    onBack = { backStack.removeLastOrNull() },
                    villages = villages,
                    selectedVillageId = route.villageId,
                    onVillageChange = onVillageChange
                )
            }
            entry<NavRoute.NotificationHub>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                NotificationScreen(
                    viewModel = viewModel(key = "Notification_${route.villageId}") { NotificationViewModel(villageId = route.villageId) },
                    onBack = { backStack.removeLastOrNull() },
                    villages = villages,
                    selectedVillageId = route.villageId,
                    onVillageChange = onVillageChange
                )
            }
        }
    )
}
