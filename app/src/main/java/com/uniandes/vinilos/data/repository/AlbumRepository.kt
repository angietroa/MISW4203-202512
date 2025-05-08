package com.uniandes.vinilos.data.repository

import com.uniandes.vinilos.data.model.Album
import com.uniandes.vinilos.data.model.Artist
import com.uniandes.vinilos.data.dto.AlbumRequestDTO
import com.uniandes.vinilos.data.adapters.RetrofitClient
import com.uniandes.vinilos.data.cache.CacheEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class AlbumRepository {
    private val apiService = RetrofitClient.apiService
    
    // Caché para la lista de álbumes
    private val albumsCache = CacheEntry<List<Album>>(null)
    
    // Caché para álbumes individuales por ID
    private val albumByIdCache = mutableMapOf<Int, CacheEntry<Album>>()
    
    // Mutex para operaciones de escritura seguras en la caché
    private val cacheMutex = Mutex()
    
    // Tiempo de vida de caché (en milisegundos)
    private val cacheLifetime = TimeUnit.MINUTES.toMillis(5)
    
    suspend fun getAlbums(): List<Album> = withContext(Dispatchers.IO) {
        // Verificamos si hay datos en caché y si son válidos
        if (albumsCache.data != null && !albumsCache.isExpired(cacheLifetime)) {
            return@withContext albumsCache.data!!
        }
        
        // Si no hay datos en caché o han expirado, hacemos la llamada a la API
        val freshData = apiService.getAlbums()
        
        // Actualizamos la caché de manera segura con los nuevos datos
        cacheMutex.withLock {
            albumsCache.update(freshData)
        }
        
        return@withContext freshData
    }

    suspend fun getAlbumById(albumId: Int): Album = withContext(Dispatchers.IO) {
        // Verificamos si hay datos en caché para este álbum y si son válidos
        val cacheEntry = albumByIdCache[albumId]
        if (cacheEntry?.data != null && !cacheEntry.isExpired(cacheLifetime)) {
            return@withContext cacheEntry.data!!
        }
        
        // Si no hay datos en caché o han expirado, hacemos la llamada a la API
        val freshData = apiService.getAlbumById(albumId)
        
        // Actualizamos la caché de manera segura con los nuevos datos
        cacheMutex.withLock {
            if (albumByIdCache[albumId] == null) {
                albumByIdCache[albumId] = CacheEntry(null)
            }
            albumByIdCache[albumId]?.update(freshData)
        }
        
        return@withContext freshData
    }

    suspend fun createAlbums(album: AlbumRequestDTO): Album = withContext(Dispatchers.IO) {
        val result = apiService.createAlbums(album)
        
        // Invalidamos la caché de álbumes para que se recargue en la próxima solicitud
        cacheMutex.withLock {
            albumsCache.invalidate()
        }
        
        return@withContext result
    }
}