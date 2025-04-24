package com.uniandes.vinilos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uniandes.vinilos.data.model.Album
import com.uniandes.vinilos.data.repository.AlbumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AlbumUiState(
    val albums: List<Album> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class SelectedAlbumUiState(
    val album: Album? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)


class AlbumViewModel : ViewModel() {
    private val repository = AlbumRepository()

    private val _albumState = MutableStateFlow(AlbumUiState(isLoading = true))
    val albumState: StateFlow<AlbumUiState> = _albumState.asStateFlow()

    private val _selectedAlbumState = MutableStateFlow(SelectedAlbumUiState())
    val selectedAlbumState: StateFlow<SelectedAlbumUiState> = _selectedAlbumState.asStateFlow()


    fun loadAlbums() {
        viewModelScope.launch {
            _albumState.update { it.copy(isLoading = true, error = null) }

            try {
                val albums = repository.getAlbums()
                _albumState.update { it.copy(albums = albums, isLoading = false) }
            } catch (e: Exception) {
                _albumState.update {
                    it.copy(
                        error = e.message ?: "Error desconocido al cargar álbumes",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun loadAlbumById(albumId: Int) {
        viewModelScope.launch {
            _selectedAlbumState.update { it.copy(isLoading = true, error = null) }

            try {
                val album = repository.getAlbumById(albumId)
                _selectedAlbumState.update {
                    it.copy(album = album, isLoading = false)
                }
            } catch (e: Exception) {
                _selectedAlbumState.update {
                    it.copy(
                        error = e.message ?: "Error desconocido al cargar el álbum",
                        isLoading = false
                    )
                }
            }
        }
    }
}