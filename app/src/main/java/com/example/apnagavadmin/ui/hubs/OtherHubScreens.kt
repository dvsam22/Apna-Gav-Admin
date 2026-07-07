package com.example.apnagavadmin.ui.hubs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.apnagavadmin.data.model.*

@Composable
fun ConstructionScreen(
    viewModel: ConstructionViewModel,
    onBack: () -> Unit,
    villages: List<Village> = emptyList(),
    selectedVillageId: String = "",
    onVillageChange: (String) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var editingItem by remember { mutableStateOf<ConstructionHub?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    if (selectedCategory == null) {
        ConstructionHubMainScreen(onBack, onCategoryClick = { selectedCategory = it; viewModel.selectCategory(it) })
    } else {
        HubScaffold(
            title = when (selectedCategory) {
                "bricks" -> "Bricks Suppliers"
                "hardware_shops" -> "Hardware Shops"
                else -> "Material Shops"
            },
            subtitle = "${state.items.size} Available",
            onBack = { selectedCategory = null },
            onAdd = { editingItem = null; showDialog = true },
            searchQuery = state.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            villages = villages,
            selectedVillageId = selectedVillageId,
            onVillageChange = onVillageChange
        ) { padding ->
            HubList(state.items.filter { it.shopName.contains(state.searchQuery, true) }, state.isLoading, state.error, contentPadding = padding) { item ->
                DetailCard(
                    title = item.shopName,
                    location = item.address,
                    details = item.products.map { Icons.Rounded.Build to "${it.name} - ₹${it.price}/${it.unit}" },
                    onEdit = { editingItem = item; showDialog = true },
                    onDelete = { viewModel.delete(item) }
                )
            }
            if (showDialog) AddConstructionHubDialog(editingItem, { showDialog = false; editingItem = null }, { viewModel.save(it); showDialog = false; editingItem = null })
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
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile("Hardware Shops", Icons.Rounded.Handyman) { onCategoryClick("hardware_shops") } } }
        }
    }
}

@Composable
fun TransportScreen(
    viewModel: TransportViewModel,
    onBack: () -> Unit,
    villages: List<Village> = emptyList(),
    selectedVillageId: String = "",
    onVillageChange: (String) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var editingItem by remember { mutableStateOf<TransportHub?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    if (selectedCategory == null) {
        TransportHubMainScreen(onBack, onCategoryClick = { selectedCategory = it; viewModel.selectCategory(it) })
    } else {
        HubScaffold(
            title = "${selectedCategory?.replaceFirstChar { it.uppercase() }} Providers",
            subtitle = "${state.items.size} Available",
            onBack = { selectedCategory = null },
            onAdd = { editingItem = null; showDialog = true },
            searchQuery = state.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            villages = villages,
            selectedVillageId = selectedVillageId,
            onVillageChange = onVillageChange
        ) { padding ->
            HubList(state.items.filter { it.name.contains(state.searchQuery, true) }, state.isLoading, state.error, contentPadding = padding) { item ->
                DetailCard(
                    title = item.name,
                    location = item.location,
                    details = listOf(Icons.Rounded.Build to "${item.categoryId.replaceFirstChar { it.uppercase() }} Type: ${item.vehicleType}"),
                    onEdit = { editingItem = item; showDialog = true },
                    onDelete = { viewModel.delete(item) }
                )
            }
            if (showDialog) AddTransportDialog(editingItem, { showDialog = false; editingItem = null }, { viewModel.save(it); showDialog = false; editingItem = null })
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
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile("Tractor", Icons.Rounded.Agriculture) { onCategoryClick("tractor") } } }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile("Car", Icons.Rounded.DirectionsCar) { onCategoryClick("car") } } }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile("Pickup/ Truck", Icons.Rounded.LocalShipping) { onCategoryClick("pickup") } } }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile("Loader", Icons.Rounded.LocalShipping) { onCategoryClick("loader") } } }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile("JCB", Icons.Rounded.Agriculture) { onCategoryClick("jcb") } } }
        }
    }
}

