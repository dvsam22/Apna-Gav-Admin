package com.example.apnagavadmin.ui.hubs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.example.apnagavadmin.R
import com.example.apnagavadmin.data.model.LocalizedString

// Target Green for "Call Now"
val CallNowGreen = Color(0xFF42C18E)
val PrimaryTeal = Color(0xFF009688)

@Composable
fun LocalizedTextField(
    value: LocalizedString,
    onValueChange: (LocalizedString) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = PrimaryTeal)
        OutlinedTextField(
            value = value.en,
            onValueChange = { onValueChange(value.copy(en = it)) },
            label = { Text(stringResource(R.string.english)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = singleLine
        )
        OutlinedTextField(
            value = value.hi,
            onValueChange = { onValueChange(value.copy(hi = it)) },
            label = { Text(stringResource(R.string.hindi)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = singleLine
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HubScaffold(
    title: String,
    subtitle: String? = null,
    onBack: () -> Unit,
    onAdd: () -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    villages: List<com.example.apnagavadmin.data.model.Village> = emptyList(),
    selectedVillageId: String = "",
    onVillageChange: (String) -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
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
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = stringResource(R.string.back))
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                title,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                                maxLines = 1
                            )
                            if (subtitle != null) {
                                Text(
                                    subtitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1
                                )
                            }
                        }
                        
                        if (villages.isNotEmpty()) {
                            VillageSelector(
                                villages = villages,
                                selectedVillageId = selectedVillageId,
                                onVillageChange = onVillageChange
                            )
                        }
                    }
                    TextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChange,
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
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .heightIn(min = 44.dp, max = 48.dp),
                        shape = MaterialTheme.shapes.medium,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdd,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(Icons.Rounded.Add, contentDescription = stringResource(R.string.add))
            }
        },
        content = content
    )
}

@Composable
fun VillageSelector(
    villages: List<com.example.apnagavadmin.data.model.Village>,
    selectedVillageId: String,
    onVillageChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedVillage = villages.find { it.id == selectedVillageId }
    val displayName = selectedVillage?.villageName?.text() ?: if (selectedVillageId == "all") stringResource(R.string.all_villages) else stringResource(R.string.select_village)

    Box(modifier = Modifier.padding(end = 8.dp)) {
        AssistChip(
            onClick = { expanded = true },
            label = { Text(displayName, maxLines = 1) },
            trailingIcon = { Icon(Icons.Rounded.ArrowDropDown, null, modifier = Modifier.size(18.dp)) },
            colors = AssistChipDefaults.assistChipColors(
                labelColor = PrimaryTeal,
                leadingIconContentColor = PrimaryTeal,
                trailingIconContentColor = PrimaryTeal
            ),
            border = AssistChipDefaults.assistChipBorder(enabled = true, borderColor = PrimaryTeal.copy(alpha = 0.3f))
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.all_villages)) },
                onClick = {
                    onVillageChange("all")
                    expanded = false
                },
                leadingIcon = { Icon(Icons.Rounded.Public, null, tint = PrimaryTeal) }
            )
            HorizontalDivider()
            villages.forEach { village ->
                DropdownMenuItem(
                    text = { Text(village.villageName.text()) },
                    onClick = {
                        onVillageChange(village.id)
                        expanded = false
                    },
                    leadingIcon = { Icon(Icons.Rounded.Home, null, tint = PrimaryTeal) }
                )
            }
        }
    }
}

@Composable
fun <T> HubList(
    items: List<T>,
    isLoading: Boolean,
    error: String?,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    itemContent: @Composable (T) -> Unit
) {
    val combinedPadding = PaddingValues(
        start = 16.dp + contentPadding.calculateStartPadding(LocalLayoutDirection.current),
        top = 16.dp + contentPadding.calculateTopPadding(),
        end = 16.dp + contentPadding.calculateEndPadding(LocalLayoutDirection.current),
        bottom = 16.dp + contentPadding.calculateBottomPadding()
    )
    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center).padding(contentPadding))
        } else if (error != null) {
            Text(error, color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center).padding(contentPadding))
        } else {
            androidx.compose.foundation.lazy.LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = combinedPadding,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items.size) { index ->
                    itemContent(items[index])
                }
            }
        }
    }
}

@Composable
fun CategoryTile(name: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(80.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = MaterialTheme.shapes.medium,
                color = PrimaryTeal.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = PrimaryTeal)
                }
            }
            Spacer(Modifier.width(16.dp))
            Text(name, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), modifier = Modifier.weight(1f))
            Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun DetailCard(
    title: String,
    location: String,
    details: List<Pair<ImageVector, String>>,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    icon: ImageVector = Icons.Rounded.Person,
    onCallClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    color = PrimaryTeal.copy(alpha = 0.1f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(icon, contentDescription = null, tint = PrimaryTeal)
                    }
                }
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.LocationOn, contentDescription = null, modifier = Modifier.size(14.dp), tint = PrimaryTeal)
                        Spacer(Modifier.width(4.dp))
                        Text(location, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Rounded.Edit, contentDescription = stringResource(R.string.edit), tint = PrimaryTeal.copy(alpha = 0.6f), modifier = Modifier.size(20.dp))
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Rounded.Delete, contentDescription = stringResource(R.string.delete), tint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f), modifier = Modifier.size(20.dp))
                    }
                }
            }
            
            Spacer(Modifier.height(16.dp))
            
            details.forEach { (icon, text) ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 6.dp)) {
                    Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = PrimaryTeal)
                    Spacer(Modifier.width(8.dp))
                    Text(text, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
                }
            }
            
            Spacer(Modifier.height(12.dp))
            
            Button(
                onClick = onCallClick,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(containerColor = CallNowGreen)
            ) {
                Icon(Icons.Rounded.Call, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.call_now), style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
