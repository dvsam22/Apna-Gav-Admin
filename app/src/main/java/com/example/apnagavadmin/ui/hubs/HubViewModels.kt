package com.example.apnagavadmin.ui.hubs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apnagavadmin.data.model.*
import com.example.apnagavadmin.data.repository.*
import com.example.apnagavadmin.util.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class HubState<T>(
    val items: List<T> = emptyList(),
    val categories: List<LabourCategory> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)

class LabourViewModel(
    private val repository: LabourRepository = LabourRepository(),
    private val villageRepository: VillageRepository = VillageRepository(),
    private val villageId: String
) : ViewModel() {
    private val _state = MutableStateFlow(HubState<LabourProvider>())
    val state = _state.asStateFlow()

    private var selectedCategoryId: String = ""

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            repository.getCategories().collect { res ->
                if (res is Resource.Success) {
                    val defaultCategories = listOf(
                        LabourCategory(id = "rajmistri", name = LocalizedString(en = "Rajmistri", hi = "राजमिस्त्री")),
                        LabourCategory(id = "plumber", name = LocalizedString(en = "Plumber", hi = "प्लंबर")),
                        LabourCategory(id = "electrician", name = LocalizedString(en = "Electrician", hi = "इलेक्ट्रिशियन")),
                        LabourCategory(id = "carpenter", name = LocalizedString(en = "Carpenter", hi = "बढ़ई")),
                        LabourCategory(id = "tailor", name = LocalizedString(en = "Tailor", hi = "दर्जी")),
                        LabourCategory(id = "labour", name = LocalizedString(en = "Labour", hi = "मजदूर"))
                    )
                    val categories = if (res.data.isNullOrEmpty()) defaultCategories else res.data
                    _state.update { it.copy(categories = categories) }
                }
            }
        }
    }

    fun selectCategory(categoryId: String) {
        selectedCategoryId = categoryId
        loadProviders()
    }

    private fun loadProviders() {
        viewModelScope.launch {
            repository.getProviders(villageId, selectedCategoryId).collect { res ->
                when (res) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> {
                        val items = res.data ?: emptyList()
                        val finalItems = if (villageId == "all") {
                            items.distinctBy { "${it.name}_${it.contact}_${it.categoryId}" }
                        } else items
                        _state.update { it.copy(isLoading = false, items = finalItems) }
                    }
                    is Resource.Error -> _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) { _state.update { it.copy(searchQuery = query) } }

    fun saveProvider(provider: LabourProvider) { 
        viewModelScope.launch { 
            if (villageId == "all" && provider.id.isEmpty()) {
                villageRepository.getVillages().filter { it is Resource.Success }.first().data?.forEach { v ->
                    repository.saveProvider(v.id, provider.copy(villageId = v.id, categoryId = selectedCategoryId))
                }
            } else {
                val vId = if (villageId == "all") provider.villageId else villageId
                repository.saveProvider(vId, provider.copy(villageId = vId, categoryId = selectedCategoryId)) 
            }
        } 
    }

    fun deleteProvider(provider: LabourProvider) { 
        viewModelScope.launch { 
            if (villageId == "all") {
                repository.getProviders("all", selectedCategoryId).filter { it is Resource.Success }.first().data?.forEach { p ->
                    if (p.name == provider.name && p.contact == provider.contact) {
                        repository.deleteProvider(p.villageId, p.id)
                    }
                }
            } else {
                repository.deleteProvider(villageId, provider.id) 
            }
        } 
    }
}

class ConstructionViewModel(
    private val repository: ConstructionRepository = ConstructionRepository(),
    private val villageRepository: VillageRepository = VillageRepository(),
    private val villageId: String
) : ViewModel() {
    private val _state = MutableStateFlow(HubState<ConstructionHub>())
    val state = _state.asStateFlow()
    
    private var selectedCategoryId: String? = null

    fun selectCategory(categoryId: String?) {
        selectedCategoryId = categoryId
        load()
    }

    private fun load() = viewModelScope.launch {
        repository.getHubs(villageId, selectedCategoryId).collect { res ->
            when (res) {
                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Success -> {
                    val items = res.data ?: emptyList()
                    val finalItems = if (villageId == "all") {
                        items.distinctBy { "${it.shopName}_${it.contact}_${it.categoryId}" }
                    } else items
                    _state.update { it.copy(isLoading = false, items = finalItems) }
                }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = res.message) }
            }
        }
    }
    fun onSearchQueryChange(q: String) { _state.update { it.copy(searchQuery = q) } }
    
    fun save(item: ConstructionHub) { 
        viewModelScope.launch { 
            if (villageId == "all" && item.id.isEmpty()) {
                villageRepository.getVillages().filter { it is Resource.Success }.first().data?.forEach { v ->
                    repository.saveHub(v.id, item.copy(villageId = v.id, categoryId = selectedCategoryId ?: "bricks"))
                }
            } else {
                val vId = if (villageId == "all") item.villageId else villageId
                repository.saveHub(vId, item.copy(villageId = vId, categoryId = selectedCategoryId ?: "bricks")) 
            }
        } 
    }
    
    fun delete(item: ConstructionHub) { 
        viewModelScope.launch { 
            if (villageId == "all") {
                repository.getHubs("all", selectedCategoryId).filter { it is Resource.Success }.first().data?.forEach { h ->
                    if (h.shopName == item.shopName && h.contact == item.contact) {
                        repository.deleteHub(h.villageId, h.id)
                    }
                }
            } else {
                repository.deleteHub(villageId, item.id) 
            }
        } 
    }
}

