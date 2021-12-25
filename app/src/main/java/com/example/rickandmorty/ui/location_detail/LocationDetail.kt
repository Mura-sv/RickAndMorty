package com.example.rickandmorty.ui.location_detail

import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.rickandmorty.core.BaseFragment
import com.example.rickandmorty.core.network.Status
import com.example.rickandmorty.databinding.FragmentLocationDetailBinding
import com.example.rickandmorty.domain.model.LocationDetail
import com.example.rickandmorty.extensions.dateConverter
import dagger.hilt.android.AndroidEntryPoint
import hilt_aggregated_deps._dagger_hilt_android_internal_lifecycle_DefaultViewModelFactories_ActivityEntryPoint

@AndroidEntryPoint
class LocationDetail : BaseFragment<FragmentLocationDetailBinding>() {

    private val viewmodel: LocationDetailViewModel by viewModels()
    private val args by navArgs<LocationDetailArgs>()

    override fun inflateViewBinding(): FragmentLocationDetailBinding {
        return FragmentLocationDetailBinding.inflate(layoutInflater)
    }

    override fun setupUI() {
        viewmodel.progressbar.observe(viewLifecycleOwner) {
            binding.progressbar.isVisible = it
            binding.locationDetailLayout.isVisible = !it
        }

    }

    override fun setupLiveData() {

        viewmodel.locationDetailById(args.currentId).observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.LOADING -> {
                    viewmodel.progressbar.value = true
                }
                Status.SUCCESS -> {
                    viewmodel.progressbar.value = false
                    response.data?.let { locationInfoBinding(it) }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun locationInfoBinding(location: LocationDetail) {
        binding.apply {
            name.text = location.name
            dimension.text = location.dimension
            type.text = location.type
            created.text = dateConverter(location.created)
        }
    }
}

