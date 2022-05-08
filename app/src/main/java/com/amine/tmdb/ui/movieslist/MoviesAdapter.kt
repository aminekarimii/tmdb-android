package com.amine.tmdb.ui.movieslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amine.tmdb.R
import com.amine.tmdb.data.local.model.MovieEntity
import com.amine.tmdb.databinding.MovieViewItemBinding


class MoviesAdapter :
    PagingDataAdapter<MovieEntity, MoviesAdapter.MovieViewHolder>(DIFF_UTIL) {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id && oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: MovieViewItemBinding = MovieViewItemBinding.bind(itemView)

        fun bind(movie: MovieEntity) {
            movie.let { binding.bindMovie(it) }
        }

        private fun MovieViewItemBinding.bindMovie(movie: MovieEntity) {
            movieName.text = movie.name
            popularityTextview.text = movie.popularity.toString()
            voteCountTextview.text = movie.voteCount.toString()
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val movieView =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_view_item, parent, false)
        return MovieViewHolder(movieView)
    }
}