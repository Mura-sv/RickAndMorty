package com.example.rickandmorty.domain.model

import java.io.Serializable

data class Search(
    val id: Int,
    val name: String,
    val created: String,

    // character
    val image: String? ,
    val status : String?,
    val species : String?,
    val gender : String?,

    //location
    val type: String?,
    val dimension: String?,

    //episode
    val episode: String?,
    val air_date: String?,

) : Serializable
