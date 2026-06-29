package com.example.apnagavadmin.ui.hubs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.apnagavadmin.data.model.*

@Composable
fun ConstructionScreen(viewModel: ConstructionViewModel, onBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    if (selectedCategory == null) {
        ConstructionHubMainScreen(onBack, onCategoryClick = { selectedCategory = it; viewModel.selectCategory(it) })
    } else {
        HubScaffold(
            title = if (selectedCategory == "bricks") "Bricks Suppliers" else "Material Shops",
            subtitle = "${state.items.size} Available",
            onBack = { selectedCategory = null },
            onAdd = { showDialog = true },
            searchQuery = state.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) }
        ) { padding ->
            HubList(state.items.filter { it.shopName.contains(state.searchQuery, true) }, state.isLoading, state.error, contentPadding = padding) { item ->
                DetailCard(
                    title = item.shopName,
                    location = item.address,
                    details = item.products.map { Icons.Rounded.Build to "${it.name} - ₹${it.price}/${it.unit}" },
                    onDelete = { viewModel.delete(item.id) }
                )
            }
            if (showDialog) AddConstructionHubDialog({ showDialog = false }, { viewModel.save(it); showDialog = false })
        }
    }
}

@Composable
fun ConstructionHubMainScreen(onBack: () -> Unit, onCategoryClick: (String) -> Unit) {
    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars,
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                windowInsets = TopAppBarDefaults.windowInsets,
                title = { Text("Construction Hub", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Back") } })
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
            item { Spacer(Modifier.height(16.dp)) }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile("Bricks", Icons.Rounded.Build) { onCategoryClick("bricks") } } }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile("Material Shops", Icons.Rounded.ShoppingCart) { onCategoryClick("material_shops") } } }
        }
    }
}

@Composable
fun TransportScreen(viewModel: TransportViewModel, onBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    if (selectedCategory == null) {
        TransportHubMainScreen(onBack, onCategoryClick = { selectedCategory = it; viewModel.selectCategory(it) })
    } else {
        HubScaffold(
            title = "${selectedCategory?.replaceFirstChar { it.uppercase() }} Providers",
            subtitle = "${state.items.size} Available",
            onBack = { selectedCategory = null },
            onAdd = { showDialog = true },
            searchQuery = state.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) }
        ) { padding ->
            HubList(state.items.filter { it.name.contains(state.searchQuery, true) }, state.isLoading, state.error, contentPadding = padding) { item ->
                DetailCard(
                    title = item.name,
                    location = item.location,
                    details = listOf(Icons.Rounded.Build to "${item.categoryId.replaceFirstChar { it.uppercase() }} Type: ${item.vehicleType}"),
                    onDelete = { viewModel.delete(item.id) }
                )
            }
            if (showDialog) AddTransportDialog({ showDialog = false }, { viewModel.save(it); showDialog = false })
        }
    }
}

@Composable
fun TransportHubMainScreen(onBack: () -> Unit, onCategoryClick: (String) -> Unit) {
    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars,
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                windowInsets = TopAppBarDefaults.windowInsets,
                title = { Text("Transport & Rentals", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Back") } })
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
            item { Spacer(Modifier.height(16.dp)) }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile("Tractor", Icons.Rounded.Build) { onCategoryClick("tractor") } } }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile("Car", Icons.Rounded.ShoppingCart) { onCategoryClick("car") } } }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile("Pickup", Icons.Rounded.Home) { onCategoryClick("pickup") } } }
        }
    }
}

@Composable
fun MandiScreen(viewModel: MandiViewModel, onBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    HubScaffold("Mandi Prices", "${state.items.size} Crops Listed", onBack, { showDialog = true }, state.searchQuery, { viewModel.onSearchQueryChange(it) }) { padding ->
        HubList(state.items.filter { it.cropName.contains(state.searchQuery, true) }, state.isLoading, state.error, contentPadding = padding) { item ->
            DetailCard(
                title = item.cropName,
                location = "Market Central",
                details = listOf(
                    Icons.Rounded.Info to "Price: ₹${item.price} / ${item.unit}",
                    Icons.Rounded.Notifications to "Trend: ${item.trend.uppercase()}"
                ),
                onDelete = { viewModel.delete(item.id) }
            )
        }
        if (showDialog) GenericAddDialog("Add Price", listOf("Crop Name", "Price", "Unit")) { vals ->
            viewModel.save(MandiPrice(cropName = vals[0], price = vals[1].toDoubleOrNull() ?: 0.0, unit = vals[2]))
            showDialog = false
        }
    }
}

@Composable
fun HealthScreen(viewModel: HealthViewModel, onBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    HubScaffold("Health Hub", "${state.items.size} Providers Available", onBack, { showDialog = true }, state.searchQuery, { viewModel.onSearchQueryChange(it) }) { padding ->
        HubList(state.items.filter { it.doctorName.contains(state.searchQuery, true) }, state.isLoading, state.error, contentPadding = padding) { item ->
            DetailCard(
                title = item.doctorName,
                location = item.hospitalName,
                details = listOf(
                    Icons.Rounded.Favorite to "Specialty: ${item.specialty}",
                    Icons.Rounded.LocationOn to "Address: ${item.address}"
                ),
                onDelete = { viewModel.delete(item.id) }
            )
        }
        if (showDialog) GenericAddDialog("Add Doctor", listOf("Name", "Specialty", "Hospital", "Contact", "Address")) { vals ->
            viewModel.save(HealthHub(doctorName = vals[0], specialty = vals[1], hospitalName = vals[2], contact = vals[3], address = vals[4]))
            showDialog = false
        }
    }
}

