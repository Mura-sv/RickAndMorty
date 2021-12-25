package com.example.rickandmorty.data.model.episode

data class EpisodeModel(
    val results: List<EpisodeDto>,
    val info: Info,
)
{
    data class Info(
        val count: Int,
        val next: String,
        val pages: Int,
        val prev: Any
    )
}