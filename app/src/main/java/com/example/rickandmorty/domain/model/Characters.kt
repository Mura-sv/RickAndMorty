package com.example.rickandmorty.domain.model

data class Characters(
    val id: Int,
    val name: String,
    val image: String,
    val status: String,
    val species: String,
    val origin: String
)
