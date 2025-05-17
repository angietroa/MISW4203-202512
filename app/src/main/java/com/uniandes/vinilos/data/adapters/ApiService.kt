package com.uniandes.vinilos.data.adapters

import com.uniandes.vinilos.data.model.Album
import com.uniandes.vinilos.data.model.Artist
import com.uniandes.vinilos.data.dto.AlbumRequestDTO
import com.uniandes.vinilos.data.dto.CollectorAlbumRequestDTO
import com.uniandes.vinilos.data.model.Collector
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

@Suppress("unused")
interface ApiService {
    @GET("albums")
    suspend fun getAlbums(): List<Album>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") albumId: Int): Album

    @POST("albums")
    suspend fun createAlbums(@Body album: AlbumRequestDTO): Album

    @GET("musicians")
    suspend fun getArtists(): List<Artist>

    @GET("musicians/{id}")
    suspend fun getArtistById(@Path("id") artistId: Int): Artist

    @GET("collectors")
    suspend fun getCollectors(): List<Collector>

    @GET("collectors/{id}")
    suspend fun getCollectorById(@Path("id") artistId: Int): Collector

    @POST("collectors/{collectorId}/albums/{albumId}")
    suspend fun addAlbumToCollector(
        @Path("collectorId") collectorId: String,
        @Path("albumId") albumId: String,
        @Body requestBody: CollectorAlbumRequestDTO
    ): Collector
}