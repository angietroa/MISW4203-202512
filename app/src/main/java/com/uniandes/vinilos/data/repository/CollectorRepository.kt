package com.uniandes.vinilos.data.repository

import com.uniandes.vinilos.data.model.Artist
import com.uniandes.vinilos.data.adapters.RetrofitClient
import com.uniandes.vinilos.data.cache.CacheEntry
import com.uniandes.vinilos.data.dto.AlbumRequestDTO
import com.uniandes.vinilos.data.dto.CollectorAlbumRequestDTO
import com.uniandes.vinilos.data.model.Album
import com.uniandes.vinilos.data.model.Collector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import retrofit2.http.Path
import java.util.concurrent.TimeUnit
import java.util.WeakHashMap

class CollectorRepository {
    private val apiService = RetrofitClient.apiService
    private val collectorsCache = CacheEntry<List<Collector>>(null)
    private val collectorByIdCache = WeakHashMap<Int, CacheEntry<Collector>>()
    private val cacheMutex = Mutex()
    private val cacheLifetime = TimeUnit.SECONDS.toMillis(5)
    
    suspend fun getCollectors(): List<Collector> = withContext(Dispatchers.IO) {
        if (collectorsCache.data != null && !collectorsCache.isExpired(cacheLifetime)) {
            return@withContext collectorsCache.data!!
        }
        
        val freshData = apiService.getCollectors()
        
        cacheMutex.withLock {
            collectorsCache.update(freshData)
        }
        
        return@withContext freshData
    }

    suspend fun getCollectorById(collectorId: Int): Collector = withContext(Dispatchers.IO) {
        val cacheEntry = collectorByIdCache[collectorId]
        if (cacheEntry?.data != null && !cacheEntry.isExpired(cacheLifetime)) {
            return@withContext cacheEntry.data!!
        }
        
        val freshData = apiService.getCollectorById(collectorId)
        
        cacheMutex.withLock {
            if (collectorByIdCache[collectorId] == null) {
                collectorByIdCache[collectorId] = CacheEntry(null)
            }
            collectorByIdCache[collectorId]?.update(freshData)
        }
        
        return@withContext freshData
    }

    suspend fun addAlbumToCollector(collectorId: String, albumId: String, requestBody: CollectorAlbumRequestDTO): Collector {
        println("COLLECTOR ID")
        println(collectorId)
        println("ALBUM ID")
        println(albumId)
        println("REQUEST BODY")
        println(requestBody)
        val result = apiService.addAlbumToCollector(collectorId, albumId, requestBody)

        cacheMutex.withLock {
            collectorsCache.invalidate()
        }

        return result
    }
}