@Composable
fun MandiScreen(
    viewModel: MandiViewModel,
    onBack: () -> Unit,
    villages: List<Village> = emptyList(),
    selectedVillageId: String = "",
    onVillageChange: (String) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var editingItem by remember { mutableStateOf<MandiPrice?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    if (selectedCategory == null) {
        MandiHubMainScreen(onBack, onCategoryClick = { selectedCategory = it; viewModel.selectCategory(it) })
    } else {
        val title = when (selectedCategory) {
            "prices" -> "Crop Prices"
            "market" -> "Today's Market"
            else -> "Local Buyers"
        }

        HubScaffold(
            title = title,
            subtitle = "${state.items.size} Available",
            onBack = { selectedCategory = null },
            onAdd = { editingItem = null; showDialog = true },
            searchQuery = state.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            villages = villages,
            selectedVillageId = selectedVillageId,
            onVillageChange = onVillageChange
        ) { padding ->
            val filteredItems = state.items.filter { 
                (it.cropName.contains(state.searchQuery, true)) || 
                (it.buyerName.contains(state.searchQuery, true)) 
            }

            if (selectedCategory == "buyers") {
                HubList(filteredItems, state.isLoading, state.error, contentPadding = padding) { item ->
                    DetailCard(
                        title = item.buyerName,
                        location = item.address,
                        details = listOf(Icons.Rounded.Book to "For: ${item.cropName}"),
                        onEdit = { editingItem = item; showDialog = true },
                        onDelete = { viewModel.delete(item) }
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryTeal.copy(0.1f)),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Table Header
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp, top = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(if (selectedCategory == "market") "Vegetables" else "Crop", modifier = Modifier.weight(1.2f), style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                                Text("Unit", modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                                Text("Price", modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), textAlign = androidx.compose.ui.text.style.TextAlign.End)
                                Spacer(Modifier.width(80.dp)) // Offset for buttons
                            }

                            if (state.isLoading) {
                                Box(Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator(color = PrimaryTeal)
                                }
                            } else if (state.error != null) {
                                Text(state.error!!, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
                            } else {
                                androidx.compose.foundation.lazy.LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(16.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    items(filteredItems) { item ->
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(item.cropName, modifier = Modifier.weight(1.2f), style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                                            Text(item.unit, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge, color = Color.Gray, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                                            Text("₹${item.price}", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge, color = Color.Gray, textAlign = androidx.compose.ui.text.style.TextAlign.End)
                                            Row {
                                                IconButton(onClick = { editingItem = item; showDialog = true }, modifier = Modifier.size(40.dp)) {
                                                    Icon(Icons.Rounded.Edit, "Edit", tint = PrimaryTeal.copy(alpha = 0.4f), modifier = Modifier.size(18.dp))
                                                }
                                                IconButton(onClick = { viewModel.delete(item) }, modifier = Modifier.size(40.dp)) {
                                                    Icon(Icons.Rounded.Delete, "Delete", tint = MaterialTheme.colorScheme.error.copy(alpha = 0.4f), modifier = Modifier.size(18.dp))
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (showDialog) {
                when (selectedCategory) {
                    "buyers" -> GenericAddDialog(
                        "Add Buyer", 
                        listOf("Buyer Name", "Crop/Item", "Village/Address", "Contact"),
                        initialValues = if (editingItem != null) listOf(editingItem!!.buyerName, editingItem!!.cropName, editingItem!!.address, editingItem!!.contact) else null,
                        onDismiss = { showDialog = false; editingItem = null }
                    ) { vals ->
                        viewModel.save(editingItem?.copy(buyerName = vals[0], cropName = vals[1], address = vals[2], contact = vals[3]) ?: MandiPrice(buyerName = vals[0], cropName = vals[1], address = vals[2], contact = vals[3]))
                        showDialog = false; editingItem = null
                    }
                    else -> GenericAddDialog(
                        "Add Price", 
                        listOf("Crop Name", "Price", "Unit"),
                        initialValues = if (editingItem != null) listOf(editingItem!!.cropName, editingItem!!.price.toString(), editingItem!!.unit) else null,
                        onDismiss = { showDialog = false; editingItem = null }
                    ) { vals ->
                        viewModel.save(editingItem?.copy(cropName = vals[0], price = vals[1].toDoubleOrNull() ?: 0.0, unit = vals[2]) ?: MandiPrice(cropName = vals[0], price = vals[1].toDoubleOrNull() ?: 0.0, unit = vals[2]))
                        showDialog = false; editingItem = null
                    }
                }
            }
        }
    }
}

@Composable
fun MandiHubMainScreen(onBack: () -> Unit, onCategoryClick: (String) -> Unit) {
    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars,
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                windowInsets = TopAppBarDefaults.windowInsets,
                title = { Text("Mandi Hub", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
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
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile("Crop Prices", Icons.Rounded.Agriculture) { onCategoryClick("prices") } } }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile("Today's Market", Icons.Rounded.Storefront) { onCategoryClick("market") } } }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile("Local Buyers", Icons.Rounded.Groups) { onCategoryClick("buyers") } } }
        }
    }
}

@Composable
fun HealthScreen(
    viewModel: HealthViewModel,
    onBack: () -> Unit,
    villages: List<Village> = emptyList(),
    selectedVillageId: String = "",
    onVillageChange: (String) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var editingItem by remember { mutableStateOf<HealthHub?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    if (selectedCategory == null) {
        HealthHubMainScreen(onBack, onCategoryClick = { selectedCategory = it; viewModel.selectCategory(it) })
    } else {
        HubScaffold(
            title = when (selectedCategory) {
                "doctors" -> "Doctors"
                "hospitals" -> "Hospitals"
                "pharmacy" -> "Pharmacy"
                "ambulance" -> "Ambulance"
                "police" -> "Police"
                else -> "Health Hub"
            },
            subtitle = "${state.items.size} Available",
            onBack = { selectedCategory = null },
            onAdd = { editingItem = null; showDialog = true },
            searchQuery = state.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            villages = villages,
            selectedVillageId = selectedVillageId,
            onVillageChange = onVillageChange
        ) { padding ->
            HubList(state.items.filter { it.name.contains(state.searchQuery, true) }, state.isLoading, state.error, contentPadding = padding) { item ->
                val details = when (selectedCategory) {
                    "doctors" -> listOf(
                        Icons.Rounded.Person to "Specialisation: ${item.specialisation}",
                        Icons.Rounded.AccessTime to "Availability: ${item.availability}"
                    )
                    "hospitals" -> listOf(
                        Icons.Rounded.Bed to "Type: ${item.type}",
                        Icons.Rounded.MedicalServices to "Facilities: ${item.facilities}",
                        Icons.Rounded.AccessTime to "Open: ${item.availability}"
                    )
                    "pharmacy" -> listOf(
                        Icons.Rounded.MedicalServices to "Services: ${item.services}",
                        Icons.Rounded.AccessTime to "Open: ${item.availability}"
                    )
                    else -> listOf(
                        Icons.Rounded.Info to item.specialisation,
                        Icons.Rounded.AccessTime to item.availability
                    )
                }
                
                DetailCard(
                    title = item.name,
                    location = item.address,
                    details = details,
                    icon = if (selectedCategory == "hospitals") Icons.Rounded.LocalHospital else Icons.Rounded.Person,
                    onEdit = { editingItem = item; showDialog = true },
                    onDelete = { viewModel.delete(item) }
                )
            }
            if (showDialog) {
                val labels = when (selectedCategory) {
                    "doctors" -> listOf("Name", "Address", "Contact", "Specialisation", "Availability")
                    "hospitals" -> listOf("Name", "Address", "Contact", "Type", "Facilities", "Open Hours")
                    "pharmacy" -> listOf("Name", "Address", "Contact", "Services", "Open Hours")
                    else -> listOf("Name", "Address", "Contact")
                }
                val initialValues = when (selectedCategory) {
                    "doctors" -> if (editingItem != null) listOf(editingItem!!.name, editingItem!!.address, editingItem!!.contact, editingItem!!.specialisation, editingItem!!.availability) else null
                    "hospitals" -> if (editingItem != null) listOf(editingItem!!.name, editingItem!!.address, editingItem!!.contact, editingItem!!.type, editingItem!!.facilities, editingItem!!.availability) else null
                    "pharmacy" -> if (editingItem != null) listOf(editingItem!!.name, editingItem!!.address, editingItem!!.contact, editingItem!!.services, editingItem!!.availability) else null
                    else -> if (editingItem != null) listOf(editingItem!!.name, editingItem!!.address, editingItem!!.contact) else null
                }
                GenericAddDialog("Add Provider", labels, initialValues = initialValues, onDismiss = { showDialog = false; editingItem = null }) { vals ->
                    val hub = when (selectedCategory) {
                        "doctors" -> (editingItem?.copy(name = vals[0], address = vals[1], contact = vals[2], specialisation = vals[3], availability = vals[4]) ?: HealthHub(name = vals[0], address = vals[1], contact = vals[2], specialisation = vals[3], availability = vals[4]))
                        "hospitals" -> (editingItem?.copy(name = vals[0], address = vals[1], contact = vals[2], type = vals[3], facilities = vals[4], availability = vals[5]) ?: HealthHub(name = vals[0], address = vals[1], contact = vals[2], type = vals[3], facilities = vals[4], availability = vals[5]))
                        "pharmacy" -> (editingItem?.copy(name = vals[0], address = vals[1], contact = vals[2], services = vals[3], availability = vals[4]) ?: HealthHub(name = vals[0], address = vals[1], contact = vals[2], services = vals[3], availability = vals[4]))
                        else -> (editingItem?.copy(name = vals[0], address = vals[1], contact = vals[2]) ?: HealthHub(name = vals[0], address = vals[1], contact = vals[2]))
                    }
                    viewModel.save(hub)
                    showDialog = false; editingItem = null
                }
            }
        }
    }
}

@Composable
fun HealthHubMainScreen(onBack: () -> Unit, onCategoryClick: (String) -> Unit) {
    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars,
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                windowInsets = TopAppBarDefaults.windowInsets,
                title = { Text("Health & Emergency", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
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
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    EmergencyCard("Ambulance", Icons.Rounded.LocalHospital, Modifier.weight(1f)) { onCategoryClick("ambulance") }
                    EmergencyCard("Police", Icons.Rounded.NotificationsActive, Modifier.weight(1f)) { onCategoryClick("police") }
                }
            }
            item { CategoryTile("Doctors", Icons.Rounded.MedicalServices) { onCategoryClick("doctors") } }
            item { CategoryTile("Hospitals", Icons.Rounded.LocalHospital) { onCategoryClick("hospitals") } }
            item { CategoryTile("Pharmacy", Icons.Rounded.Medication) { onCategoryClick("pharmacy") } }
        }
    }
}

@Composable
fun EmergencyCard(title: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.height(140.dp),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1FBF9)), // Light teal background
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, null, tint = PrimaryTeal, modifier = Modifier.size(32.dp))
            Spacer(Modifier.height(8.dp))
            Text(title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = onClick,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                modifier = Modifier.height(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CallNowGreen)
            ) {
                Text("Call Now", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
fun NewsScreen(
    viewModel: NewsViewModel,
    onBack: () -> Unit,
    villages: List<Village> = emptyList(),
    selectedVillageId: String = "",
    onVillageChange: (String) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    var selectedNews by remember { mutableStateOf<News?>(null) }
    var editingItem by remember { mutableStateOf<News?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    if (selectedNews != null) {
        NewsDetailScreen(news = selectedNews!!, onBack = { selectedNews = null })
    } else {
        HubScaffold(
            title = "Local News",
            subtitle = "${state.items.size} Updates",
            onBack = onBack,
            onAdd = { editingItem = null; showDialog = true },
            searchQuery = state.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            villages = villages,
            selectedVillageId = selectedVillageId,
            onVillageChange = onVillageChange
        ) { padding ->
            val filteredItems = state.items.filter { it.title.contains(state.searchQuery, true) }
            val breakingNews = filteredItems.filter { it.category == "news" }
            val notices = filteredItems.filter { it.category == "notice" }

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
                if (breakingNews.isNotEmpty()) {
                    item { Text("Breaking News", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) }
                    items(breakingNews) { item ->
                        NewsCard(item, onClick = { selectedNews = item }, onEdit = { editingItem = item; showDialog = true }, onDelete = { viewModel.delete(item) })
                    }
                }

                if (notices.isNotEmpty()) {
                    item { Text("Notices", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) }
                    items(notices) { item ->
                        NoticeCard(item, onClick = { selectedNews = item }, onEdit = { editingItem = item; showDialog = true }, onDelete = { viewModel.delete(item) })
                    }
                }
            }
        }
    }

    if (showDialog) {
        GenericAddDialog(
            "Add News/Notice", 
            listOf("Title", "Description", "Image URL (Leave empty for Notice)"), 
            initialValues = if (editingItem != null) listOf(editingItem!!.title, editingItem!!.description, editingItem!!.image) else null,
            onDismiss = { showDialog = false; editingItem = null }
        ) { vals ->
            val type = if (vals[2].isEmpty()) "notice" else "news"
            viewModel.save(editingItem?.copy(title = vals[0], description = vals[1], image = vals[2], category = type) ?: News(title = vals[0], description = vals[1], image = vals[2], category = type))
            showDialog = false; editingItem = null
        }
    }
}

@Composable
fun NewsCard(news: News, onClick: () -> Unit, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = news.image,
                contentDescription = null,
                modifier = Modifier.size(80.dp).clip(MaterialTheme.shapes.medium),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(news.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), maxLines = 2)
                Text(news.description, style = MaterialTheme.typography.bodySmall, color = Color.Gray, maxLines = 2)
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                    Icon(Icons.Rounded.AccessTime, null, modifier = Modifier.size(14.dp), tint = PrimaryTeal)
                    Spacer(Modifier.width(4.dp))
                    Text("2 Hours", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
            }
            Row {
                IconButton(onClick = onEdit) { Icon(Icons.Rounded.Edit, null, tint = PrimaryTeal.copy(0.4f), modifier = Modifier.size(20.dp)) }
                IconButton(onClick = onDelete) { Icon(Icons.Rounded.Delete, null, tint = MaterialTheme.colorScheme.error.copy(0.4f), modifier = Modifier.size(20.dp)) }
            }
        }
    }
}

@Composable
fun NoticeCard(news: News, onClick: () -> Unit, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(48.dp).background(PrimaryTeal.copy(0.1f), MaterialTheme.shapes.medium), contentAlignment = Alignment.Center) {
                Icon(Icons.Rounded.Notifications, null, tint = PrimaryTeal)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(news.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), maxLines = 1)
                Text(news.description, style = MaterialTheme.typography.bodySmall, color = Color.Gray, maxLines = 2)
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                    Icon(Icons.Rounded.CalendarMonth, null, modifier = Modifier.size(14.dp), tint = PrimaryTeal)
                    Spacer(Modifier.width(4.dp))
                    Text("12 June 2025", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
            }
            Row {
                IconButton(onClick = onEdit) { Icon(Icons.Rounded.Edit, null, tint = PrimaryTeal.copy(0.4f), modifier = Modifier.size(20.dp)) }
                IconButton(onClick = onDelete) { Icon(Icons.Rounded.Delete, null, tint = MaterialTheme.colorScheme.error.copy(0.4f), modifier = Modifier.size(20.dp)) }
            }
        }
    }
}

