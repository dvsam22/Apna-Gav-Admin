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
import androidx.compose.ui.res.stringResource
import com.example.apnagavadmin.R
import com.example.apnagavadmin.data.model.*
import com.example.apnagavadmin.data.model.text

@Composable
fun ConstructionScreen(
    viewModel: ConstructionViewModel,
    onBack: () -> Unit,
    villages: List<Village> = emptyList(),
    selectedVillageId: String = "",
    onVillageChange: (String) -> Unit = {},
    onNavigateToEdit: (String, ConstructionHub?) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    if (selectedCategory == null) {
        ConstructionHubMainScreen(onBack, onCategoryClick = { selectedCategory = it; viewModel.selectCategory(it) })
    } else {
        HubScaffold(
            title = when (selectedCategory) {
                "bricks" -> stringResource(R.string.bricks)
                "hardware_shops" -> stringResource(R.string.hardware_shops)
                else -> stringResource(R.string.material_shops)
            },
            subtitle = "${state.items.size} ${stringResource(R.string.available)}",
            onBack = { selectedCategory = null },
            onAdd = { onNavigateToEdit(selectedCategory!!, null) },
            searchQuery = state.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            villages = villages,
            selectedVillageId = selectedVillageId,
            onVillageChange = onVillageChange
        ) { padding ->
            HubList(state.items.filter { 
                it.shopName.en.contains(state.searchQuery, true) || 
                it.shopName.hi.contains(state.searchQuery, true) 
            }, state.isLoading, state.error, contentPadding = padding) { item ->
                DetailCard(
                    title = item.shopName.text(),
                    location = item.address.text(),
                    details = item.products.map { Icons.Rounded.Build to "${it.name.text()} - ₹${it.price}/${it.unit.text()}" },
                    onEdit = { onNavigateToEdit(selectedCategory!!, item) },
                    onDelete = { viewModel.delete(item) }
                )
            }
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
                title = { Text(stringResource(R.string.construction_hub), style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, stringResource(R.string.back)) } })
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
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile(stringResource(R.string.bricks), Icons.Rounded.Build) { onCategoryClick("bricks") } } }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile(stringResource(R.string.material_shops), Icons.Rounded.ShoppingCart) { onCategoryClick("material_shops") } } }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile(stringResource(R.string.hardware_shops), Icons.Rounded.Handyman) { onCategoryClick("hardware_shops") } } }
        }
    }
}

@Composable
fun TransportScreen(
    viewModel: TransportViewModel,
    onBack: () -> Unit,
    villages: List<Village> = emptyList(),
    selectedVillageId: String = "",
    onVillageChange: (String) -> Unit = {},
    onNavigateToEdit: (String, TransportHub?) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    if (selectedCategory == null) {
        TransportHubMainScreen(onBack, onCategoryClick = { selectedCategory = it; viewModel.selectCategory(it) })
    } else {
        HubScaffold(
            title = when (selectedCategory) {
                "tractor" -> stringResource(R.string.tractor)
                "car" -> stringResource(R.string.car)
                "pickup" -> stringResource(R.string.pickup_truck)
                "loader" -> stringResource(R.string.loader)
                "jcb" -> stringResource(R.string.jcb)
                else -> stringResource(R.string.transport_rentals)
            },
            subtitle = "${state.items.size} ${stringResource(R.string.available)}",
            onBack = { selectedCategory = null },
            onAdd = { onNavigateToEdit(selectedCategory!!, null) },
            searchQuery = state.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            villages = villages,
            selectedVillageId = selectedVillageId,
            onVillageChange = onVillageChange
        ) { padding ->
            HubList(state.items.filter { 
                it.name.en.contains(state.searchQuery, true) || 
                it.name.hi.contains(state.searchQuery, true) 
            }, state.isLoading, state.error, contentPadding = padding) { item ->
                DetailCard(
                    title = item.name.text(),
                    location = item.location.text(),
                    details = listOf(Icons.Rounded.Build to "${item.categoryId.replaceFirstChar { it.uppercase() }} Type: ${item.vehicleType.text()}"),
                    onEdit = { onNavigateToEdit(selectedCategory!!, item) },
                    onDelete = { viewModel.delete(item) }
                )
            }
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
                title = { Text(stringResource(R.string.transport_rentals), style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, stringResource(R.string.back)) } })
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
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile(stringResource(R.string.tractor), Icons.Rounded.Agriculture) { onCategoryClick("tractor") } } }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile(stringResource(R.string.car), Icons.Rounded.DirectionsCar) { onCategoryClick("car") } } }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile(stringResource(R.string.pickup_truck), Icons.Rounded.LocalShipping) { onCategoryClick("pickup") } } }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile(stringResource(R.string.loader), Icons.Rounded.LocalShipping) { onCategoryClick("loader") } } }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile(stringResource(R.string.jcb), Icons.Rounded.Agriculture) { onCategoryClick("jcb") } } }
        }
    }
}

