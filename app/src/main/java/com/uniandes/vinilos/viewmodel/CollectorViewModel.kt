package com.uniandes.vinilos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uniandes.vinilos.data.dto.AlbumRequestDTO
import com.uniandes.vinilos.data.dto.CollectorAlbumRequestDTO
import com.uniandes.vinilos.data.model.Artist
import com.uniandes.vinilos.data.model.Collector
import com.uniandes.vinilos.data.repository.ArtistRepository
import com.uniandes.vinilos.data.repository.CollectorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CollectorsUiState(
    val collectors: List<Collector> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class SelectedCollectorsUiState(
    val collector: Collector? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class CollectorViewModel: ViewModel(){

    private val repository = CollectorRepository()

    private val _collectorState = MutableStateFlow(CollectorsUiState(isLoading = true))
    val collectorState: StateFlow<CollectorsUiState> = _collectorState.asStateFlow()

    private val _selectedCollectorState = MutableStateFlow(SelectedCollectorsUiState())
    val selectedCollectorState: StateFlow<SelectedCollectorsUiState> = _selectedCollectorState.asStateFlow()

    fun loadCollectors() {
        viewModelScope.launch {
            _collectorState.update { it.copy(isLoading = true, error = null) }

            try {
                val collectors = repository.getCollectors()
                _collectorState.update { it.copy(collectors = collectors, isLoading = false) }
            } catch (e: Exception) {
                _collectorState.update {
                    it.copy(
                        error = e.message ?: "Error desconocido al cargar artistas",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun loadCollectorById(collectorId: Int) {
        viewModelScope.launch {
            _selectedCollectorState.update { it.copy(isLoading = true, error = null) }

            try {
                val collector = repository.getCollectorById(collectorId)
                _selectedCollectorState.update {
                    it.copy(collector = collector, isLoading = false)
                }
            } catch (e: Exception) {
                _selectedCollectorState.update {
                    it.copy(
                        error = e.message ?: "Error desconocido al cargar el artista",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun createCollectoralbum(collectorId: String, albumId: String, requestBody: CollectorAlbumRequestDTO, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                repository.addAlbumToCollector(
                    collectorId = collectorId,
                    albumId = albumId,
                    requestBody = requestBody,
                )
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}