@Composable
fun NewsDetailScreen(news: News, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("News Details", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Back") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(news.title, style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 12.dp)) {
                Icon(Icons.Rounded.AccessTime, null, modifier = Modifier.size(16.dp), tint = PrimaryTeal)
                Spacer(Modifier.width(4.dp))
                Text("Published: 2 hours ago", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
            }
            if (news.image.isNotEmpty()) {
                AsyncImage(
                    model = news.image,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(240.dp).clip(MaterialTheme.shapes.large),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
                Spacer(Modifier.height(16.dp))
            }
            Text(news.description, style = MaterialTheme.typography.bodyLarge, color = Color.DarkGray)
        }
    }
}

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel,
    onBack: () -> Unit,
    villages: List<Village> = emptyList(),
    selectedVillageId: String = "",
    onVillageChange: (String) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    var selectedNotification by remember { mutableStateOf<AppNotification?>(null) }
    var editingItem by remember { mutableStateOf<AppNotification?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    if (selectedNotification != null) {
        NotificationDetailScreen(notification = selectedNotification!!, onBack = { selectedNotification = null })
    } else {
        HubScaffold(
            title = "Notifications",
            subtitle = "${state.items.size} Total",
            onBack = onBack,
            onAdd = { editingItem = null; showDialog = true },
            searchQuery = state.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            villages = villages,
            selectedVillageId = selectedVillageId,
            onVillageChange = onVillageChange
        ) { padding ->
            val filteredItems = state.items.filter { it.title.contains(state.searchQuery, true) }
            
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
                if (filteredItems.isNotEmpty()) {
                    item { Text("Today", style = MaterialTheme.typography.labelLarge, color = Color.Gray) }
                    items(filteredItems) { item ->
                        NotificationCard(item, onClick = { selectedNotification = item }, onEdit = { editingItem = item; showDialog = true }, onDelete = { viewModel.delete(item) })
                    }
                }
            }
        }
    }

    if (showDialog) {
        GenericAddDialog(
            "Add/Edit Notification",
            listOf("Title", "Message"),
            initialValues = if (editingItem != null) listOf(editingItem!!.title, editingItem!!.message) else null,
            onDismiss = { showDialog = false; editingItem = null }
        ) { vals ->
            viewModel.save(editingItem?.copy(title = vals[0], message = vals[1]) ?: AppNotification(title = vals[0], message = vals[1]))
            showDialog = false; editingItem = null
        }
    }
}

