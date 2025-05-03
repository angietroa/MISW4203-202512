package com.uniandes.vinilos.data.repository

import com.uniandes.vinilos.data.model.Album
import com.uniandes.vinilos.data.model.Artist
import com.uniandes.vinilos.data.dto.AlbumRequestDTO
import com.uniandes.vinilos.data.adapters.RetrofitClient

class AlbumRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getAlbums(): List<Album> {
        return apiService.getAlbums()
    }

    suspend fun getAlbumById(albumId: Int): Album {
        return apiService.getAlbumById(albumId)
    }

    suspend fun createAlbums(album: AlbumRequestDTO): Album{
        return apiService.createAlbums(album)
    }
}