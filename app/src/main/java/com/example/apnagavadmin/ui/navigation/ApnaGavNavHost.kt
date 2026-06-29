package com.example.apnagavadmin.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
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
                            else -> null
                        }
                        nextRoute?.let { backStack.add(it) }
                    }
                )
            }
            entry<NavRoute.LabourHub>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                LabourScreen(viewModel = viewModel { LabourViewModel(villageId = route.villageId) }, onBack = { backStack.removeLastOrNull() })
            }
            entry<NavRoute.ConstructionHub>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                ConstructionScreen(viewModel = viewModel { ConstructionViewModel(villageId = route.villageId) }, onBack = { backStack.removeLastOrNull() })
            }
            entry<NavRoute.TransportHub>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                TransportScreen(viewModel = viewModel { TransportViewModel(villageId = route.villageId) }, onBack = { backStack.removeLastOrNull() })
            }
            entry<NavRoute.MandiHub>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                MandiScreen(viewModel = viewModel { MandiViewModel(villageId = route.villageId) }, onBack = { backStack.removeLastOrNull() })
            }
            entry<NavRoute.HealthHub>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                HealthScreen(viewModel = viewModel { HealthViewModel(villageId = route.villageId) }, onBack = { backStack.removeLastOrNull() })
            }
            entry<NavRoute.NewsHub>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                NewsScreen(viewModel = viewModel { NewsViewModel(villageId = route.villageId) }, onBack = { backStack.removeLastOrNull() })
            }
            entry<NavRoute.BannerHub>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                BannerScreen(viewModel = viewModel { BannerViewModel(villageId = route.villageId) }, onBack = { backStack.removeLastOrNull() })
            }
        }
    )
}
