package com.uniandes.vinilos.data.repository

import com.uniandes.vinilos.data.model.Album
import com.uniandes.vinilos.data.adapters.RetrofitClient

class AlbumRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getAlbums(): List<Album> {
        return apiService.getAlbums()
    }

    suspend fun getAlbumById(albumId: Int): Album {
        return apiService.getAlbumById(albumId)
    }
}