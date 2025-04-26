package com.uniandes.vinilos.data.model

data class Artist(
    val id: String,
    val name: String,
    val image: String,
    val description: String = "",
    val creationDate: String = ""
)