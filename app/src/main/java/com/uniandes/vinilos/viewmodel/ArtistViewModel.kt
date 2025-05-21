package com.uniandes.vinilos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uniandes.vinilos.data.dto.AlbumRequestDTO
import com.uniandes.vinilos.data.dto.ArtistRequestDTO
import com.uniandes.vinilos.data.model.Artist
import com.uniandes.vinilos.data.repository.ArtistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ArtistsUiState(
    val artists: List<Artist> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class SelectedArtistUiState(
    val artist: Artist? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class ArtistViewModel: ViewModel(){

    private val repository = ArtistRepository()

    private val _artistState = MutableStateFlow(ArtistsUiState(isLoading = true))
    val artistState: StateFlow<ArtistsUiState> = _artistState.asStateFlow()

    private val _selectedArtistState = MutableStateFlow(SelectedArtistUiState())
    val selectedArtistState: StateFlow<SelectedArtistUiState> = _selectedArtistState.asStateFlow()

    fun loadArtists() {
        viewModelScope.launch {
            _artistState.update { it.copy(isLoading = true, error = null) }

            try {
                val artists = repository.getArtists()
                _artistState.update { it.copy(artists = artists, isLoading = false) }
            } catch (e: Exception) {
                _artistState.update {
                    it.copy(
                        error = e.message ?: "Error desconocido al cargar artistas",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun loadArtistById(artistId: Int) {
        viewModelScope.launch {
            _selectedArtistState.update { it.copy(isLoading = true, error = null) }

            try {
                val artist = repository.getArtistById(artistId)
                _selectedArtistState.update {
                    it.copy(artist = artist, isLoading = false)
                }
            } catch (e: Exception) {
                _selectedArtistState.update {
                    it.copy(
                        error = e.message ?: "Error desconocido al cargar el artista",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun createArtist(artist: ArtistRequestDTO, onSuccess: () -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                repository.createArtist(artist)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}