class TransportViewModel(
    private val repository: TransportRepository = TransportRepository(),
    private val villageRepository: VillageRepository = VillageRepository(),
    private val villageId: String
) : ViewModel() {
    private val _state = MutableStateFlow(HubState<TransportHub>())
    val state = _state.asStateFlow()
    
    private var selectedCategoryId: String? = null

    fun selectCategory(categoryId: String?) {
        selectedCategoryId = categoryId
        load()
    }

    private fun load() = viewModelScope.launch {
        repository.getHubs(villageId, selectedCategoryId).collect { res ->
            when (res) {
                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Success -> {
                    val items = res.data ?: emptyList()
                    val finalItems = if (villageId == "all") {
                        items.distinctBy { "${it.name}_${it.contact}_${it.categoryId}" }
                    } else items
                    _state.update { it.copy(isLoading = false, items = finalItems) }
                }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = res.message) }
            }
        }
    }
    fun onSearchQueryChange(q: String) { _state.update { it.copy(searchQuery = q) } }
    
    fun save(item: TransportHub) { 
        viewModelScope.launch { 
            if (villageId == "all" && item.id.isEmpty()) {
                villageRepository.getVillages().filter { it is Resource.Success }.first().data?.forEach { v ->
                    repository.saveHub(v.id, item.copy(villageId = v.id, categoryId = selectedCategoryId ?: "tractor"))
                }
            } else {
                val vId = if (villageId == "all") item.villageId else villageId
                repository.saveHub(vId, item.copy(villageId = vId, categoryId = selectedCategoryId ?: "tractor")) 
            }
        } 
    }
    
    fun delete(item: TransportHub) { 
        viewModelScope.launch { 
            if (villageId == "all") {
                repository.getHubs("all", selectedCategoryId).filter { it is Resource.Success }.first().data?.forEach { t ->
                    if (t.name == item.name && t.contact == item.contact) {
                        repository.deleteHub(t.villageId, t.id)
                    }
                }
            } else {
                repository.deleteHub(villageId, item.id) 
            }
        } 
    }
}

