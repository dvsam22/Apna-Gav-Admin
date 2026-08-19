package com.example.apnagavadmin.ui.hubs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.example.apnagavadmin.R
import com.example.apnagavadmin.data.model.LabourCategory
import com.example.apnagavadmin.data.model.LabourProvider
import com.example.apnagavadmin.data.model.Village
import com.example.apnagavadmin.data.model.text

@Composable
fun LabourScreen(
    viewModel: LabourViewModel,
    onBack: () -> Unit,
    villages: List<Village> = emptyList(),
    selectedVillageId: String = "",
    onVillageChange: (String) -> Unit = {},
    onNavigateToEdit: (String, LabourProvider?) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var selectedCategory by remember { mutableStateOf<LabourCategory?>(null) }

    if (selectedCategory == null) {
        LabourBoardMainScreen(
            categories = state.categories,
            onBack = onBack,
            onCategoryClick = { category ->
                selectedCategory = category
                viewModel.selectCategory(category.id)
            }
        )
    } else {
        HubScaffold(
            title = "${selectedCategory?.name?.text()} ${stringResource(R.string.details)}",
            subtitle = "${state.items.size} ${stringResource(R.string.available)}",
            onBack = { selectedCategory = null },
            onAdd = { onNavigateToEdit(selectedCategory!!.id, null) },
            searchQuery = state.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            villages = villages,
            selectedVillageId = selectedVillageId,
            onVillageChange = onVillageChange
        ) { padding ->
            HubList(
                items = state.items.filter { 
                    it.name.en.contains(state.searchQuery, true) || 
                    it.name.hi.contains(state.searchQuery, true) 
                },
                isLoading = state.isLoading,
                error = state.error,
                contentPadding = padding
            ) { provider ->
                DetailCard(
                    title = provider.name.text(),
                    location = provider.location.text(),
                    details = listOf(
                        Icons.Rounded.Build to stringResource(R.string.skills_label, provider.skills.text()),
                        Icons.Rounded.Info to stringResource(R.string.charges_label, provider.charges.text())
                    ),
                    onEdit = { onNavigateToEdit(selectedCategory!!.id, provider) },
                    onDelete = { viewModel.delete(provider) }
                )
            }
        }
    }
}

@Composable
fun LabourBoardMainScreen(
    categories: List<LabourCategory>,
    onBack: () -> Unit,
    onCategoryClick: (LabourCategory) -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars,
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                windowInsets = TopAppBarDefaults.windowInsets,
                title = { Text(stringResource(R.string.labour_board)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ) { padding ->
        androidx.compose.foundation.lazy.LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                top = padding.calculateTopPadding() + 16.dp,
                end = 16.dp,
                bottom = padding.calculateBottomPadding() + 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(Modifier.height(16.dp)) } // Top margin within the scroll area
            items(categories) { category ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    CategoryTile(
                        name = category.name.text(),
                        icon = getLabourIcon(category.name.en),
                        onClick = { onCategoryClick(category) }
                    )
                }
            }
            item { Spacer(Modifier.height(16.dp)) } // Bottom margin
        }
    }
}

fun getLabourIcon(category: String): ImageVector {
    return when (category.lowercase()) {
        "plumber" -> Icons.Rounded.Build
        "electrician" -> Icons.Rounded.Settings
        "carpenter" -> Icons.Rounded.Edit
        "tailor" -> Icons.Rounded.AccountBox
        "painter" -> Icons.Rounded.Brush
        "rajmistri" -> Icons.Rounded.Home
        else -> Icons.Rounded.Person
    }
}
