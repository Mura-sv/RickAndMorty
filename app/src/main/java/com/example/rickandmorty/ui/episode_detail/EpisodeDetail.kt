package com.example.rickandmorty.ui.episode_detail

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.rickandmorty.core.BaseFragment
import com.example.rickandmorty.core.network.Status
import com.example.rickandmorty.databinding.FragmentEpisodeDetailBinding
import com.example.rickandmorty.domain.model.EpisodeDetail
import com.example.rickandmorty.extensions.dateConverter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeDetail : BaseFragment<FragmentEpisodeDetailBinding>() {

    private val viewmodel: EpisodeDetailViewModel by viewModels()
    private val args by navArgs<EpisodeDetailArgs>()


    override fun inflateViewBinding(): FragmentEpisodeDetailBinding {
        return FragmentEpisodeDetailBinding.inflate(layoutInflater)
    }


    override fun setupUI() {
        viewmodel.progressbar.observe(viewLifecycleOwner) {
            binding.progressbar.isVisible = it
            binding.characterDetailLayout.isVisible = !it
        }
    }

    override fun setupLiveData() {

        viewmodel.fetchEpisodeDetailById(args.currentId).observe(viewLifecycleOwner) { response ->
            when (response.status) {

                Status.LOADING -> {
                    viewmodel.progressbar.value = true
                }
                Status.SUCCESS -> {
                    viewmodel.progressbar.value = false
                    response.data?.let { episodeInfoBinding(it) }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),
                        "couldn't load details ${response.message}",
                        Toast.LENGTH_LONG).show()
                }
            }
        }


    }

    private fun episodeInfoBinding(episode: EpisodeDetail) {
        binding.apply {
            name.text = episode.name
            number.text = episode.episode
            airDate.text = episode.air_date
            created.text = dateConverter(episode.created)
        }
    }


}