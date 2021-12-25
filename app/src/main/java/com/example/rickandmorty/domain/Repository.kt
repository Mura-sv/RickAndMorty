package com.example.rickandmorty.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.rickandmorty.core.network.Resource
import com.example.rickandmorty.domain.model.*

interface Repository {

    fun fetchCharactersByPage(): LiveData<PagingData<Characters>>
    fun fetchEpisodesByPage(): LiveData<PagingData<Episodes>>
    fun fetchLocationsByPage(): LiveData<PagingData<Locations>>
    fun fetchCharactersByNameAndPage(name: String): LiveData<PagingData<Characters>>

    fun characterDetailById(id: Int): LiveData<Resource<CharacterDetail>>
    fun episodeByDetailById(id: Int): LiveData<Resource<EpisodeDetail>>
    fun locationDetailById(id: Int): LiveData<Resource<LocationDetail>>

}