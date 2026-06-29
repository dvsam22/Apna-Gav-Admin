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
                        LabourCategory(id = "rajmistri", name = "Rajmistri"),
                        LabourCategory(id = "plumber", name = "Plumber"),
                        LabourCategory(id = "electrician", name = "Electrician"),
                        LabourCategory(id = "carpenter", name = "Carpenter"),
                        LabourCategory(id = "tailor", name = "Tailor"),
                        LabourCategory(id = "labour", name = "Labour")
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
                    is Resource.Success -> _state.update { it.copy(isLoading = false, items = res.data ?: emptyList()) }
                    is Resource.Error -> _state.update { it.copy(isLoading = false, error = res.message) }
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) { _state.update { it.copy(searchQuery = query) } }
    fun saveProvider(provider: LabourProvider) { viewModelScope.launch { repository.saveProvider(villageId, provider.copy(villageId = villageId, categoryId = selectedCategoryId)) } }
    fun deleteProvider(id: String) { viewModelScope.launch { repository.deleteProvider(villageId, id) } }
}

class ConstructionViewModel(private val repository: ConstructionRepository = ConstructionRepository(), private val villageId: String) : ViewModel() {
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
                is Resource.Success -> _state.update { it.copy(isLoading = false, items = res.data ?: emptyList()) }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = res.message) }
            }
        }
    }
    fun onSearchQueryChange(q: String) { _state.update { it.copy(searchQuery = q) } }
    fun save(item: ConstructionHub) { 
        viewModelScope.launch { 
            repository.saveHub(villageId, item.copy(villageId = villageId, categoryId = selectedCategoryId ?: "bricks")) 
        } 
    }
    fun delete(id: String) { viewModelScope.launch { repository.deleteHub(villageId, id) } }
}

class TransportViewModel(private val repository: TransportRepository = TransportRepository(), private val villageId: String) : ViewModel() {
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
                is Resource.Success -> _state.update { it.copy(isLoading = false, items = res.data ?: emptyList()) }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = res.message) }
            }
        }
    }
    fun onSearchQueryChange(q: String) { _state.update { it.copy(searchQuery = q) } }
    fun save(item: TransportHub) { 
        viewModelScope.launch { 
            repository.saveHub(villageId, item.copy(villageId = villageId, categoryId = selectedCategoryId ?: "tractor")) 
        } 
    }
    fun delete(id: String) { viewModelScope.launch { repository.deleteHub(villageId, id) } }
}

class MandiViewModel(private val repository: MandiRepository = MandiRepository(), private val villageId: String) : ViewModel() {
    private val _state = MutableStateFlow(HubState<MandiPrice>())
    val state = _state.asStateFlow()
    init { load() }
    private fun load() = viewModelScope.launch {
        repository.getPrices(villageId).collect { res ->
            when (res) {
                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Success -> _state.update { it.copy(isLoading = false, items = res.data ?: emptyList()) }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = res.message) }
            }
        }
    }
    fun onSearchQueryChange(q: String) { _state.update { it.copy(searchQuery = q) } }
    fun save(item: MandiPrice) { viewModelScope.launch { repository.savePrice(villageId, item.copy(villageId = villageId)) } }
    fun delete(id: String) { viewModelScope.launch { repository.deletePrice(villageId, id) } }
}

class HealthViewModel(private val repository: HealthRepository = HealthRepository(), private val villageId: String) : ViewModel() {
    private val _state = MutableStateFlow(HubState<HealthHub>())
    val state = _state.asStateFlow()
    init { load() }
    private fun load() = viewModelScope.launch {
        repository.getHubs(villageId).collect { res ->
            when (res) {
                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Success -> _state.update { it.copy(isLoading = false, items = res.data ?: emptyList()) }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = res.message) }
            }
        }
    }
    fun onSearchQueryChange(q: String) { _state.update { it.copy(searchQuery = q) } }
    fun save(item: HealthHub) { viewModelScope.launch { repository.saveHub(villageId, item.copy(villageId = villageId)) } }
    fun delete(id: String) { viewModelScope.launch { repository.deleteHub(villageId, id) } }
}

class NewsViewModel(private val repository: NewsBannerRepository = NewsBannerRepository(), private val villageId: String) : ViewModel() {
    private val _state = MutableStateFlow(HubState<News>())
    val state = _state.asStateFlow()
    init { load() }
    private fun load() = viewModelScope.launch {
        repository.getNews(villageId).collect { res ->
            when (res) {
                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Success -> _state.update { it.copy(isLoading = false, items = res.data ?: emptyList()) }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = res.message) }
            }
        }
    }
    fun onSearchQueryChange(q: String) { _state.update { it.copy(searchQuery = q) } }
    fun save(item: News) { viewModelScope.launch { repository.saveNews(villageId, item.copy(villageId = villageId)) } }
    fun delete(id: String) { viewModelScope.launch { repository.deleteNews(villageId, id) } }
}

class BannerViewModel(private val repository: NewsBannerRepository = NewsBannerRepository(), private val villageId: String) : ViewModel() {
    private val _state = MutableStateFlow(HubState<Banner>())
    val state = _state.asStateFlow()
    init { load() }
    private fun load() = viewModelScope.launch {
        repository.getBanners(villageId).collect { res ->
            when (res) {
                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Success -> _state.update { it.copy(isLoading = false, items = res.data ?: emptyList()) }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = res.message) }
            }
        }
    }
    fun onSearchQueryChange(q: String) { _state.update { it.copy(searchQuery = q) } }
    fun save(item: Banner) { viewModelScope.launch { repository.saveBanner(villageId, item.copy(villageId = villageId)) } }
    fun delete(id: String) { viewModelScope.launch { repository.deleteBanner(villageId, id) } }
}
