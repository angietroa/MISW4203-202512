package com.uniandes.vinilos.data.model

data class Album(
    val id: String,
    val name: String,
    val cover: String,
    val releaseDate: String = "",
    val description: String = "",
    val performers: List<Artist> = emptyList(),
    // NOTE: Remove!
    var artist: String
)