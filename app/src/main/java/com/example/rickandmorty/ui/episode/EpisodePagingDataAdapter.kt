package com.example.rickandmorty.ui.episode

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.RecyclerviewItemBinding
import com.example.rickandmorty.domain.model.Episodes
import com.example.rickandmorty.ui.episode.EpisodePagingDataAdapter.*

class EpisodePagingDataAdapter : PagingDataAdapter<Episodes, EpisodeViewHolder>(diffCallback) {


    private var onItemClickListener: ((Episodes) -> Unit)? = null

    fun setOnClickListener(listener: (Episodes) -> Unit) {
        onItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item, parent, false
        ))
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }


    inner class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = RecyclerviewItemBinding.bind(itemView)

        fun onBind(episode: Episodes?) {
            binding.apply {
                title.text = episode!!.name
                status.text = episode.episode
                image.visibility = View.GONE
                species.visibility = View.GONE
                lastlocationText.visibility = View.GONE
                firstappearance.visibility = View.GONE


                root.setOnClickListener {
                    onItemClickListener?.let { click ->
                        click(episode)
                    }
                }
            }
        }


    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Episodes>() {
            override fun areItemsTheSame(oldItem: Episodes, newItem: Episodes): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Episodes, newItem: Episodes): Boolean {
                return oldItem == newItem
            }
        }
    }
}