package com.example.rickandmorty.ui.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.rickandmorty.core.network.Resource
import com.example.rickandmorty.domain.model.Locations
import com.example.rickandmorty.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {


    fun fetchLocationByPage() : LiveData<PagingData<Locations>> =
        repo.fetchLocationsByPage()
}