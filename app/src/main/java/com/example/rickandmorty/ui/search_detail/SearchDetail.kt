package com.example.rickandmorty.ui.search_detail

import androidx.navigation.fragment.navArgs
import coil.load
import com.example.rickandmorty.R
import com.example.rickandmorty.core.BaseFragment
import com.example.rickandmorty.databinding.FragmentSearchDetailBinding
import com.example.rickandmorty.extensions.dateConverter


class SearchDetail : BaseFragment<FragmentSearchDetailBinding>() {

    private val args by navArgs<SearchDetailArgs>()

    override fun inflateViewBinding(): FragmentSearchDetailBinding {
        return FragmentSearchDetailBinding.inflate(layoutInflater)
    }

    override fun setupUI() {
        setupDetailInfo()
    }

    override fun setupLiveData() {

    }


    private fun setupDetailInfo() {
        binding.name.text = args.currentSearchDetail.name
        binding.created.text = dateConverter(args.currentSearchDetail.created)

        args.currentSearchDetail.apply {
            if (image != null && status != null &&
                species != null && gender != null
            ) {
                binding.model.text = getString(R.string.character_text)
                binding.image.load(image)
                binding.description.text = species
                binding.additional.text = status
            }

            if (type != null && dimension != null) {
                binding.model.text = getString(R.string.location_text)
                binding.description.text = type
                binding.additional.text = dimension
            }

            if (episode != null && air_date != null) {
                binding.model.text = getString(R.string.episode_text)
                binding.description.text = episode
                binding.airDate.text = air_date
            }
        }


    }
}