package com.example.apnagavadmin.ui.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
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
import com.example.apnagavadmin.navigation.NavRoute
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
                is NavRoute.FamilyFunctionScreen -> last.copy(villageId = newVillageId)
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
                mainGraph(backStack, villageViewModel)
                hubGraph(backStack, villages, onVillageChange)
            }
        )
    }
}
