package com.example.rickandmorty.ui.character

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.RecyclerviewItemBinding
import com.example.rickandmorty.domain.model.Characters
import com.example.rickandmorty.ui.character.CharacterPagingDataAdapter.PagingViewHolder
import javax.inject.Inject

class CharacterPagingDataAdapter : PagingDataAdapter<Characters, PagingViewHolder>(diffCallback) {


    private var onItemClickListener: ((Characters) -> Unit)? = null

    fun setOnClickListener(listener: (Characters) -> Unit) {
        onItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        return PagingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recyclerview_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        holder.onBind(getItem(position))

    }

    inner class PagingViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        private val binding = RecyclerviewItemBinding.bind(itemView)

        fun onBind(character: Characters?) {
            binding.apply {
                title.text = character?.name
                image.load(character!!.image) {
                    crossfade(true)
                    crossfade(50)
                }
                status.text = character.status
                species.text = character.species
                firstappearance.text = character.origin

                root.setOnClickListener {
                    onItemClickListener?.let { click ->
                        click(character)
                    }
                }
            }

        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Characters>() {
            override fun areItemsTheSame(
                oldItem: Characters,
                newItem: Characters,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Characters,
                newItem: Characters,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}