package com.example.rickandmorty.ui.character

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.core.BaseFragment
import com.example.rickandmorty.databinding.FragmentCharacterBinding
import com.example.rickandmorty.paging.PagingLoadStateAdapter
import com.example.rickandmorty.ui.search.SearchFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterFragment : BaseFragment<FragmentCharacterBinding>() {

    private val viewmodel: CharacterViewModel by viewModels()
    private val pAdapter by lazy { CharacterPagingDataAdapter() }


    override fun inflateViewBinding(): FragmentCharacterBinding {
        return FragmentCharacterBinding.inflate(layoutInflater)
    }


    override fun setupUI() {
        setupRecycler()

    }

    override fun setupLiveData() {
        viewmodel.fetchCharactersByPage().observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                pAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }


    }


    private fun setupRecycler() {
        binding.recyclerview.adapter = pAdapter
        pAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW

        pAdapter.addLoadStateListener {
            binding.progressbar.isVisible =
                it.refresh is LoadState.Loading && pAdapter.itemCount == 0
        }
        pAdapter.withLoadStateHeaderAndFooter(
            header = PagingLoadStateAdapter(),
            footer = PagingLoadStateAdapter()
        )

        pAdapter.setOnClickListener { character ->
            val passId =
                CharacterFragmentDirections.actionCharacterFragmentToCharacterDetail(character.id)
            findNavController().navigate(passId)
        }

    }


}






