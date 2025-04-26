package com.uniandes.vinilos.data.model

data class Album(
    val id: String,
    val name: String,
    val cover: String,
    val releaseDate: String = "",
    val description: String = "",
    val genre: String = "",
    val recordLabel: String = "",
    val performers: List<Artist> = emptyList(),
    val tracks: List<Track> = emptyList(),
    val comments: List<Comment> = emptyList(),
    // NOTE: Remove!
    var artist: String
)