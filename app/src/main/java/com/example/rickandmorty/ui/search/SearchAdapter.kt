package com.example.rickandmorty.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.RecyclerviewItemBinding
import com.example.rickandmorty.domain.model.Characters
import com.example.rickandmorty.domain.model.Search
import com.example.rickandmorty.extensions.dateConverter
import com.example.rickandmorty.ui.search.SearchAdapter.SearchViewHolder

class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {

    private var onItemClickListener: ((Search) -> Unit)? = null

    fun setOnClickListener(listener: (Search) -> Unit) {
        onItemClickListener = listener
    }


//    var searchList: List<Search> = ArrayList()
//    fun updateList(list: List<Search>) {
//        this.searchList = list
//  }'


    val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recyclerview_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val search = differ.currentList[position]
        holder.onbind(search)
    }

    override fun getItemCount(): Int = differ.currentList.size


    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = RecyclerviewItemBinding.bind(itemView)

        fun onbind(searchModel: Search?) {
            binding.apply {
                title.text = searchModel!!.name
                firstappearance.text = dateConverter(searchModel.created)
                if (searchModel.image != null) {
                    image.visibility = VISIBLE
                    image.load(searchModel.image)
                } else {
                    image.visibility = GONE
                }
                status.visibility = GONE
                species.visibility = GONE
                lastlocationText.visibility = GONE



                root.setOnClickListener {
                    onItemClickListener?.let { click ->
                        click(searchModel)
                    }
                }
            }


        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Search>() {
            override fun areItemsTheSame(
                oldItem: Search,
                newItem: Search,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Search,
                newItem: Search,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}