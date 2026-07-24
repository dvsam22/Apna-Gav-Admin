package com.example.apnagavadmin.ui.village

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import androidx.compose.ui.res.stringResource
import com.example.apnagavadmin.R
import com.example.apnagavadmin.data.model.Village
import com.example.apnagavadmin.data.model.text
import com.example.apnagavadmin.ui.hubs.PrimaryTeal
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VillageListScreen(
    viewModel: VillageViewModel,
    onNavigateToDetails: (String) -> Unit,
    onNavigateToAddVillage: () -> Unit,
    onNavigateToEditVillage: (Village) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var showLanguageMenu by remember { mutableStateOf(false) }
    
    val config = androidx.compose.ui.platform.LocalConfiguration.current
    val currentLocale = config.locales[0].language

    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars,
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 3.dp
            ) {
                Column(
                    modifier = Modifier
                        .windowInsetsPadding(TopAppBarDefaults.windowInsets)
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, top = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(R.string.app_name),
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                            modifier = Modifier.weight(1f)
                        )
                        
                        // Language Selector
                        Box {
                            IconButton(onClick = { showLanguageMenu = true }) {
                                Icon(Icons.Rounded.Language, contentDescription = "Change Language", tint = PrimaryTeal)
                            }
                            DropdownMenu(
                                expanded = showLanguageMenu,
                                onDismissRequest = { showLanguageMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("English", fontWeight = if(currentLocale.startsWith("en")) FontWeight.Bold else FontWeight.Normal) },
                                    onClick = {
                                        showLanguageMenu = false
                                        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("en")
                                        AppCompatDelegate.setApplicationLocales(appLocale)
                                    },
                                    trailingIcon = { if(currentLocale.startsWith("en")) Icon(Icons.Rounded.Check, null, modifier = Modifier.size(18.dp)) }
                                )
                                DropdownMenuItem(
                                    text = { Text("हिंदी (Hindi)", fontWeight = if(currentLocale.startsWith("hi")) FontWeight.Bold else FontWeight.Normal) },
                                    onClick = {
                                        showLanguageMenu = false
                                        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("hi")
                                        AppCompatDelegate.setApplicationLocales(appLocale)
                                    },
                                    trailingIcon = { if(currentLocale.startsWith("hi")) Icon(Icons.Rounded.Check, null, modifier = Modifier.size(18.dp)) }
                                )
                            }
                        }
                    }
                    TextField(
                        value = state.searchQuery,
                        onValueChange = { viewModel.onSearchQueryChange(it) },
                        placeholder = { Text(stringResource(R.string.search), style = MaterialTheme.typography.bodyMedium) },
                        leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null, modifier = Modifier.size(20.dp)) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .heightIn(min = 44.dp, max = 48.dp),
                        shape = MaterialTheme.shapes.medium,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyMedium
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = state.selectedFilter == VillageFilter.ALL,
                            onClick = { viewModel.onFilterChange(VillageFilter.ALL) },
                            label = { Text("All (${state.villages.size})") }
                        )
                        FilterChip(
                            selected = state.selectedFilter == VillageFilter.ACTIVE,
                            onClick = { viewModel.onFilterChange(VillageFilter.ACTIVE) },
                            label = { Text("Active (${state.villages.count { it.isActive }})") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PrimaryTeal.copy(alpha = 0.15f),
                                selectedLabelColor = PrimaryTeal
                            )
                        )
                        FilterChip(
                            selected = state.selectedFilter == VillageFilter.INACTIVE,
                            onClick = { viewModel.onFilterChange(VillageFilter.INACTIVE) },
                            label = { Text("Deactive (${state.villages.count { !it.isActive }})") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
                                selectedLabelColor = MaterialTheme.colorScheme.error
                            )
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToAddVillage() },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(Icons.Rounded.Add, contentDescription = "Add Village", modifier = Modifier.size(24.dp))
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center).padding(padding))
            } else if (state.error != null) {
                Text(state.error ?: "Unknown error", color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center).padding(padding))
            } else {
                val filteredVillages = state.villages.filter { village ->
                    val matchesSearch = village.villageName.en.contains(state.searchQuery, ignoreCase = true) || 
                                        village.villageName.hi.contains(state.searchQuery, ignoreCase = true) ||
                                        village.district.en.contains(state.searchQuery, ignoreCase = true) ||
                                        village.district.hi.contains(state.searchQuery, ignoreCase = true)
                    val matchesFilter = when (state.selectedFilter) {
                        VillageFilter.ALL -> true
                        VillageFilter.ACTIVE -> village.isActive
                        VillageFilter.INACTIVE -> !village.isActive
                    }
                    matchesSearch && matchesFilter
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = padding.calculateTopPadding() + 16.dp,
                        end = 16.dp,
                        bottom = padding.calculateBottomPadding() + 16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(text = stringResource(R.string.villages_configured, filteredVillages.size), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    items(filteredVillages, key = { it.id }) { village ->
                        VillageItem(
                            village = village,
                            onClick = { onNavigateToDetails(village.id) },
                            onEdit = { onNavigateToEditVillage(village) },
                            onToggleActive = { viewModel.toggleVillageActive(village, it) },
                            onDelete = { viewModel.deleteVillage(village.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VillageItem(
    village: Village,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onToggleActive: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                color = PrimaryTeal.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Rounded.Home, contentDescription = null, tint = PrimaryTeal, modifier = Modifier.size(28.dp))
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(village.villageName.text(), style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                if (village.sarpanchName.get().isNotEmpty()) {
                    Text(stringResource(R.string.sarpanch_label, village.sarpanchName.text()), style = MaterialTheme.typography.bodySmall, color = PrimaryTeal)
                }
                if (village.sarpanchPhone.isNotEmpty()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.Phone, contentDescription = null, modifier = Modifier.size(12.dp), tint = PrimaryTeal.copy(alpha = 0.7f))
                        Spacer(Modifier.width(4.dp))
                        Text(village.sarpanchPhone, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Rounded.LocationOn, contentDescription = null, modifier = Modifier.size(14.dp), tint = PrimaryTeal)
                    Spacer(Modifier.width(4.dp))
                    Text("${village.district.text()}, ${village.state.text()}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text("PIN: ${village.pincode}", style = MaterialTheme.typography.labelMedium, color = PrimaryTeal)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Switch(checked = village.isActive, onCheckedChange = onToggleActive, colors = SwitchDefaults.colors(checkedThumbColor = PrimaryTeal))
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Rounded.Edit, contentDescription = "Edit", tint = PrimaryTeal.copy(alpha = 0.6f))
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Rounded.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f))
                    }
                }
            }
            Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
