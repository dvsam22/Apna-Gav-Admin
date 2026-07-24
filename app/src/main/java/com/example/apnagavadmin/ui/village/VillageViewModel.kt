package com.example.apnagavadmin.ui.village

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apnagavadmin.data.model.Village
import com.example.apnagavadmin.data.repository.VillageRepository
import com.example.apnagavadmin.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class VillageFilter {
    ALL, ACTIVE, INACTIVE
}

data class VillageState(
    val villages: List<Village> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val selectedFilter: VillageFilter = VillageFilter.ALL
)

class VillageViewModel(
    private val repository: VillageRepository = VillageRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(VillageState())
    val state: StateFlow<VillageState> = _state.asStateFlow()

    init {
        getVillages()
    }

    fun onSearchQueryChange(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
    }

    fun onFilterChange(filter: VillageFilter) {
        _state.value = _state.value.copy(selectedFilter = filter)
    }

    private fun getVillages() {
        viewModelScope.launch {
            repository.getVillages().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            villages = resource.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    fun addVillage(village: Village) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = repository.addVillage(village)
            if (result is Resource.Error) {
                _state.value = _state.value.copy(error = result.message, isLoading = false)
            } else {
                _state.value = _state.value.copy(isLoading = false, error = null)
            }
        }
    }

    fun updateVillage(village: Village) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = repository.updateVillage(village)
            if (result is Resource.Error) {
                _state.value = _state.value.copy(error = result.message, isLoading = false)
            } else {
                _state.value = _state.value.copy(isLoading = false, error = null)
            }
        }
    }

    fun toggleVillageActive(village: Village, isActive: Boolean) {
        viewModelScope.launch {
            val updatedList = _state.value.villages.map { v ->
                if (v.id == village.id) v.copy(isActive = isActive) else v
            }
            _state.value = _state.value.copy(villages = updatedList)

            val result = repository.updateVillageStatus(village.id, isActive)
            if (result is Resource.Error) {
                val revertedList = _state.value.villages.map { v ->
                    if (v.id == village.id) v.copy(isActive = !isActive) else v
                }
                _state.value = _state.value.copy(villages = revertedList, error = result.message)
            }
        }
    }

    fun deleteVillage(villageId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val result = repository.deleteVillage(villageId)
            if (result is Resource.Error) {
                _state.value = _state.value.copy(error = result.message, isLoading = false)
            } else {
                _state.value = _state.value.copy(isLoading = false, error = null)
            }
        }
    }
}
