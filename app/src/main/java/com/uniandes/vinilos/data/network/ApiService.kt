package com.uniandes.vinilos.data.network

import com.uniandes.vinilos.data.model.Album
import retrofit2.http.GET

interface ApiService {
    @GET("albums")
    suspend fun getAlbums(): List<Album>
}