package com.uniandes.vinilos.data.model

data class Collector(
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val cover: String,
    val comments: List<CollectorComment> = emptyList(),
    val favoritePerformers: List<Performer> = emptyList(),
    val collectorAlbums: List<CollectorAlbum> = emptyList()
)

data class CollectorComment(
    val id: Int,
    val description: String,
    val rating: Int
)

data class Performer(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String
)

data class CollectorAlbum(
    val id: Int,
    val price: Int,
    val status: String
)