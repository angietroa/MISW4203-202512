package com.uniandes.vinilos.data.model

data class Album(
    val id: String,
    val cover: String,
    val name: String,
    val performers: List<Artist> = emptyList(),
    // NOTE: Remove!
    var artist: String
)