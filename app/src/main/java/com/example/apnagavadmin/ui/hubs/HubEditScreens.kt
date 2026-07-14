package com.example.apnagavadmin.ui.hubs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Message
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.apnagavadmin.R
import com.example.apnagavadmin.data.model.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScaffold(
    title: String,
    onBack: () -> Unit,
    onSave: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                actions = {
                    Button(
                        onClick = onSave,
                        modifier = Modifier.padding(end = 8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Icon(Icons.Rounded.Save, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(R.string.save))
                    }
                }
            )
        },
        content = content
    )
}

@Composable
fun LabourEditScreen(
    provider: LabourProvider? = null,
    onBack: () -> Unit,
    onSave: (LabourProvider) -> Unit
) {
    var name by remember { mutableStateOf(provider?.name ?: LocalizedString()) }
    var location by remember { mutableStateOf(provider?.location ?: LocalizedString()) }
    var contact by remember { mutableStateOf(provider?.contact ?: "") }
    var skills by remember { mutableStateOf(provider?.skills ?: LocalizedString()) }
    var charges by remember { mutableStateOf(provider?.charges ?: LocalizedString()) }

    EditScaffold(
        title = if (provider == null) stringResource(R.string.add_labourer) else stringResource(R.string.edit_labourer),
        onBack = onBack,
        onSave = {
            onSave(provider?.copy(name = name, location = location, contact = contact, skills = skills, charges = charges) 
                ?: LabourProvider(name = name, location = location, contact = contact, skills = skills, charges = charges))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("BASIC INFORMATION", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
            LocalizedTextField(value = name, onValueChange = { name = it }, label = stringResource(R.string.name), icon = Icons.Rounded.Person)
            
            OutlinedTextField(
                value = contact, 
                onValueChange = { contact = it }, 
                label = { Text(stringResource(R.string.contact_number)) }, 
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = { Icon(Icons.Rounded.Call, null, tint = PrimaryTeal) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, unfocusedContainerColor = Color.White)
            )

            Text("SERVICE DETAILS", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
            LocalizedTextField(value = location, onValueChange = { location = it }, label = stringResource(R.string.location), icon = Icons.Rounded.LocationOn)
            LocalizedTextField(value = skills, onValueChange = { skills = it }, label = stringResource(R.string.skills), icon = Icons.Rounded.Build)
            LocalizedTextField(value = charges, onValueChange = { charges = it }, label = stringResource(R.string.charges), icon = Icons.Rounded.Payments)
        }
    }
}

@Composable
fun ConstructionEditScreen(
    hub: ConstructionHub? = null,
    onBack: () -> Unit,
    onSave: (ConstructionHub) -> Unit
) {
    var shopName by remember { mutableStateOf(hub?.shopName ?: LocalizedString()) }
    var address by remember { mutableStateOf(hub?.address ?: LocalizedString()) }
    var contact by remember { mutableStateOf(hub?.contact ?: "") }
    val products = remember { mutableStateListOf<ConstructionProduct>().apply { hub?.products?.let { addAll(it) } } }
    
    var pName by remember { mutableStateOf(LocalizedString()) }
    var pPrice by remember { mutableStateOf("") }
    var pUnit by remember { mutableStateOf(LocalizedString()) }

    EditScaffold(
        title = if (hub == null) stringResource(R.string.add_provider) else stringResource(R.string.edit_provider),
        onBack = onBack,
        onSave = {
            onSave(hub?.copy(shopName = shopName, address = address, contact = contact, products = products.toList()) 
                ?: ConstructionHub(shopName = shopName, address = address, contact = contact, products = products.toList()))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("SHOP INFORMATION", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
            LocalizedTextField(shopName, { shopName = it }, stringResource(R.string.name), icon = Icons.Rounded.Store)
            LocalizedTextField(address, { address = it }, stringResource(R.string.address), icon = Icons.Rounded.LocationOn)
            
            OutlinedTextField(
                value = contact, 
                onValueChange = { contact = it }, 
                label = { Text(stringResource(R.string.contact)) }, 
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = { Icon(Icons.Rounded.Call, null, tint = PrimaryTeal) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, unfocusedContainerColor = Color.White)
            )
            
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text("PRODUCTS & PRICING", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PrimaryTeal.copy(alpha = 0.05f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryTeal.copy(alpha = 0.2f))
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    LocalizedTextField(pName, { pName = it }, stringResource(R.string.name), icon = Icons.Rounded.Build)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            pPrice, { pPrice = it }, 
                            label = { Text(stringResource(R.string.price)) }, 
                            modifier = Modifier.weight(1f),
                            shape = MaterialTheme.shapes.medium,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, unfocusedContainerColor = Color.White)
                        )
                        LocalizedTextField(pUnit, { pUnit = it }, stringResource(R.string.unit), modifier = Modifier.weight(1.2f))
                    }
                    Button(
                        onClick = { 
                            if (pName.en.isNotEmpty()) { 
                                products.add(ConstructionProduct(pName, pPrice, pUnit))
                                pName = LocalizedString(); pPrice = ""; pUnit = LocalizedString() 
                            } 
                        }, 
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium
                    ) { 
                        Icon(Icons.Rounded.Add, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(R.string.add)) 
                    }
                }
            }

            products.forEach { p ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
                ) {
                    ListItem(
                        headlineContent = { Text(p.name.get(), fontWeight = FontWeight.Bold) },
                        supportingContent = { Text("${p.price}/${p.unit.get()}", color = PrimaryTeal) },
                        trailingContent = {
                            IconButton(onClick = { products.remove(p) }) {
                                Icon(Icons.Rounded.Delete, null, tint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f))
                            }
                        },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                    )
                }
            }
        }
    }
}

