package com.artemiod.challengeandroidkotlinalkemy.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artemiod.challengeandroidkotlinalkemy.R
import com.artemiod.challengeandroidkotlinalkemy.databinding.ItemListBinding
import com.artemiod.challengeandroidkotlinalkemy.domain.model.MovieItem
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ItemAdapter @Inject constructor() : ListAdapter<MovieItem, ItemAdapter.ViewHolder>(DiffCallBack) {

    // para hacer click
    lateinit var onItemClickListener: (MovieItem) -> Unit

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemListBinding.bind(view)

        fun bind(movie: MovieItem) {
            Picasso.get().load(movie.posterPath)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(binding.ivPoster)
            //binding.tvTitle.text = movie.title

            view.setOnClickListener {
                onItemClickListener(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        return holder.bind(item)
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<MovieItem>() {

        override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
            return oldItem == newItem
        }

    }

}