class MandiViewModel(
    private val repository: MandiRepository = MandiRepository(),
    private val villageRepository: VillageRepository = VillageRepository(),
    private val villageId: String
) : ViewModel() {
    private val _state = MutableStateFlow(HubState<MandiPrice>())
    val state = _state.asStateFlow()

    private var selectedCategoryId: String? = null

    fun selectCategory(categoryId: String?) {
        selectedCategoryId = categoryId
        load()
    }

    private fun load() = viewModelScope.launch {
        repository.getPrices(villageId, selectedCategoryId).collect { res ->
            when (res) {
                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Success -> {
                    val items = res.data ?: emptyList()
                    val finalItems = if (villageId == "all") {
                        items.distinctBy { "${it.cropName}_${it.price}_${it.unit}_${it.buyerName}_${it.categoryId}" }
                    } else items
                    _state.update { it.copy(isLoading = false, items = finalItems) }
                }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = res.message) }
            }
        }
    }
    fun onSearchQueryChange(q: String) { _state.update { it.copy(searchQuery = q) } }
    
    fun save(item: MandiPrice) { 
        viewModelScope.launch { 
            if (villageId == "all" && item.id.isEmpty()) {
                villageRepository.getVillages().filter { it is Resource.Success }.first().data?.forEach { v ->
                    repository.savePrice(v.id, item.copy(villageId = v.id, categoryId = selectedCategoryId ?: "prices"))
                }
            } else {
                val vId = if (villageId == "all") item.villageId else villageId
                repository.savePrice(vId, item.copy(villageId = vId, categoryId = selectedCategoryId ?: "prices")) 
            }
        } 
    }
    
    fun delete(item: MandiPrice) { 
        viewModelScope.launch { 
            if (villageId == "all") {
                repository.getPrices("all", selectedCategoryId).filter { it is Resource.Success }.first().data?.forEach { m ->
                    if (m.cropName == item.cropName && m.price == item.price && m.unit == item.unit) {
                        repository.deletePrice(m.villageId, m.id)
                    }
                }
            } else {
                repository.deletePrice(villageId, item.id) 
            }
        } 
    }
}

class HealthViewModel(
    private val repository: HealthRepository = HealthRepository(),
    private val villageRepository: VillageRepository = VillageRepository(),
    private val villageId: String
) : ViewModel() {
    private val _state = MutableStateFlow(HubState<HealthHub>())
    val state = _state.asStateFlow()

    private var selectedCategoryId: String? = null

    fun selectCategory(categoryId: String?) {
        selectedCategoryId = categoryId
        load()
    }

    private fun load() = viewModelScope.launch {
        repository.getHubs(villageId, selectedCategoryId).collect { res ->
            when (res) {
                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Success -> {
                    val items = res.data ?: emptyList()
                    val finalItems = if (villageId == "all") {
                        items.distinctBy { "${it.name}_${it.contact}_${it.categoryId}" }
                    } else items
                    _state.update { it.copy(isLoading = false, items = finalItems) }
                }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = res.message) }
            }
        }
    }
    fun onSearchQueryChange(q: String) { _state.update { it.copy(searchQuery = q) } }
    
    fun save(item: HealthHub) { 
        viewModelScope.launch { 
            if (villageId == "all" && item.id.isEmpty()) {
                villageRepository.getVillages().filter { it is Resource.Success }.first().data?.forEach { v ->
                    repository.saveHub(v.id, item.copy(villageId = v.id, categoryId = selectedCategoryId ?: "doctors"))
                }
            } else {
                val vId = if (villageId == "all") item.villageId else villageId
                repository.saveHub(vId, item.copy(villageId = vId, categoryId = selectedCategoryId ?: "doctors")) 
            }
        } 
    }
    
    fun delete(item: HealthHub) { 
        viewModelScope.launch { 
            if (villageId == "all") {
                repository.getHubs("all", selectedCategoryId).filter { it is Resource.Success }.first().data?.forEach { h ->
                    if (h.name == item.name && h.contact == item.contact) {
                        repository.deleteHub(h.villageId, h.id)
                    }
                }
            } else {
                repository.deleteHub(villageId, item.id) 
            }
        } 
    }
}

class NewsViewModel(
    private val repository: NewsBannerRepository = NewsBannerRepository(),
    private val villageRepository: VillageRepository = VillageRepository(),
    private val villageId: String
) : ViewModel() {
    private val _state = MutableStateFlow(HubState<News>())
    val state = _state.asStateFlow()
    init { load() }
    private fun load() = viewModelScope.launch {
        repository.getNews(villageId).collect { res ->
            when (res) {
                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Success -> {
                    val items = res.data ?: emptyList()
                    val finalItems = if (villageId == "all") {
                        items.distinctBy { "${it.title}_${it.description}_${it.category}" }
                    } else items
                    _state.update { it.copy(isLoading = false, items = finalItems) }
                }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = res.message) }
            }
        }
    }
    fun onSearchQueryChange(q: String) { _state.update { it.copy(searchQuery = q) } }
    
    fun save(item: News) { 
        viewModelScope.launch { 
            if (villageId == "all" && item.id.isEmpty()) {
                villageRepository.getVillages().filter { it is Resource.Success }.first().data?.forEach { v ->
                    repository.saveNews(v.id, item.copy(villageId = v.id))
                }
            } else {
                val vId = if (villageId == "all") item.villageId else villageId
                repository.saveNews(vId, item.copy(villageId = vId)) 
            }
        } 
    }
    
    fun delete(item: News) { 
        viewModelScope.launch { 
            if (villageId == "all") {
                repository.getNews("all").filter { it is Resource.Success }.first().data?.forEach { n ->
                    if (n.title == item.title && n.description == item.description) {
                        repository.deleteNews(n.villageId, n.id)
                    }
                }
            } else {
                repository.deleteNews(villageId, item.id) 
            }
        } 
    }
}

