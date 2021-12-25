package com.example.rickandmorty.ui.location_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.rickandmorty.core.network.Resource
import com.example.rickandmorty.domain.model.LocationDetail
import com.example.rickandmorty.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class LocationDetailViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {

    val progressbar = MutableLiveData<Boolean>()

    fun locationDetailById(id: Int): LiveData<Resource<LocationDetail>> =
        repo.locationDetailById(id)


}