@Composable
fun NotificationCard(notification: AppNotification, onClick: () -> Unit, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(56.dp).background(PrimaryTeal.copy(0.1f), MaterialTheme.shapes.medium),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Rounded.Notifications, null, tint = PrimaryTeal, modifier = Modifier.size(28.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(notification.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), maxLines = 1, modifier = Modifier.weight(1f))
                    Text("2 Hr ago", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
                Text(notification.message, style = MaterialTheme.typography.bodySmall, color = Color.Gray, maxLines = 2)
            }
            Row {
                IconButton(onClick = onEdit) { Icon(Icons.Rounded.Edit, null, tint = PrimaryTeal.copy(0.4f), modifier = Modifier.size(20.dp)) }
                IconButton(onClick = onDelete) { Icon(Icons.Rounded.Delete, null, tint = MaterialTheme.colorScheme.error.copy(0.4f), modifier = Modifier.size(20.dp)) }
            }
            Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, null, tint = PrimaryTeal, modifier = Modifier.size(24.dp))
        }
    }
}

@Composable
fun NotificationDetailScreen(notification: AppNotification, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("Notification Details", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Back") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(notification.title, style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 12.dp)) {
                Icon(Icons.Rounded.AccessTime, null, modifier = Modifier.size(16.dp), tint = PrimaryTeal)
                Spacer(Modifier.width(4.dp))
                Text("Published: Today, 01:30PM", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
            }
            Spacer(Modifier.height(16.dp))
            Text(notification.message, style = MaterialTheme.typography.bodyLarge, color = Color.DarkGray)
        }
    }
}

