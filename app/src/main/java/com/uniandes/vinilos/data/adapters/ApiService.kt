package com.uniandes.vinilos.data.adapters

import com.uniandes.vinilos.data.model.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("albums")
    suspend fun getAlbums(): List<Album>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") albumId: Int): Album
}