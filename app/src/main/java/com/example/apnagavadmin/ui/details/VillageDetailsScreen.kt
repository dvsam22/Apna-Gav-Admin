package com.example.apnagavadmin.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.apnagavadmin.ui.hubs.PrimaryTeal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VillageDetailsScreen(
    villageId: String,
    onBack: () -> Unit,
    onNavigateToHub: (String) -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars,
        topBar = {
            TopAppBar(
                windowInsets = TopAppBarDefaults.windowInsets,
                title = { Text("Village Management", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                top = padding.calculateTopPadding() + 16.dp,
                end = 16.dp,
                bottom = padding.calculateBottomPadding() + 16.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Admin Dashboard", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold), color = PrimaryTeal)
                    Text("Village ID: $villageId", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            val services = listOf(
                ServiceItem("Labour Board", Icons.Rounded.Person, "labour"),
                ServiceItem("Construction", Icons.Rounded.Build, "construction"),
                ServiceItem("Transport", Icons.Rounded.ShoppingCart, "transport"),
                ServiceItem("Mandi Prices", Icons.Rounded.Info, "mandi"),
                ServiceItem("Health Hub", Icons.Rounded.Favorite, "health"),
                ServiceItem("Village News", Icons.Rounded.Notifications, "news"),
                ServiceItem("Banners", Icons.Rounded.Star, "banners")
            )

            items(services) { service ->
                Box(modifier = Modifier.padding(
                    start = if (services.indexOf(service) % 2 == 0) 16.dp else 0.dp,
                    end = if (services.indexOf(service) % 2 != 0) 16.dp else 0.dp
                )) {
                    ServiceGridCard(service.name, service.icon) { onNavigateToHub(service.route) }
                }
            }
            
            item(span = { GridItemSpan(2) }) { Spacer(Modifier.height(16.dp)) }
        }
    }
}

data class ServiceItem(val name: String, val icon: ImageVector, val route: String)

@Composable
fun ServiceGridCard(name: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(140.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.size(56.dp),
                shape = MaterialTheme.shapes.medium,
                color = PrimaryTeal.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, modifier = Modifier.size(32.dp), tint = PrimaryTeal)
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(name, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
        }
    }
}
