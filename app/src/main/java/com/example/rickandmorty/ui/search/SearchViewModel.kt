package com.example.rickandmorty.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.core.network.Resource
import com.example.rickandmorty.domain.model.Search
import com.example.rickandmorty.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {

    val progressBar = MutableLiveData(false)


    fun searchByName(name: String): LiveData<Resource<List<Search>>> =
        repo.searchByName(name)


}