@Composable
fun MandiScreen(
    viewModel: MandiViewModel,
    onBack: () -> Unit,
    villages: List<Village> = emptyList(),
    selectedVillageId: String = "",
    onVillageChange: (String) -> Unit = {},
    onNavigateToEdit: (String, MandiPrice?) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    if (selectedCategory == null) {
        MandiHubMainScreen(onBack, onCategoryClick = { selectedCategory = it; viewModel.selectCategory(it) })
    } else {
        val title = when (selectedCategory) {
            "prices" -> stringResource(R.string.crop_prices)
            "market" -> stringResource(R.string.todays_market)
            else -> stringResource(R.string.local_buyers)
        }

        HubScaffold(
            title = title,
            subtitle = "${state.items.size} ${stringResource(R.string.available)}",
            onBack = { selectedCategory = null },
            onAdd = { onNavigateToEdit(selectedCategory!!, null) },
            searchQuery = state.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            villages = villages,
            selectedVillageId = selectedVillageId,
            onVillageChange = onVillageChange
        ) { padding ->
            val filteredItems = state.items.filter { 
                (it.cropName.en.contains(state.searchQuery, true) || it.cropName.hi.contains(state.searchQuery, true)) || 
                (it.buyerName.en.contains(state.searchQuery, true) || it.buyerName.hi.contains(state.searchQuery, true)) 
            }

            if (selectedCategory == "buyers") {
                HubList(filteredItems, state.isLoading, state.error, contentPadding = padding) { item ->
                    DetailCard(
                        title = item.buyerName.text(),
                        location = item.address.text(),
                        details = listOf(Icons.Rounded.Book to "For: ${item.cropName.text()}"),
                        onEdit = { onNavigateToEdit(selectedCategory!!, item) },
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
                                Text(state.error?.message ?: "", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
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
                                            Text(item.cropName.text(), modifier = Modifier.weight(1.2f), style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                                            Text(item.unit.text(), modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge, color = Color.Gray, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                                            Text("₹${item.price}", modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge, color = Color.Gray, textAlign = androidx.compose.ui.text.style.TextAlign.End)
                                            Row {
                                                IconButton(onClick = { onNavigateToEdit(selectedCategory!!, item) }, modifier = Modifier.size(40.dp)) {
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
                title = { Text(stringResource(R.string.mandi_hub), style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, stringResource(R.string.back)) } })
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
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile(stringResource(R.string.crop_prices), Icons.Rounded.Agriculture) { onCategoryClick("prices") } } }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile(stringResource(R.string.todays_market), Icons.Rounded.Storefront) { onCategoryClick("market") } } }
            item { Box(Modifier.padding(horizontal = 16.dp)) { CategoryTile(stringResource(R.string.local_buyers), Icons.Rounded.Groups) { onCategoryClick("buyers") } } }
        }
    }
}

@Composable
fun HealthScreen(
    viewModel: HealthViewModel,
    onBack: () -> Unit,
    villages: List<Village> = emptyList(),
    selectedVillageId: String = "",
    onVillageChange: (String) -> Unit = {},
    onNavigateToEdit: (String, HealthHub?) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    if (selectedCategory == null) {
        HealthHubMainScreen(onBack, onCategoryClick = { selectedCategory = it; viewModel.selectCategory(it) })
    } else {
        HubScaffold(
            title = when (selectedCategory) {
                "doctors" -> stringResource(R.string.doctors)
                "hospitals" -> stringResource(R.string.hospitals)
                "pharmacy" -> stringResource(R.string.pharmacy)
                "ambulance" -> stringResource(R.string.ambulance)
                "police" -> stringResource(R.string.police)
                else -> stringResource(R.string.health_emergency)
            },
            subtitle = "${state.items.size} ${stringResource(R.string.available)}",
            onBack = { selectedCategory = null },
            onAdd = { onNavigateToEdit(selectedCategory!!, null) },
            searchQuery = state.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            villages = villages,
            selectedVillageId = selectedVillageId,
            onVillageChange = onVillageChange
        ) { padding ->
            HubList(state.items.filter { 
                it.name.en.contains(state.searchQuery, true) || 
                it.name.hi.contains(state.searchQuery, true) 
            }, state.isLoading, state.error, contentPadding = padding) { item ->
                val details = when (selectedCategory) {
                    "doctors" -> listOf(
                        Icons.Rounded.Person to "Specialisation: ${item.specialisation.text()}",
                        Icons.Rounded.AccessTime to "Availability: ${item.availability.text()}"
                    )
                    "hospitals" -> listOf(
                        Icons.Rounded.Bed to "Type: ${item.type.text()}",
                        Icons.Rounded.MedicalServices to "Facilities: ${item.facilities.text()}",
                        Icons.Rounded.AccessTime to "Open: ${item.availability.text()}"
                    )
                    "pharmacy" -> listOf(
                        Icons.Rounded.MedicalServices to "Services: ${item.services.text()}",
                        Icons.Rounded.AccessTime to "Open: ${item.availability.text()}"
                    )
                    else -> listOf(
                        Icons.Rounded.Info to item.specialisation.text(),
                        Icons.Rounded.AccessTime to item.availability.text()
                    )
                }
                
                DetailCard(
                    title = item.name.text(),
                    location = item.address.text(),
                    details = details,
                    icon = if (selectedCategory == "hospitals") Icons.Rounded.LocalHospital else Icons.Rounded.Person,
                    onEdit = { onNavigateToEdit(selectedCategory!!, item) },
                    onDelete = { viewModel.delete(item) }
                )
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
                title = { Text(stringResource(R.string.health_emergency), style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, stringResource(R.string.back)) } })
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
                    EmergencyCard(stringResource(R.string.ambulance), Icons.Rounded.LocalHospital, Modifier.weight(1f)) { onCategoryClick("ambulance") }
                    EmergencyCard(stringResource(R.string.police), Icons.Rounded.NotificationsActive, Modifier.weight(1f)) { onCategoryClick("police") }
                }
            }
            item { CategoryTile(stringResource(R.string.doctors), Icons.Rounded.MedicalServices) { onCategoryClick("doctors") } }
            item { CategoryTile(stringResource(R.string.hospitals), Icons.Rounded.LocalHospital) { onCategoryClick("hospitals") } }
            item { CategoryTile(stringResource(R.string.pharmacy), Icons.Rounded.Medication) { onCategoryClick("pharmacy") } }
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
                Text(stringResource(R.string.call_now), style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
fun FamilyFunctionScreen(
    viewModel: FamilyFunctionViewModel,
    onBack: () -> Unit,
    villages: List<Village> = emptyList(),
    selectedVillageId: String = "",
    onVillageChange: (String) -> Unit = {},
    onNavigateToEdit: (String, FamilyFunctionHub?) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    if (selectedCategory == null) {
        FamilyFunctionMainScreen(onBack, onCategoryClick = { selectedCategory = it; viewModel.selectCategory(it) })
    } else {
        HubScaffold(
            title = when (selectedCategory) {
                "tent" -> stringResource(R.string.tent_decor)
                "catering" -> stringResource(R.string.catering_halwai)
                "photo" -> stringResource(R.string.photo_video)
                "dj" -> stringResource(R.string.dj_sound)
                "marriage_halls" -> stringResource(R.string.marriage_halls)
                else -> stringResource(R.string.family_functions)
            },
            subtitle = "${state.items.size} ${stringResource(R.string.available)}",
            onBack = { selectedCategory = null },
            onAdd = { onNavigateToEdit(selectedCategory!!, null) },
            searchQuery = state.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            villages = villages,
            selectedVillageId = selectedVillageId,
            onVillageChange = onVillageChange
        ) { padding ->
            HubList(state.items.filter { 
                it.name.en.contains(state.searchQuery, true) || 
                it.name.hi.contains(state.searchQuery, true) 
            }, state.isLoading, state.error, contentPadding = padding) { item ->
                DetailCard(
                    title = item.name.text(),
                    location = item.address.text(),
                    details = listOf(
                        Icons.Rounded.Star to stringResource(R.string.services_label, item.services.text()),
                        Icons.Rounded.Payments to stringResource(R.string.starting_price_label, item.startingPrice.text())
                    ),
                    onEdit = { onNavigateToEdit(selectedCategory!!, item) },
                    onDelete = { viewModel.delete(item) }
                )
            }
        }
    }
}

@Composable
fun FamilyFunctionMainScreen(onBack: () -> Unit, onCategoryClick: (String) -> Unit) {
    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars,
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                windowInsets = TopAppBarDefaults.windowInsets,
                title = { Text(stringResource(R.string.family_functions), style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, stringResource(R.string.back)) } })
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
            item { CategoryTile(stringResource(R.string.tent_decor), Icons.Rounded.Store) { onCategoryClick("tent") } }
            item { CategoryTile(stringResource(R.string.catering_halwai), Icons.Rounded.Restaurant) { onCategoryClick("catering") } }
            item { CategoryTile(stringResource(R.string.photo_video), Icons.Rounded.PhotoCamera) { onCategoryClick("photo") } }
            item { CategoryTile(stringResource(R.string.dj_sound), Icons.Rounded.Audiotrack) { onCategoryClick("dj") } }
            item { CategoryTile(stringResource(R.string.marriage_halls), Icons.Rounded.LocationCity) { onCategoryClick("marriage_halls") } }
        }
    }
}

