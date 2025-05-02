package com.uniandes.vinilos.data.dto

data class AlbumRequestDTO(
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String
)
