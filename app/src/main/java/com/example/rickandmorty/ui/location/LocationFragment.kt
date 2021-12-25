package com.example.rickandmorty.ui.location

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.rickandmorty.R
import com.example.rickandmorty.core.BaseFragment
import com.example.rickandmorty.databinding.FragmentLocationBinding
import com.example.rickandmorty.domain.model.LocationDetail
import com.example.rickandmorty.ui.character.CharacterFragment
import com.example.rickandmorty.ui.location_detail.LocationDetailArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding>() {

    private val viewmodel: LocationViewModel by viewModels()
    private val adapter: LocationPagingDataAdapter by lazy { LocationPagingDataAdapter() }

    override fun inflateViewBinding(): FragmentLocationBinding {
        return FragmentLocationBinding.inflate(layoutInflater)
    }

    fun newInstance(): LocationFragment {

        return LocationFragment()
    }

    override fun setupUI() {
        setupRecycler()

    }

    override fun setupLiveData() {


        viewmodel.fetchLocationByPage().observe(this) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }

    }

    private fun setupRecycler() {
        adapter.addLoadStateListener {
            binding.progressbar.isVisible =
                it.refresh is LoadState.Loading && adapter.itemCount == 0
        }
        binding.recyclerview.adapter = this@LocationFragment.adapter

        adapter.setOnClickListener { location ->
            val passId =
                LocationFragmentDirections.actionLocationFragmentToLocationDetail2(location.id)
            findNavController().navigate(passId)
        }
    }

}