package com.example.apnagavadmin.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.example.apnagavadmin.R
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
                title = { Text(stringResource(R.string.village_management), style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ) { padding ->
            val services = listOf(
                ServiceItem(stringResource(R.string.labour_board), Icons.Rounded.Person, "labour"),
                ServiceItem(stringResource(R.string.construction_hub), Icons.Rounded.Build, "construction"),
                ServiceItem(stringResource(R.string.transport_rentals), Icons.Rounded.ShoppingCart, "transport"),
                ServiceItem(stringResource(R.string.mandi_hub), Icons.Rounded.Info, "mandi"),
                ServiceItem(stringResource(R.string.health_emergency), Icons.Rounded.Favorite, "health"),
                ServiceItem(stringResource(R.string.family_functions), Icons.Rounded.Celebration, "family"),
                ServiceItem(stringResource(R.string.local_news), Icons.Rounded.Newspaper, "news"),
                ServiceItem(stringResource(R.string.village_banners), Icons.Rounded.Star, "banners"),
                ServiceItem(stringResource(R.string.notices), Icons.Rounded.NotificationsActive, "notifications")
            )

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
                    Text(stringResource(R.string.admin_dashboard), style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold), color = PrimaryTeal)
                    Text(stringResource(R.string.village_id_label, villageId), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

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
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Subtle background decoration
            Surface(
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 20.dp, y = (-20).dp),
                shape = CircleShape,
                color = PrimaryTeal.copy(alpha = 0.05f)
            ) {}

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    modifier = Modifier.size(44.dp),
                    shape = MaterialTheme.shapes.medium,
                    color = PrimaryTeal.copy(alpha = 0.12f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = PrimaryTeal
                        )
                    }
                }
                
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 20.sp,
                        fontSize = 15.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2
                )
            }
        }
    }
}
