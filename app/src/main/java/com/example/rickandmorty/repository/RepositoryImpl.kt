package com.example.rickandmorty.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.rickandmorty.core.Constant.NETWORK_PAGE_SIZE
import com.example.rickandmorty.core.network.Resource
import com.example.rickandmorty.data.*
import com.example.rickandmorty.data.paging_data_source.CharactersPagingDataSource
import com.example.rickandmorty.data.paging_data_source.EpisodePagingDataSource
import com.example.rickandmorty.data.paging_data_source.LocationPagingDataSource
import com.example.rickandmorty.data.paging_data_source.SearchPagingDataSource
import com.example.rickandmorty.domain.Repository
import com.example.rickandmorty.domain.model.*
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class RepositoryImpl @Inject constructor(private val api: ApiService) : Repository {

// search by name for all queries

    fun searchByName(name: String): LiveData<Resource<List<Search>>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            val characters = api.fetchCharactersByName(name)
            val locations = api.fetchAllLocationByName(name)
            val episodes = api.fetchAllEpisodeByName(name)

            emit(
                if (characters.isSuccessful && characters.body() != null ||
                    locations.isSuccessful && locations.body() != null ||
                    episodes.isSuccessful && episodes.body() != null
                ) {
                    val mappedCharacters =
                        characters.body()?.results?.map { it.toSearchModel() }
                    val mappedLocations =
                        locations.body()?.results?.map { it.toSearchModel() }
                    val mappedEpisodes =
                        episodes.body()?.results?.map { it.toSearchModel() }
                    val list: MutableList<Search> = mutableListOf()
                    mappedCharacters?.let { list.addAll(it) }
                    mappedLocations?.let { list.addAll(it) }
                    mappedEpisodes?.let { list.addAll(it) }

                    list.sortWith(
                        compareBy { name }
                    )

                    Resource.success(list)
                } else Resource.error(characters.message(), null, characters.code())
            )
        }


    // detail queries by id

    override fun characterDetailById(id: Int): LiveData<Resource<CharacterDetail>> =
        liveData(Dispatchers.IO) {

            emit(Resource.loading(null))

            val response = api.fetchCharacterById(id)

            emit(
                if (response.isSuccessful && response.body() != null) {
                    val characterDetail = response.body()
                    Resource.success(characterDetail?.toCharacterDetail())
                } else {
                    Resource.error(response.message(), null, response.code())
                })

        }

    override fun episodeByDetailById(id: Int): LiveData<Resource<EpisodeDetail>> =
        liveData(Dispatchers.IO) {

            emit(Resource.loading(null))

            val response = api.fetchEpisodeById(id)

            emit(if (response.isSuccessful && response.body() != null) {
                val episodeDetail = response.body()!!.toEpisodeDetail()
                Resource.success(episodeDetail)
            } else {
                Resource.error(response.message(), null, response.code())
            }
            )

        }

    override fun locationDetailById(id: Int): LiveData<Resource<LocationDetail>> =
        liveData(Dispatchers.IO) {

            emit(Resource.loading(null))

            val response = api.fetchLocationById(id)

            emit(
                if (response.isSuccessful && response.body() != null) {
                    val locationDetail = response.body()?.toLocationDetail()
                    Resource.success(locationDetail)
                } else {
                    Resource.error(response.message(), null, response.code())
                }


            )
        }


    // Paginated requests


    override fun fetchCharactersByPage(): LiveData<PagingData<Characters>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                CharactersPagingDataSource(api)
            }
        ).liveData
    }

    override fun fetchEpisodesByPage(): LiveData<PagingData<Episodes>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                EpisodePagingDataSource(api)
            }
        ).liveData
    }

    override fun fetchLocationsByPage(): LiveData<PagingData<Locations>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                LocationPagingDataSource(api)
            }
        ).liveData
    }

    override fun fetchCharactersByNameAndPage(name: String): LiveData<PagingData<Characters>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                SearchPagingDataSource(name, api)
            }
        ).liveData
    }


}