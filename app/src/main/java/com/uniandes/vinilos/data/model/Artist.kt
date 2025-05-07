package com.uniandes.vinilos.data.model

@Suppress("unused")
data class Artist(
    val id: String,
    val name: String,
    val image: String,
    val description: String = "",
    val birthDate: String = "",
    val albums: List<Album> = emptyList()
)