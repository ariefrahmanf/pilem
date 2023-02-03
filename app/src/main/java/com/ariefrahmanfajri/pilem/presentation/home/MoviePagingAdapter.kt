package com.ariefrahmanfajri.pilem.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ariefrahmanfajri.pilem.data.entity.ResultEntity
import com.ariefrahmanfajri.pilem.databinding.MovieTrendingItemBinding
import com.ariefrahmanfajri.pilem.domain.model.Result
import com.ariefrahmanfajri.pilem.util.loadImage

class MoviePagingAdapter(
    val onClick: (view: ImageView, Result) -> Unit?
) :
    PagingDataAdapter<Result, MoviePagingAdapter.TrendingMovieViewHolder>(diffUtil) {
    inner class TrendingMovieViewHolder(val binding: MovieTrendingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Result) {
            binding.ivPoster.loadImage(movie.posterPath.toString(), binding.root.context)
            binding.constraintLayoutRoot.setOnClickListener {
                onClick(binding.ivPoster, movie)
            }

            ViewCompat.setTransitionName(binding.ivPoster, "image-${movie.id}")
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviePagingAdapter.TrendingMovieViewHolder {
        val view =
            MovieTrendingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendingMovieViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MoviePagingAdapter.TrendingMovieViewHolder,
        position: Int
    ) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

        }
    }
}