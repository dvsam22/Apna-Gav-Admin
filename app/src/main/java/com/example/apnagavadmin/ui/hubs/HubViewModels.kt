package com.example.apnagavadmin.ui.hubs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apnagavadmin.data.model.*
import com.example.apnagavadmin.data.repository.*
import com.example.apnagavadmin.util.AppError
import com.example.apnagavadmin.util.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class HubState<T>(
    val items: List<T> = emptyList(),
    val categories: List<LabourCategory> = emptyList(),
    val isLoading: Boolean = false,
    val error: AppError? = null,
    val searchQuery: String = "",
    val shouldDismiss: Boolean = false
)

abstract class BaseHubViewModel<T>(
    protected val villageId: String,
    protected val villageRepository: VillageRepository = VillageRepository()
) : ViewModel() {
    protected val _state = MutableStateFlow(HubState<T>())
    val state = _state.asStateFlow()

    protected var selectedCategoryId: String? = null

    fun selectCategory(categoryId: String?) {
        selectedCategoryId = categoryId
        load()
    }

    fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    fun resetDismiss() {
        _state.update { it.copy(shouldDismiss = false) }
    }

    abstract fun load()
    abstract fun save(item: T)
    abstract fun delete(item: T)

    /**
     * Helper to handle a flow of resources in the state
     */
    protected fun <R> collectResource(
        flow: Flow<Resource<List<R>>>,
        distinctSelector: (R) -> Any? = { it }
    ) = viewModelScope.launch {
        flow.collect { res ->
            when (res) {
                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                is Resource.Success -> {
                    val items = res.data ?: emptyList()
                    @Suppress("UNCHECKED_CAST")
                    val finalItems = if (villageId == "all") {
                        items.distinctBy(distinctSelector)
                    } else items
                    _state.update { it.copy(isLoading = false, items = finalItems as List<T>) }
                }
                is Resource.Error -> _state.update { it.copy(isLoading = false, error = res.error) }
            }
        }
    }

    /**
     * Helper for saving items to all villages if villageId is "all"
     */
    protected fun performSave(
        isNew: Boolean,
        item: T,
        saveAction: suspend (String, T) -> Resource<Unit>
    ) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        val result = if (villageId == "all" && isNew) {
            val villages = villageRepository.getVillages().filter { it is Resource.Success }.first().data
            var lastRes: Resource<Unit> = Resource.Success(Unit)
            villages?.forEach { v ->
                lastRes = saveAction(v.id, item)
            }
            lastRes
        } else {
            val vId = if (villageId == "all") (item as? HubItem)?.villageId ?: villageId else villageId
            saveAction(vId, item)
        }
        
        _state.update { it.copy(isLoading = false) }
        when (result) {
            is Resource.Success -> {
                com.example.apnagavadmin.util.GlobalEventBus.showToast("Saved successfully!")
                _state.update { it.copy(shouldDismiss = true) }
            }
            is Resource.Error -> com.example.apnagavadmin.util.GlobalEventBus.showToast("Error: ${result.error?.message}")
            else -> {}
        }
    }
}

class LabourViewModel(
    private val repository: LabourRepository = LabourRepository(),
    villageRepository: VillageRepository = VillageRepository(),
    villageId: String
) : BaseHubViewModel<LabourProvider>(villageId, villageRepository) {

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

    override fun load() {
        selectedCategoryId?.let { catId ->
            collectResource(repository.getProviders(villageId, catId)) { "${it.name}_${it.contact}_${it.categoryId}" }
        }
    }

    override fun save(item: LabourProvider) {
        performSave(item.id.isEmpty(), item) { vId, p ->
            repository.saveProvider(vId, p.copy(villageId = vId, categoryId = selectedCategoryId ?: ""))
        }
    }

    override fun delete(item: LabourProvider) {
        viewModelScope.launch {
            if (villageId == "all") {
                repository.getProviders("all", selectedCategoryId ?: "").filter { it is Resource.Success }.first().data?.forEach { p ->
                    if (p.name == item.name && p.contact == item.contact) {
                        repository.deleteProvider(p.villageId, p.id)
                    }
                }
            } else {
                repository.deleteProvider(villageId, item.id)
            }
        }
    }
}

