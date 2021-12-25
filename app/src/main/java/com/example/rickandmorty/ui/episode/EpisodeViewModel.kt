package com.example.rickandmorty.ui.episode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.rickandmorty.core.network.Resource
import com.example.rickandmorty.domain.model.Episodes
import com.example.rickandmorty.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {


    fun fetchEpisodesByPage(): LiveData<PagingData<Episodes>> =
        repo.fetchEpisodesByPage()


}