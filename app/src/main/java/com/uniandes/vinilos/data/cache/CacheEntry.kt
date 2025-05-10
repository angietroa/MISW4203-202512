package com.uniandes.vinilos.data.cache

/**
 * Clase utilitaria genérica para gestionar entradas de caché con timestamps.
 * Permite almacenar datos de cualquier tipo junto con un timestamp para controlar expiración.
 *
 * @param T El tipo de datos que se almacenará en la caché
 * @property data Los datos almacenados en la caché, inicialmente pueden ser null
 */
class CacheEntry<T>(var data: T?) {
    // Timestamp cuando se actualizaron los datos por última vez (millisegundos)
    private var timestamp: Long = 0
    
    /**
     * Actualiza los datos en caché y establece el timestamp actual
     * 
     * @param data Los nuevos datos a almacenar
     */
    fun update(data: T) {
        this.data = data
        this.timestamp = System.currentTimeMillis()
    }
    
    /**
     * Verifica si los datos han expirado basándose en el tiempo de vida especificado
     * 
     * @param lifetime Tiempo de vida en milisegundos
     * @return true si los datos han expirado, false de lo contrario
     */
    fun isExpired(lifetime: Long): Boolean {
        return System.currentTimeMillis() - timestamp > lifetime
    }
    
    /**
     * Invalida los datos almacenados en caché
     */
    fun invalidate() {
        data = null
    }
}