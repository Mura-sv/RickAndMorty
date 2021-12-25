package com.example.rickandmorty.ui.character_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.core.network.Resource
import com.example.rickandmorty.domain.model.CharacterDetail
import com.example.rickandmorty.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val repo: RepositoryImpl) :
    ViewModel() {

    val progressbar = MutableLiveData<Boolean>()


    fun fetchCharacterById(id: Int): LiveData<Resource<CharacterDetail>> =
        repo.characterDetailById(id)

}