@Composable
fun BannerScreen(
    viewModel: BannerViewModel,
    onBack: () -> Unit,
    villages: List<Village> = emptyList(),
    selectedVillageId: String = "",
    onVillageChange: (String) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    var editingItem by remember { mutableStateOf<Banner?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    
    HubScaffold(
        title = "Village Banners", 
        subtitle = "${state.items.size} Banners", 
        onBack = onBack, 
        onAdd = { editingItem = null; showDialog = true }, 
        searchQuery = state.searchQuery, 
        onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
        villages = villages,
        selectedVillageId = selectedVillageId,
        onVillageChange = onVillageChange
    ) { padding ->
        val filteredItems = state.items.filter { 
            it.title.contains(state.searchQuery, true) || it.imageUrl.contains(state.searchQuery, true) 
        }

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
            items(filteredItems) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1FBF9)), // Light background like the image
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                maxLines = 2
                            )
                            Spacer(Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text("Disc. ", style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    text = "₹${item.discountText}",
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFFFFA000) // Orange color from image
                                    )
                                )
                            }
                        }
                        
                        AsyncImage(
                            model = item.imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(MaterialTheme.shapes.medium),
                            contentScale = androidx.compose.ui.layout.ContentScale.Fit
                        )
                        
                        Row {
                            IconButton(onClick = { editingItem = item; showDialog = true }) {
                                Icon(Icons.Rounded.Edit, "Edit", tint = PrimaryTeal.copy(alpha = 0.5f), modifier = Modifier.size(20.dp))
                            }
                            IconButton(onClick = { viewModel.delete(item) }) {
                                Icon(Icons.Rounded.Delete, "Delete", tint = MaterialTheme.colorScheme.error.copy(alpha = 0.5f), modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            }
        }

        if (showDialog) {
            GenericAddDialog(
                title = "Add/Edit Banner", 
                labels = listOf("Title/Offer Text", "Discount Amount (e.g. 10)", "Image URL", "Link"),
                initialValues = if (editingItem != null) listOf(editingItem!!.title, editingItem!!.discountText, editingItem!!.imageUrl, editingItem!!.link) else null,
                onDismiss = { showDialog = false; editingItem = null }
            ) { vals ->
                viewModel.save(editingItem?.copy(title = vals[0], discountText = vals[1], imageUrl = vals[2], link = vals[3]) ?: Banner(title = vals[0], discountText = vals[1], imageUrl = vals[2], link = vals[3]))
                showDialog = false; editingItem = null
            }
        }
    }
}

