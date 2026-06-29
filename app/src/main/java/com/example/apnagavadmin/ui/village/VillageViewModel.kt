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

data class VillageState(
    val villages: List<Village> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
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
            repository.addVillage(village)
        }
    }

    fun updateVillage(village: Village) {
        viewModelScope.launch {
            repository.updateVillage(village)
        }
    }

    fun deleteVillage(villageId: String) {
        viewModelScope.launch {
            repository.deleteVillage(villageId)
        }
    }
}