class ConstructionViewModel(
    private val repository: ConstructionRepository = ConstructionRepository(),
    villageRepository: VillageRepository = VillageRepository(),
    villageId: String
) : BaseHubViewModel<ConstructionHub>(villageId, villageRepository) {

    override fun load() {
        collectResource(repository.getHubs(villageId, selectedCategoryId)) { "${it.shopName}_${it.contact}_${it.categoryId}" }
    }

    override fun save(item: ConstructionHub) {
        performSave(item.id.isEmpty(), item) { vId, h ->
            repository.saveHub(vId, h.copy(villageId = vId, categoryId = selectedCategoryId ?: "bricks"))
        }
    }

    override fun delete(item: ConstructionHub) {
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
    villageRepository: VillageRepository = VillageRepository(),
    villageId: String
) : BaseHubViewModel<TransportHub>(villageId, villageRepository) {

    override fun load() {
        collectResource(repository.getHubs(villageId, selectedCategoryId)) { "${it.name}_${it.contact}_${it.categoryId}" }
    }

    override fun save(item: TransportHub) {
        performSave(item.id.isEmpty(), item) { vId, t ->
            repository.saveHub(vId, t.copy(villageId = vId, categoryId = selectedCategoryId ?: "tractor"))
        }
    }

    override fun delete(item: TransportHub) {
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
    villageRepository: VillageRepository = VillageRepository(),
    villageId: String
) : BaseHubViewModel<MandiPrice>(villageId, villageRepository) {

    override fun load() {
        collectResource(repository.getPrices(villageId, selectedCategoryId)) { "${it.cropName}_${it.price}_${it.unit}_${it.buyerName}_${it.categoryId}" }
    }

    override fun save(item: MandiPrice) {
        performSave(item.id.isEmpty(), item) { vId, m ->
            repository.savePrice(vId, m.copy(villageId = vId, categoryId = selectedCategoryId ?: "prices"))
        }
    }

    override fun delete(item: MandiPrice) {
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
    villageRepository: VillageRepository = VillageRepository(),
    villageId: String
) : BaseHubViewModel<HealthHub>(villageId, villageRepository) {

    override fun load() {
        collectResource(repository.getHubs(villageId, selectedCategoryId)) { "${it.name}_${it.contact}_${it.categoryId}" }
    }

    override fun save(item: HealthHub) {
        performSave(item.id.isEmpty(), item) { vId, h ->
            repository.saveHub(vId, h.copy(villageId = vId, categoryId = selectedCategoryId ?: "doctors"))
        }
    }

    override fun delete(item: HealthHub) {
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
    villageRepository: VillageRepository = VillageRepository(),
    villageId: String
) : BaseHubViewModel<News>(villageId, villageRepository) {

    init { load() }

    override fun load() {
        collectResource(repository.getNews(villageId)) { "${it.title}_${it.description}_${it.type}" }
    }

    override fun save(item: News) {
        performSave(item.id.isEmpty(), item) { vId, n ->
            repository.saveNews(vId, n.copy(villageId = vId))
        }
    }

    override fun delete(item: News) {
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
    villageRepository: VillageRepository = VillageRepository(),
    villageId: String
) : BaseHubViewModel<Banner>(villageId, villageRepository) {

    init { load() }

    override fun load() {
        collectResource(repository.getBanners(villageId)) { "${it.title}_${it.imageUrl}" }
    }

    override fun save(item: Banner) {
        performSave(item.id.isEmpty(), item) { vId, b ->
            repository.saveBanner(vId, b.copy(villageId = vId))
        }
    }

    override fun delete(item: Banner) {
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
    villageRepository: VillageRepository = VillageRepository(),
    villageId: String
) : BaseHubViewModel<AppNotification>(villageId, villageRepository) {

    init { load() }

    override fun load() {
        collectResource(repository.getNotifications(villageId)) { "${it.title}_${it.message}" }
    }

    override fun save(item: AppNotification) {
        performSave(item.id.isEmpty(), item) { vId, n ->
            repository.saveNotification(vId, n.copy(villageId = vId))
        }
    }

    override fun delete(item: AppNotification) {
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

class FamilyFunctionViewModel(
    private val repository: FamilyFunctionRepository = FamilyFunctionRepository(),
    villageRepository: VillageRepository = VillageRepository(),
    villageId: String
) : BaseHubViewModel<FamilyFunctionHub>(villageId, villageRepository) {

    override fun load() {
        collectResource(repository.getHubs(villageId, selectedCategoryId)) { "${it.name}_${it.contact}_${it.categoryId}" }
    }

    override fun save(item: FamilyFunctionHub) {
        performSave(item.id.isEmpty(), item) { vId, h ->
            repository.saveHub(vId, h.copy(villageId = vId, categoryId = selectedCategoryId ?: "tent"))
        }
    }

    override fun delete(item: FamilyFunctionHub) {
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
