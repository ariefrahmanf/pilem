package com.ariefrahmanfajri.pilem.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ariefrahmanfajri.pilem.databinding.SearchMovieItemBinding
import com.ariefrahmanfajri.pilem.databinding.SearchTitleItemBinding
import com.ariefrahmanfajri.pilem.domain.model.Result
import com.ariefrahmanfajri.pilem.util.loadImage

class SearchMovieAdapter(private val movies: List<Result>, val onClick: (Result) -> Unit?) :
    RecyclerView.Adapter<SearchMovieAdapter.SearchMovieTitleViewHolder>() {
    inner class SearchMovieTitleViewHolder(val binding: SearchMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Result) {
            binding.movie = movie
            binding.ivPoster.loadImage(movie.posterPath.toString(), binding.root.context)
            binding.ivPoster.setOnClickListener {
                onClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchMovieAdapter.SearchMovieTitleViewHolder {
        val view =
            SearchMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchMovieTitleViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SearchMovieAdapter.SearchMovieTitleViewHolder,
        position: Int
    ) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size
}