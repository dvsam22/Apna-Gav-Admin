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
import com.example.apnagavadmin.data.model.LabourCategory
import com.example.apnagavadmin.data.model.LabourProvider

@Composable
fun LabourScreen(
    viewModel: LabourViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var selectedCategory by remember { mutableStateOf<LabourCategory?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }

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
            title = "${selectedCategory?.name} Details",
            subtitle = "${state.items.size} Available",
            onBack = { selectedCategory = null },
            onAdd = { showAddDialog = true },
            searchQuery = state.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) }
        ) { padding ->
            HubList(
                items = state.items.filter { it.name.contains(state.searchQuery, true) },
                isLoading = state.isLoading,
                error = state.error,
                contentPadding = padding
            ) { provider ->
                DetailCard(
                    title = provider.name,
                    location = provider.location,
                    details = listOf(
                        Icons.Rounded.Build to "Skills: ${provider.skills}",
                        Icons.Rounded.Info to "Charges: ${provider.charges}"
                    ),
                    onDelete = { viewModel.deleteProvider(provider.id) }
                )
            }

            if (showAddDialog) {
                AddLabourerDialog(
                    onDismiss = { showAddDialog = false },
                    onSave = {
                        viewModel.saveProvider(it)
                        showAddDialog = false
                    }
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
                title = { Text("Labour Board") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
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
                        name = category.name,
                        icon = getLabourIcon(category.name),
                        onClick = { onCategoryClick(category) }
                    )
                }
            }
            item { Spacer(Modifier.height(16.dp)) } // Bottom margin
        }
    }
}

@Composable
fun AddLabourerDialog(onDismiss: () -> Unit, onSave: (LabourProvider) -> Unit) {
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var skills by remember { mutableStateOf("") }
    var charges by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Labourer") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Location") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = contact, onValueChange = { contact = it }, label = { Text("Contact Number") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = skills, onValueChange = { skills = it }, label = { Text("Skills") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = charges, onValueChange = { charges = it }, label = { Text("Charges") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(onClick = { onSave(LabourProvider(name = name, location = location, contact = contact, skills = skills, charges = charges)) }) {
                Text("Save")
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

fun getLabourIcon(category: String): ImageVector {
    return when (category.lowercase()) {
        "plumber" -> Icons.Rounded.Build
        "electrician" -> Icons.Rounded.Settings
        "carpenter" -> Icons.Rounded.Edit
        "tailor" -> Icons.Rounded.AccountBox
        "rajmistri" -> Icons.Rounded.Home
        else -> Icons.Rounded.Person
    }
}
