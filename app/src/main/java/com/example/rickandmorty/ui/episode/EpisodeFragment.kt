package com.example.rickandmorty.ui.episode

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.rickandmorty.R
import com.example.rickandmorty.core.BaseFragment
import com.example.rickandmorty.databinding.FragmentEpisodesBinding
import com.example.rickandmorty.ui.character.CharacterFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EpisodeFragment : BaseFragment<FragmentEpisodesBinding>() {

    private val viewmodel: EpisodeViewModel by viewModels()
    private val padapter by lazy { EpisodePagingDataAdapter() }


    override fun inflateViewBinding(): FragmentEpisodesBinding {
        return FragmentEpisodesBinding.inflate(layoutInflater)
    }

    override fun setupUI() {
        setupRecycler()


    }

    override fun setupLiveData() {


        viewmodel.fetchEpisodesByPage().observe(this) {
            lifecycleScope.launch {
                padapter.submitData(it)
            }
        }
    }


    private fun setupRecycler() {

        binding.recyclerview.adapter = padapter

        padapter.addLoadStateListener {
            binding.progressbar.isVisible =
                it.refresh is LoadState.Loading && padapter.itemCount == 0
        }


        padapter.setOnClickListener { episode ->
            val passId =
                EpisodeFragmentDirections.actionEpisodeFragmentToEpisodeDetail(episode.id)
            findNavController().navigate(passId)
        }
    }


}


