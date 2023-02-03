package com.ariefrahmanfajri.pilem.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.ariefrahmanfajri.pilem.databinding.MovieTrendingItemBinding
import com.ariefrahmanfajri.pilem.domain.model.Result
import com.ariefrahmanfajri.pilem.util.loadImage

class TrendingMovieAdapter(
    private val movies: List<Result>,
    val onClick: (view: ImageView, Result) -> Unit?
) :
    RecyclerView.Adapter<TrendingMovieAdapter.TrendingMovieViewHolder>() {
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
    ): TrendingMovieAdapter.TrendingMovieViewHolder {
        val view =
            MovieTrendingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendingMovieViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TrendingMovieAdapter.TrendingMovieViewHolder,
        position: Int
    ) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size
}