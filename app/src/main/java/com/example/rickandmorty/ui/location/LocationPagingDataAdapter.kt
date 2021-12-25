package com.example.rickandmorty.ui.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.characters.Location
import com.example.rickandmorty.databinding.RecyclerviewItemBinding
import com.example.rickandmorty.domain.model.Episodes
import com.example.rickandmorty.domain.model.Locations
import com.example.rickandmorty.ui.location.LocationPagingDataAdapter.*

class LocationPagingDataAdapter : PagingDataAdapter<Locations, LocationViewHolder>(diffCallback) {

        private var onItemClickListener: ((Locations) -> Unit)? = null

        fun setOnClickListener(listener: (Locations) -> Unit) {
            onItemClickListener = listener
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_item, parent, false
        ))
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.onBind(getItem(position))


    }

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = RecyclerviewItemBinding.bind(itemView)

        fun onBind(location: Locations?) {
            binding.apply {
                title.text = location!!.name
                status.text = location.locationType
                firstappearance.visibility = View.GONE
                species.visibility = View.GONE
                lastlocationText.visibility = View.GONE
                image.visibility = View.GONE

                root.setOnClickListener {
                    onItemClickListener?.let { click ->
                        click(location)
                    }
                }

            }

        }
    }


    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Locations>() {
            override fun areItemsTheSame(oldItem: Locations, newItem: Locations): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Locations, newItem: Locations): Boolean {
                return oldItem == newItem
            }
        }
    }
}