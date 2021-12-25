package com.example.rickandmorty.ui.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.rickandmorty.core.network.Resource
import com.example.rickandmorty.data.model.characters.CharacterDto
import com.example.rickandmorty.data.model.characters.CharactersModel
import com.example.rickandmorty.domain.model.CharacterDetail
import com.example.rickandmorty.domain.model.Characters
import com.example.rickandmorty.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {


    fun fetchCharactersByPage(): LiveData<PagingData<Characters>> =
        repo.fetchCharactersByPage()



}