@Composable
fun NewsScreen(viewModel: NewsViewModel, onBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    HubScaffold("Village News", "${state.items.size} Updates", onBack, { showDialog = true }, state.searchQuery, { viewModel.onSearchQueryChange(it) }) { padding ->
        HubList(state.items.filter { it.title.contains(state.searchQuery, true) }, state.isLoading, state.error, contentPadding = padding) { item ->
            Card(modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.extraLarge, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(item.title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                    Text(item.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        IconButton(onClick = { viewModel.delete(item.id) }) { Icon(Icons.Rounded.Delete, "Delete", tint = MaterialTheme.colorScheme.error) }
                    }
                }
            }
        }
        if (showDialog) GenericAddDialog("Add News", listOf("Title", "Description", "Image URL")) { vals ->
            viewModel.save(News(title = vals[0], description = vals[1], image = vals[2]))
            showDialog = false
        }
    }
}

@Composable
fun BannerScreen(viewModel: BannerViewModel, onBack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    HubScaffold("Village Banners", "${state.items.size} Banners", onBack, { showDialog = true }, state.searchQuery, { viewModel.onSearchQueryChange(it) }) { padding ->
        HubList(state.items.filter { it.imageUrl.contains(state.searchQuery, true) }, state.isLoading, state.error, contentPadding = padding) { item ->
            Card(modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(80.dp).background(PrimaryTeal.copy(0.1f), MaterialTheme.shapes.medium), contentAlignment = Alignment.Center) {
                        Icon(Icons.Rounded.Star, null, tint = PrimaryTeal)
                    }
                    Spacer(Modifier.width(16.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Banner URL", style = MaterialTheme.typography.labelSmall)
                        Text(item.imageUrl, maxLines = 1, style = MaterialTheme.typography.bodySmall)
                        Text("Link", style = MaterialTheme.typography.labelSmall)
                        Text(item.link, maxLines = 1, style = MaterialTheme.typography.bodySmall)
                    }
                    IconButton(onClick = { viewModel.delete(item.id) }) { Icon(Icons.Rounded.Delete, "Delete", tint = MaterialTheme.colorScheme.error) }
                }
            }
        }
        if (showDialog) GenericAddDialog("Add Banner", listOf("Image URL", "Link")) { vals ->
            viewModel.save(Banner(imageUrl = vals[0], link = vals[1]))
            showDialog = false
        }
    }
}

@Composable
fun HubCard(title: String, subtitle: String, trailing: String, onDelete: () -> Unit) {
    // Redundant now, but keeping for compatibility if any other call exists. 
    // Recommended to use DetailCard
    DetailCard(title, subtitle, listOf(Icons.Rounded.Info to trailing), onDelete)
}

@Composable
fun GenericAddDialog(title: String, labels: List<String>, onSave: (List<String>) -> Unit) {
    val values = remember { mutableStateListOf(*Array(labels.size) { "" }) }
    AlertDialog(
        onDismissRequest = {},
        title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                labels.forEachIndexed { index, label ->
                    OutlinedTextField(value = values[index], onValueChange = { values[index] = it }, label = { Text(label) }, modifier = Modifier.fillMaxWidth())
                }
            }
        },
        confirmButton = { Button(onClick = { onSave(values.toList()) }) { Text("Save") } },
        dismissButton = { TextButton(onClick = {}) { Text("Cancel") } }
    )
}

@Composable
fun AddConstructionHubDialog(onDismiss: () -> Unit, onSave: (ConstructionHub) -> Unit) {
    var shopName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    val products = remember { mutableStateListOf<ConstructionProduct>() }
    var pName by remember { mutableStateOf("") }; var pPrice by remember { mutableStateOf("") }; var pUnit by remember { mutableStateOf("") }

    AlertDialog(onDismissRequest = onDismiss, title = { Text("Add Shop/Supplier") }, text = {
        androidx.compose.foundation.lazy.LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            item {
                OutlinedTextField(shopName, { shopName = it }, label = { Text("Shop Name") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(address, { address = it }, label = { Text("Location") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(contact, { contact = it }, label = { Text("Contact") }, modifier = Modifier.fillMaxWidth())
                HorizontalDivider(Modifier.padding(vertical = 8.dp))
                Text("Add Product", style = MaterialTheme.typography.titleSmall)
                OutlinedTextField(pName, { pName = it }, label = { Text("Name") })
                OutlinedTextField(pPrice, { pPrice = it }, label = { Text("Price") })
                OutlinedTextField(pUnit, { pUnit = it }, label = { Text("Unit") })
                Button({ if (pName.isNotEmpty()) { products.add(ConstructionProduct(pName, pPrice, pUnit)); pName = ""; pPrice = ""; pUnit = "" } }) { Text("Add Product") }
            }
            items(products) { p -> Text("• ${p.name}: ${p.price}/${p.unit}", style = MaterialTheme.typography.bodySmall) }
        }
    }, confirmButton = { Button({ onSave(ConstructionHub(shopName = shopName, address = address, contact = contact, products = products.toList())) }) { Text("Save") } }, dismissButton = { TextButton(onDismiss) { Text("Cancel") } })
}

@Composable
fun AddTransportDialog(onDismiss: () -> Unit, onSave: (TransportHub) -> Unit) {
    var name by remember { mutableStateOf("") }
    var loc by remember { mutableStateOf("") }
    var con by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = onDismiss, title = { Text("Add Provider") }, text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(name, { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(loc, { loc = it }, label = { Text("Location") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(con, { con = it }, label = { Text("Contact") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(type, { type = it }, label = { Text("Vehicle Type") }, modifier = Modifier.fillMaxWidth())
        }
    }, confirmButton = { Button({ onSave(TransportHub(name = name, location = loc, contact = con, vehicleType = type)) }) { Text("Save") } }, dismissButton = { TextButton(onDismiss) { Text("Cancel") } })
}