@Composable
fun TransportEditScreen(
    hub: TransportHub? = null,
    onBack: () -> Unit,
    onSave: (TransportHub) -> Unit
) {
    var name by remember { mutableStateOf(hub?.name ?: LocalizedString()) }
    var loc by remember { mutableStateOf(hub?.location ?: LocalizedString()) }
    var con by remember { mutableStateOf(hub?.contact ?: "") }
    var type by remember { mutableStateOf(hub?.vehicleType ?: LocalizedString()) }

    EditScaffold(
        title = if (hub == null) stringResource(R.string.add_provider) else stringResource(R.string.edit_provider),
        onBack = onBack,
        onSave = {
            onSave(hub?.copy(name = name, location = loc, contact = con, vehicleType = type) 
                ?: TransportHub(name = name, location = loc, contact = con, vehicleType = type))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("VEHICLE & OWNER", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
            LocalizedTextField(name, { name = it }, stringResource(R.string.name), icon = Icons.Rounded.Person)
            
            OutlinedTextField(
                value = con, 
                onValueChange = { con = it }, 
                label = { Text(stringResource(R.string.contact)) }, 
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = { Icon(Icons.Rounded.Call, null, tint = PrimaryTeal) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, unfocusedContainerColor = Color.White)
            )

            Text("LOCATION & TYPE", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
            LocalizedTextField(loc, { loc = it }, stringResource(R.string.location), icon = Icons.Rounded.LocationOn)
            LocalizedTextField(type, { type = it }, stringResource(R.string.type), icon = Icons.Rounded.DirectionsCar)
        }
    }
}

@Composable
fun MandiEditScreen(
    priceItem: MandiPrice? = null,
    selectedCategory: String,
    onBack: () -> Unit,
    onSave: (MandiPrice) -> Unit
) {
    var buyerName by remember { mutableStateOf(priceItem?.buyerName ?: LocalizedString()) }
    var cropName by remember { mutableStateOf(priceItem?.cropName ?: LocalizedString()) }
    var address by remember { mutableStateOf(priceItem?.address ?: LocalizedString()) }
    var contact by remember { mutableStateOf(priceItem?.contact ?: "") }
    var price by remember { mutableStateOf(priceItem?.price?.toString() ?: "") }
    var unit by remember { mutableStateOf(priceItem?.unit ?: LocalizedString()) }

    EditScaffold(
        title = if (priceItem == null) stringResource(R.string.add) else stringResource(R.string.edit),
        onBack = onBack,
        onSave = {
            onSave(priceItem?.copy(
                buyerName = buyerName, cropName = cropName, address = address, contact = contact,
                price = price.toDoubleOrNull() ?: 0.0, unit = unit
            ) ?: MandiPrice(
                buyerName = buyerName, cropName = cropName, address = address, contact = contact,
                price = price.toDoubleOrNull() ?: 0.0, unit = unit
            ))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if (selectedCategory == "buyers") {
                Text("BUYER INFORMATION", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
                LocalizedTextField(buyerName, { buyerName = it }, stringResource(R.string.name), icon = Icons.Rounded.Person)
                LocalizedTextField(cropName, { cropName = it }, stringResource(R.string.crop_prices), icon = Icons.Rounded.Agriculture)
                
                Text("CONTACT & ADDRESS", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
                OutlinedTextField(
                    value = contact, 
                    onValueChange = { contact = it }, 
                    label = { Text(stringResource(R.string.contact)) }, 
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    leadingIcon = { Icon(Icons.Rounded.Call, null, tint = PrimaryTeal) },
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, unfocusedContainerColor = Color.White)
                )
                LocalizedTextField(address, { address = it }, stringResource(R.string.address), icon = Icons.Rounded.LocationOn)
            } else {
                Text("ITEM & PRICE", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
                LocalizedTextField(cropName, { cropName = it }, stringResource(R.string.name), icon = Icons.Rounded.ShoppingCart)
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = price, 
                        onValueChange = { price = it }, 
                        label = { Text(stringResource(R.string.price)) }, 
                        modifier = Modifier.weight(1f),
                        shape = MaterialTheme.shapes.medium,
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, unfocusedContainerColor = Color.White)
                    )
                    LocalizedTextField(unit, { unit = it }, stringResource(R.string.unit), modifier = Modifier.weight(1.2f))
                }
            }
        }
    }
}

@Composable
fun HealthEditScreen(
    hub: HealthHub? = null,
    selectedCategory: String,
    onBack: () -> Unit,
    onSave: (HealthHub) -> Unit
) {
    var name by remember { mutableStateOf(hub?.name ?: LocalizedString()) }
    var address by remember { mutableStateOf(hub?.address ?: LocalizedString()) }
    var contact by remember { mutableStateOf(hub?.contact ?: "") }
    var specialisation by remember { mutableStateOf(hub?.specialisation ?: LocalizedString()) }
    var availability by remember { mutableStateOf(hub?.availability ?: LocalizedString()) }
    var type by remember { mutableStateOf(hub?.type ?: LocalizedString()) }
    var facilities by remember { mutableStateOf(hub?.facilities ?: LocalizedString()) }
    var services by remember { mutableStateOf(hub?.services ?: LocalizedString()) }

    EditScaffold(
        title = if (hub == null) stringResource(R.string.add_provider) else stringResource(R.string.edit_provider),
        onBack = onBack,
        onSave = {
            onSave(hub?.copy(
                name = name, address = address, contact = contact, 
                specialisation = specialisation, availability = availability,
                type = type, facilities = facilities, services = services
            ) ?: HealthHub(
                name = name, address = address, contact = contact, 
                specialisation = specialisation, availability = availability,
                type = type, facilities = facilities, services = services
            ))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("BASIC INFORMATION", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
            LocalizedTextField(name, { name = it }, stringResource(R.string.name), icon = Icons.Rounded.Person)
            LocalizedTextField(address, { address = it }, stringResource(R.string.address), icon = Icons.Rounded.LocationOn)
            
            OutlinedTextField(
                value = contact, 
                onValueChange = { contact = it }, 
                label = { Text(stringResource(R.string.contact)) }, 
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = { Icon(Icons.Rounded.Call, null, tint = PrimaryTeal) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, unfocusedContainerColor = Color.White)
            )
            
            Text("SERVICE DETAILS", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
            when (selectedCategory) {
                "doctors" -> {
                    LocalizedTextField(specialisation, { specialisation = it }, stringResource(R.string.specialisation), icon = Icons.Rounded.MedicalServices)
                    LocalizedTextField(availability, { availability = it }, stringResource(R.string.availability), icon = Icons.Rounded.AccessTime)
                }
                "hospitals" -> {
                    LocalizedTextField(type, { type = it }, stringResource(R.string.type), icon = Icons.Rounded.LocalHospital)
                    LocalizedTextField(facilities, { facilities = it }, stringResource(R.string.facilities), icon = Icons.Rounded.Bed)
                    LocalizedTextField(availability, { availability = it }, stringResource(R.string.open_hours), icon = Icons.Rounded.AccessTime)
                }
                "pharmacy" -> {
                    LocalizedTextField(services, { services = it }, stringResource(R.string.services), icon = Icons.Rounded.Medication)
                    LocalizedTextField(availability, { availability = it }, stringResource(R.string.open_hours), icon = Icons.Rounded.AccessTime)
                }
            }
        }
    }
}

@Composable
fun NewsEditScreen(
    newsItem: News? = null,
    onBack: () -> Unit,
    onSave: (News) -> Unit
) {
    var title by remember { mutableStateOf(newsItem?.title ?: LocalizedString()) }
    var description by remember { mutableStateOf(newsItem?.description ?: LocalizedString()) }
    var image by remember { mutableStateOf(newsItem?.image ?: "") }

    EditScaffold(
        title = if (newsItem == null) stringResource(R.string.add) else stringResource(R.string.edit),
        onBack = onBack,
        onSave = {
            onSave(newsItem?.copy(title = title, description = description, image = image, type = "news") 
                ?: News(title = title, description = description, image = image, type = "news"))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("CONTENT", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
            LocalizedTextField(title, { title = it }, stringResource(R.string.headline), icon = Icons.Rounded.Newspaper)
            LocalizedTextField(description, { description = it }, stringResource(R.string.description), singleLine = false, icon = Icons.Rounded.Description)
            
            Text("MEDIA", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
            OutlinedTextField(
                value = image, 
                onValueChange = { image = it }, 
                label = { Text(stringResource(R.string.image_url)) }, 
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = { Icon(Icons.Rounded.Image, null, tint = PrimaryTeal) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, unfocusedContainerColor = Color.White)
            )
        }
    }
}

@Composable
fun BannerEditScreen(
    banner: Banner? = null,
    onBack: () -> Unit,
    onSave: (Banner) -> Unit
) {
    var title by remember { mutableStateOf(banner?.title ?: LocalizedString()) }
    var discountText by remember { mutableStateOf(banner?.discountText ?: "") }
    var imageUrl by remember { mutableStateOf(banner?.imageUrl ?: "") }
    var link by remember { mutableStateOf(banner?.link ?: "") }

    EditScaffold(
        title = if (banner == null) stringResource(R.string.add) else stringResource(R.string.edit),
        onBack = onBack,
        onSave = {
            onSave(banner?.copy(title = title, discountText = discountText, imageUrl = imageUrl, link = link) 
                ?: Banner(title = title, discountText = discountText, imageUrl = imageUrl, link = link))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("BANNER DETAILS", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
            LocalizedTextField(title, { title = it }, stringResource(R.string.banner_title), icon = Icons.Rounded.Star)
            
            OutlinedTextField(
                value = discountText, 
                onValueChange = { discountText = it }, 
                label = { Text(stringResource(R.string.discount)) }, 
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = { Icon(Icons.Rounded.Percent, null, tint = PrimaryTeal) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, unfocusedContainerColor = Color.White)
            )

            Text("LINKS & MEDIA", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
            OutlinedTextField(
                value = imageUrl, 
                onValueChange = { imageUrl = it }, 
                label = { Text(stringResource(R.string.image_url)) }, 
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = { Icon(Icons.Rounded.Image, null, tint = PrimaryTeal) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, unfocusedContainerColor = Color.White)
            )
            OutlinedTextField(
                value = link, 
                onValueChange = { link = it }, 
                label = { Text(stringResource(R.string.link)) }, 
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = { Icon(Icons.Rounded.Link, null, tint = PrimaryTeal) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, unfocusedContainerColor = Color.White)
            )
        }
    }
}

@Composable
fun NotificationEditScreen(
    notification: AppNotification? = null,
    onBack: () -> Unit,
    onSave: (AppNotification) -> Unit
) {
    var title by remember { mutableStateOf(notification?.title ?: LocalizedString()) }
    var message by remember { mutableStateOf(notification?.message ?: LocalizedString()) }

    EditScaffold(
        title = if (notification == null) stringResource(R.string.add) else stringResource(R.string.edit),
        onBack = onBack,
        onSave = {
            onSave(notification?.copy(title = title, message = message, type = "notification") 
                ?: AppNotification(title = title, message = message, type = "notification"))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("NOTIFICATION CONTENT", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
            LocalizedTextField(title, { title = it }, stringResource(R.string.headline), icon = Icons.Rounded.NotificationsActive)
            LocalizedTextField(message, { message = it }, stringResource(R.string.description), singleLine = false, icon = Icons.AutoMirrored.Rounded.Message)
        }
    }
}

@Composable
fun FamilyFunctionEditScreen(
    hub: FamilyFunctionHub? = null,
    onBack: () -> Unit,
    onSave: (FamilyFunctionHub) -> Unit
) {
    var name by remember { mutableStateOf(hub?.name ?: LocalizedString()) }
    var address by remember { mutableStateOf(hub?.address ?: LocalizedString()) }
    var contact by remember { mutableStateOf(hub?.contact ?: "") }
    var services by remember { mutableStateOf(hub?.services ?: LocalizedString()) }
    var startingPrice by remember { mutableStateOf(hub?.startingPrice ?: LocalizedString()) }

    EditScaffold(
        title = if (hub == null) stringResource(R.string.add_provider) else stringResource(R.string.edit_provider),
        onBack = onBack,
        onSave = {
            onSave(hub?.copy(name = name, address = address, contact = contact, services = services, startingPrice = startingPrice) 
                ?: FamilyFunctionHub(name = name, address = address, contact = contact, services = services, startingPrice = startingPrice))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("VENDOR INFORMATION", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
            LocalizedTextField(name, { name = it }, stringResource(R.string.name), icon = Icons.Rounded.Store)
            
            OutlinedTextField(
                value = contact, 
                onValueChange = { contact = it }, 
                label = { Text(stringResource(R.string.contact)) }, 
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                leadingIcon = { Icon(Icons.Rounded.Call, null, tint = PrimaryTeal) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, unfocusedContainerColor = Color.White)
            )

            Text("LOCATION & PRICE", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
            LocalizedTextField(address, { address = it }, stringResource(R.string.address), icon = Icons.Rounded.LocationOn)
            LocalizedTextField(services, { services = it }, stringResource(R.string.services), icon = Icons.Rounded.Star)
            LocalizedTextField(startingPrice, { startingPrice = it }, stringResource(R.string.starting_price), icon = Icons.Rounded.Payments)
        }
    }
}

@Composable
fun VillageEditScreen(
    village: Village? = null,
    onBack: () -> Unit,
    onSave: (Village) -> Unit
) {
    var name by remember { mutableStateOf(village?.villageName ?: LocalizedString()) }
    var sarpanchName by remember { mutableStateOf(village?.sarpanchName ?: LocalizedString()) }
    var district by remember { mutableStateOf(village?.district ?: LocalizedString()) }
    var state by remember { mutableStateOf(village?.state ?: LocalizedString()) }
    var pincode by remember { mutableStateOf(village?.pincode ?: "") }
    var lat by remember { mutableStateOf(village?.lat?.toString() ?: "") }
    var lng by remember { mutableStateOf(village?.lng?.toString() ?: "") }

    EditScaffold(
        title = if (village == null) stringResource(R.string.add_village) else stringResource(R.string.edit_village),
        onBack = onBack,
        onSave = {
            onSave(village?.copy(
                villageName = name, sarpanchName = sarpanchName, district = district, state = state, 
                pincode = pincode, lat = lat.toDoubleOrNull() ?: 0.0, lng = lng.toDoubleOrNull() ?: 0.0
            ) ?: Village(
                villageName = name, sarpanchName = sarpanchName, district = district, state = state, 
                pincode = pincode, lat = lat.toDoubleOrNull() ?: 0.0, lng = lng.toDoubleOrNull() ?: 0.0
            ))
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("VILLAGE IDENTITY", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
            LocalizedTextField(value = name, onValueChange = { name = it }, label = stringResource(R.string.village_name), icon = Icons.Rounded.Home)
            LocalizedTextField(value = sarpanchName, onValueChange = { sarpanchName = it }, label = stringResource(R.string.sarpanch_name), icon = Icons.Rounded.Person)
            
            Text("LOCATION DETAILS", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = Color.Gray)
            LocalizedTextField(value = district, onValueChange = { district = it }, label = stringResource(R.string.district), icon = Icons.Rounded.LocationCity)
            LocalizedTextField(value = state, onValueChange = { state = it }, label = stringResource(R.string.state), icon = Icons.Rounded.Public)

            OutlinedTextField(
                value = pincode, 
                onValueChange = { pincode = it }, 
                label = { Text(stringResource(R.string.pincode)) }, 
                modifier = Modifier.fillMaxWidth(), 
                shape = MaterialTheme.shapes.medium,
                leadingIcon = { Icon(Icons.Rounded.Pin, null, tint = PrimaryTeal) },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, unfocusedContainerColor = Color.White),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = lat, 
                    onValueChange = { lat = it }, 
                    label = { Text(stringResource(R.string.latitude)) }, 
                    modifier = Modifier.weight(1f), 
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, unfocusedContainerColor = Color.White),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal)
                )
                OutlinedTextField(
                    value = lng, 
                    onValueChange = { lng = it }, 
                    label = { Text(stringResource(R.string.longitude)) }, 
                    modifier = Modifier.weight(1f), 
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryTeal, unfocusedContainerColor = Color.White),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