class BannerViewModel(
    private val repository: NewsBannerRepository = NewsBannerRepository(),
    private val villageRepository: VillageRepository = VillageRepository(),
    private val villageId: String
) : ViewModel() {
    private val _state = MutableStateFlow(HubState<Banner>())
    val state = _state.asStateFlow()
    init { load() }
    private fun load() = viewModelScope.launch {
        repository.getBanners(villageId).collect { res ->
            when (res) {
                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Success -> {
                    val items = res.data ?: emptyList()
                    val finalItems = if (villageId == "all") {
                        items.distinctBy { "${it.title}_${it.imageUrl}" }
                    } else items
                    _state.update { it.copy(isLoading = false, items = finalItems) }
                }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = res.message) }
            }
        }
    }
    fun onSearchQueryChange(q: String) { _state.update { it.copy(searchQuery = q) } }
    
    fun save(item: Banner) { 
        viewModelScope.launch { 
            if (villageId == "all" && item.id.isEmpty()) {
                villageRepository.getVillages().filter { it is Resource.Success }.first().data?.forEach { v ->
                    repository.saveBanner(v.id, item.copy(villageId = v.id))
                }
            } else {
                val vId = if (villageId == "all") item.villageId else villageId
                repository.saveBanner(vId, item.copy(villageId = vId)) 
            }
        } 
    }
    
    fun delete(item: Banner) { 
        viewModelScope.launch { 
            if (villageId == "all") {
                repository.getBanners("all").filter { it is Resource.Success }.first().data?.forEach { b ->
                    if (b.title == item.title && b.imageUrl == item.imageUrl) {
                        repository.deleteBanner(b.villageId, b.id)
                    }
                }
            } else {
                repository.deleteBanner(villageId, item.id)
            }
        } 
    }
}

class NotificationViewModel(
    private val repository: NewsBannerRepository = NewsBannerRepository(),
    private val villageRepository: VillageRepository = VillageRepository(),
    private val villageId: String
) : ViewModel() {
    private val _state = MutableStateFlow(HubState<AppNotification>())
    val state = _state.asStateFlow()
    init { load() }
    private fun load() = viewModelScope.launch {
        repository.getNotifications(villageId).collect { res ->
            when (res) {
                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Success -> {
                    val items = res.data ?: emptyList()
                    val finalItems = if (villageId == "all") {
                        items.distinctBy { "${it.title}_${it.message}" }
                    } else items
                    _state.update { it.copy(isLoading = false, items = finalItems) }
                }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = res.message) }
            }
        }
    }
    fun onSearchQueryChange(q: String) { _state.update { it.copy(searchQuery = q) } }
    
    fun save(item: AppNotification) { 
        viewModelScope.launch { 
            if (villageId == "all" && item.id.isEmpty()) {
                villageRepository.getVillages().filter { it is Resource.Success }.first().data?.forEach { v ->
                    repository.saveNotification(v.id, item.copy(villageId = v.id))
                }
            } else {
                val vId = if (villageId == "all") item.villageId else villageId
                // Use a separate scope or non-cancellable context for network operations after save
                repository.saveNotification(vId, item.copy(villageId = vId)) 
            }
        } 
    }
    
    fun delete(item: AppNotification) { 
        viewModelScope.launch { 
            if (villageId == "all") {
                repository.getNotifications("all").filter { it is Resource.Success }.first().data?.forEach { n ->
                    if (n.title == item.title && n.message == item.message) {
                        repository.deleteNotification(n.villageId, n.id)
                    }
                }
            } else {
                repository.deleteNotification(villageId, item.id)
            }
        } 
    }
}
