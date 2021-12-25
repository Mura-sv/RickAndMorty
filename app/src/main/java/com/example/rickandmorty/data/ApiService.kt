package com.example.rickandmorty.data

import com.example.rickandmorty.data.model.characters.CharacterDto
import com.example.rickandmorty.data.model.characters.CharactersModel
import com.example.rickandmorty.data.model.episode.EpisodeDto
import com.example.rickandmorty.data.model.episode.EpisodeModel
import com.example.rickandmorty.data.model.location.LocationDto
import com.example.rickandmorty.data.model.location.LocationModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Character

    @GET("api/character/{id}")
    suspend fun fetchCharacterById(
        @Path("id") id: Int,
    ): Response<CharacterDto>

    @GET("api/character")
    suspend fun fetchAllCharacters(
        @Query("page") page: Int,
    ): Response<CharactersModel>

    @GET("api/character/")
    suspend fun fetchCharactersByName(
        @Query("name") name: String,
    ): Response<CharactersModel>

    @GET("api/character/")
    suspend fun fetchCharactersByNameAndPage(
        @Query("page") page: Int,
        @Query("name") name: String,
    ): Response<CharactersModel>

    // Location


    @GET("api/location")
    suspend fun fetchAllLocationsByPage(
        @Query("page") page: Int,
    ): Response<LocationModel>

    @GET("api/location")
    suspend fun fetchAllLocationByName(
        @Query("name") name: String,
    ): Response<LocationModel>

    @GET("api/location/{id}")
    suspend fun fetchLocationById(
        @Path("id") id: Int,
    ): Response<LocationDto>


    // Episode

    @GET("api/episode")
    suspend fun fetchAllEpisodesByPage(
        @Query("page") page: Int,
    ): Response<EpisodeModel>

    @GET("api/episode")
    suspend fun fetchAllEpisodeByName(
        @Query("name") name: String,
    ): Response<EpisodeModel>

    @GET("api/episode/{id}")
    suspend fun fetchEpisodeById(
        @Path("id") id: Int,
    ): Response<EpisodeDto>


}