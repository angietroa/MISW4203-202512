package com.uniandes.vinilos.data.repository

import com.uniandes.vinilos.data.model.Artist
import com.uniandes.vinilos.data.adapters.RetrofitClient

class ArtistRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getArtists(): List<Artist>{
        return apiService.getArtists()
    }

    suspend fun getArtistById(artistId: Int): Artist {
        return apiService.getArtistById(artistId)
    }
}