@Composable
fun NewsScreen(
    viewModel: NewsViewModel,
    onBack: () -> Unit,
    villages: List<Village> = emptyList(),
    selectedVillageId: String = "",
    onVillageChange: (String) -> Unit = {},
    onNavigateToEdit: (News?) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var selectedNews by remember { mutableStateOf<News?>(null) }

    if (selectedNews != null) {
        NewsDetailScreen(news = selectedNews!!, onBack = { selectedNews = null })
    } else {
        HubScaffold(
            title = stringResource(R.string.local_news),
            subtitle = stringResource(R.string.updates_label, state.items.size),
            onBack = onBack,
            onAdd = { onNavigateToEdit(null) },
            searchQuery = state.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            villages = villages,
            selectedVillageId = selectedVillageId,
            onVillageChange = onVillageChange
        ) { padding ->
            val filteredItems = state.items.filter { 
                it.title.en.contains(state.searchQuery, true) || it.title.hi.contains(state.searchQuery, true) 
            }
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
                    item { Text(stringResource(R.string.breaking_news), style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) }
                    items(breakingNews) { item ->
                        NewsCard(item, onClick = { selectedNews = item }, onEdit = { onNavigateToEdit(item) }, onDelete = { viewModel.delete(item) })
                    }
                }

                if (notices.isNotEmpty()) {
                    item { Text(stringResource(R.string.notices), style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) }
                    items(notices) { item ->
                        NoticeCard(item, onClick = { selectedNews = item }, onEdit = { onNavigateToEdit(item) }, onDelete = { viewModel.delete(item) })
                    }
                }
            }
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
                Text(news.title.text(), style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), maxLines = 2)
                Text(news.description.text(), style = MaterialTheme.typography.bodySmall, color = Color.Gray, maxLines = 2)
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                    Icon(Icons.Rounded.AccessTime, null, modifier = Modifier.size(14.dp), tint = PrimaryTeal)
                    Spacer(Modifier.width(4.dp))
                    Text(stringResource(R.string.ago_label, "2 Hr"), style = MaterialTheme.typography.labelSmall, color = Color.Gray)
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
                Text(news.title.text(), style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), maxLines = 1)
                Text(news.description.text(), style = MaterialTheme.typography.bodySmall, color = Color.Gray, maxLines = 2)
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
                title = { Text(stringResource(R.string.news_details), style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, stringResource(R.string.back)) } }
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
            Text(news.title.text(), style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 12.dp)) {
                Icon(Icons.Rounded.AccessTime, null, modifier = Modifier.size(16.dp), tint = PrimaryTeal)
                Spacer(Modifier.width(4.dp))
                Text(stringResource(R.string.published_label, stringResource(R.string.ago_label, "2 hours")), style = MaterialTheme.typography.labelMedium, color = Color.Gray)
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
            Text(news.description.text(), style = MaterialTheme.typography.bodyLarge, color = Color.DarkGray)
        }
    }
}

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel,
    onBack: () -> Unit,
    villages: List<Village> = emptyList(),
    selectedVillageId: String = "",
    onVillageChange: (String) -> Unit = {},
    onNavigateToEdit: (AppNotification?) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var selectedNotification by remember { mutableStateOf<AppNotification?>(null) }

    if (selectedNotification != null) {
        NotificationDetailScreen(notification = selectedNotification!!, onBack = { selectedNotification = null })
    } else {
        HubScaffold(
            title = stringResource(R.string.notifications),
            subtitle = stringResource(R.string.total_label, state.items.size),
            onBack = onBack,
            onAdd = { onNavigateToEdit(null) },
            searchQuery = state.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
            villages = villages,
            selectedVillageId = selectedVillageId,
            onVillageChange = onVillageChange
        ) { padding ->
            val filteredItems = state.items.filter { 
                it.title.en.contains(state.searchQuery, true) || 
                it.title.hi.contains(state.searchQuery, true) 
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
                if (filteredItems.isNotEmpty()) {
                    item { Text(stringResource(R.string.today), style = MaterialTheme.typography.labelLarge, color = Color.Gray) }
                    items(filteredItems) { item ->
                        NotificationCard(item, onClick = { selectedNotification = item }, onEdit = { onNavigateToEdit(item) }, onDelete = { viewModel.delete(item) })
                    }
                }
            }
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
                    Text(notification.title.text(), style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), maxLines = 1, modifier = Modifier.weight(1f))
                    Text(stringResource(R.string.ago_label, "2 Hr"), style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
                Text(notification.message.text(), style = MaterialTheme.typography.bodySmall, color = Color.Gray, maxLines = 2)
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
                title = { Text(stringResource(R.string.notification_details), style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, stringResource(R.string.back)) } }
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
            Text(notification.title.text(), style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 12.dp)) {
                Icon(Icons.Rounded.AccessTime, null, modifier = Modifier.size(16.dp), tint = PrimaryTeal)
                Spacer(Modifier.width(4.dp))
                Text(stringResource(R.string.published_label, stringResource(R.string.today)), style = MaterialTheme.typography.labelMedium, color = Color.Gray)
            }
            Spacer(Modifier.height(16.dp))
            Text(notification.message.text(), style = MaterialTheme.typography.bodyLarge, color = Color.DarkGray)
        }
    }
}

@Composable
fun BannerScreen(
    viewModel: BannerViewModel,
    onBack: () -> Unit,
    villages: List<Village> = emptyList(),
    selectedVillageId: String = "",
    onVillageChange: (String) -> Unit = {},
    onNavigateToEdit: (Banner?) -> Unit
) {
    val state by viewModel.state.collectAsState()
    
    HubScaffold(
        title = stringResource(R.string.village_banners), 
        subtitle = stringResource(R.string.banners_label, state.items.size),
        onBack = onBack, 
        onAdd = { onNavigateToEdit(null) }, 
        searchQuery = state.searchQuery, 
        onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
        villages = villages,
        selectedVillageId = selectedVillageId,
        onVillageChange = onVillageChange
    ) { padding ->
        val filteredItems = state.items.filter { 
            it.title.en.contains(state.searchQuery, true) || 
            it.title.hi.contains(state.searchQuery, true) || 
            it.imageUrl.contains(state.searchQuery, true) 
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
                                text = item.title.text(),
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
                            IconButton(onClick = { onNavigateToEdit(item) }) {
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
    }
}
// Cleaned version