@Composable
fun HubCard(title: String, subtitle: String, trailing: String, onDelete: () -> Unit) {
    // Redundant now, but keeping for compatibility if any other call exists. 
    // Recommended to use DetailCard
    DetailCard(title, subtitle, listOf(Icons.Rounded.Info to trailing), onDelete, onEdit = {})
}

@Composable
fun GenericAddDialog(title: String, labels: List<String>, initialValues: List<String>? = null, onDismiss: () -> Unit, onSave: (List<String>) -> Unit) {
    val values = remember { mutableStateListOf(*Array(labels.size) { i -> initialValues?.getOrNull(i) ?: "" }) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                labels.forEachIndexed { index, label ->
                    OutlinedTextField(value = values[index], onValueChange = { values[index] = it }, label = { Text(label) }, modifier = Modifier.fillMaxWidth())
                }
            }
        },
        confirmButton = { Button(onClick = { onSave(values.toList()) }) { Text("Save") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

@Composable
fun AddConstructionHubDialog(item: ConstructionHub? = null, onDismiss: () -> Unit, onSave: (ConstructionHub) -> Unit) {
    var shopName by remember { mutableStateOf(item?.shopName ?: "") }
    var address by remember { mutableStateOf(item?.address ?: "") }
    var contact by remember { mutableStateOf(item?.contact ?: "") }
    val products = remember { mutableStateListOf<ConstructionProduct>().apply { item?.products?.let { addAll(it) } } }
    var pName by remember { mutableStateOf("") }; var pPrice by remember { mutableStateOf("") }; var pUnit by remember { mutableStateOf("") }

    AlertDialog(onDismissRequest = onDismiss, title = { Text(if (item == null) "Add Shop/Supplier" else "Edit Shop/Supplier") }, text = {
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
            items(products) { p -> 
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("• ${p.name}: ${p.price}/${p.unit}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
                    IconButton(onClick = { products.remove(p) }) { Icon(Icons.Rounded.Close, null, modifier = Modifier.size(16.dp)) }
                }
            }
        }
    }, confirmButton = { Button({ onSave(item?.copy(shopName = shopName, address = address, contact = contact, products = products.toList()) ?: ConstructionHub(shopName = shopName, address = address, contact = contact, products = products.toList())) }) { Text("Save") } }, dismissButton = { TextButton(onDismiss) { Text("Cancel") } })
}

@Composable
fun AddTransportDialog(item: TransportHub? = null, onDismiss: () -> Unit, onSave: (TransportHub) -> Unit) {
    var name by remember { mutableStateOf(item?.name ?: "") }
    var loc by remember { mutableStateOf(item?.location ?: "") }
    var con by remember { mutableStateOf(item?.contact ?: "") }
    var type by remember { mutableStateOf(item?.vehicleType ?: "") }
    AlertDialog(onDismissRequest = onDismiss, title = { Text(if (item == null) "Add Provider" else "Edit Provider") }, text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(name, { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(loc, { loc = it }, label = { Text("Location") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(con, { con = it }, label = { Text("Contact") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(type, { type = it }, label = { Text("Vehicle Type") }, modifier = Modifier.fillMaxWidth())
        }
    }, confirmButton = { Button({ onSave(item?.copy(name = name, location = loc, contact = con, vehicleType = type) ?: TransportHub(name = name, location = loc, contact = con, vehicleType = type)) }) { Text("Save") } }, dismissButton = { TextButton(onDismiss) { Text("Cancel") } })
}
