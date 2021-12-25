package com.example.rickandmorty.ui.character_detail

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.rickandmorty.core.BaseFragment
import com.example.rickandmorty.core.network.Status
import com.example.rickandmorty.data.model.characters.CharacterDto
import com.example.rickandmorty.databinding.FragmentCharacterDetailBinding
import com.example.rickandmorty.domain.model.CharacterDetail
import com.example.rickandmorty.ui.character.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetail : BaseFragment<FragmentCharacterDetailBinding>() {

    private val args by navArgs<CharacterDetailArgs>()

    private val viewmodel: CharacterDetailViewModel by viewModels()

    override fun inflateViewBinding(): FragmentCharacterDetailBinding {
        return FragmentCharacterDetailBinding.inflate(layoutInflater)
    }

    override fun setupUI() {
        viewmodel.progressbar.observe(viewLifecycleOwner) {
            binding.characterDetailLayout.isVisible = !it
            binding.progressbar.isVisible = it
        }

    }


    override fun setupLiveData() {

        viewmodel.fetchCharacterById(args.currentId).observe(viewLifecycleOwner) { response ->
                when (response.status) {
                    Status.LOADING -> {
                        viewmodel.progressbar.value = true
                    }
                    Status.ERROR -> {
                        viewmodel.progressbar.value = false
                    }
                    Status.SUCCESS -> {
                        viewmodel.progressbar.value = false
                        response.data?.let { character ->
                            characterInfoBinding(character)
                        }
                    }
                }
            }


    }


    private fun characterInfoBinding(character: CharacterDetail) {
        binding.name.text = character.name
        binding.characterImage.load(character.image)
        binding.statusText.text = character.status
        binding.speciesText.text = character.species
        binding.originText.text = character.origin
        binding.genderText.text = character.gender
    }

}