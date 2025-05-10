package com.uniandes.vinilos.data.repository

import com.uniandes.vinilos.data.model.Artist
import com.uniandes.vinilos.data.adapters.RetrofitClient
import com.uniandes.vinilos.data.cache.CacheEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import java.util.WeakHashMap

/**
 * Repositorio para gestionar artistas con optimizaciones de memoria.
 * Utiliza WeakHashMap para permitir que las entradas de caché sean liberadas
 * cuando hay presión de memoria.
 */
class ArtistRepository {
    private val apiService = RetrofitClient.apiService
    
    // Caché para la lista de artistas
    private val artistsCache = CacheEntry<List<Artist>>(null)
    
    // Caché para artistas individuales por ID usando WeakHashMap
    // Esto permite que las entradas sean liberadas cuando hay presión de memoria
    // y no son fuertemente referenciadas en otro lugar
    private val artistByIdCache = WeakHashMap<Int, CacheEntry<Artist>>()
    
    // Mutex para operaciones de escritura seguras en la caché
    private val cacheMutex = Mutex()
    
    // Tiempo de vida de caché (en milisegundos)
    private val cacheLifetime = TimeUnit.SECONDS.toMillis(5)
    
    suspend fun getArtists(): List<Artist> = withContext(Dispatchers.IO) {
        // Verificamos si hay datos en caché y si son válidos
        if (artistsCache.data != null && !artistsCache.isExpired(cacheLifetime)) {
            return@withContext artistsCache.data!!
        }
        
        // Si no hay datos en caché o han expirado, hacemos la llamada a la API
        val freshData = apiService.getArtists()
        
        // Actualizamos la caché de manera segura con los nuevos datos
        cacheMutex.withLock {
            artistsCache.update(freshData)
        }
        
        return@withContext freshData
    }

    suspend fun getArtistById(artistId: Int): Artist = withContext(Dispatchers.IO) {
        // Verificamos si hay datos en caché para este artista y si son válidos
        val cacheEntry = artistByIdCache[artistId]
        if (cacheEntry?.data != null && !cacheEntry.isExpired(cacheLifetime)) {
            return@withContext cacheEntry.data!!
        }
        
        // Si no hay datos en caché o han expirado, hacemos la llamada a la API
        val freshData = apiService.getArtistById(artistId)
        
        // Actualizamos la caché de manera segura con los nuevos datos
        cacheMutex.withLock {
            if (artistByIdCache[artistId] == null) {
                artistByIdCache[artistId] = CacheEntry(null)
            }
            artistByIdCache[artistId]?.update(freshData)
        }
        
        return@withContext freshData
    }
}