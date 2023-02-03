package com.ariefrahmanfajri.pilem.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ariefrahmanfajri.pilem.databinding.SearchTitleItemBinding
import com.ariefrahmanfajri.pilem.domain.model.Result

class SearchMovieTitleAdapter(private val movies: List<Result>, val onClick: (title: String) -> Unit?) :
    RecyclerView.Adapter<SearchMovieTitleAdapter.SearchMovieTitleViewHolder>() {
    inner class SearchMovieTitleViewHolder(val binding: SearchTitleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Result) {
            binding.tvTitle.text = movie.title
            binding.tvTitle.setOnClickListener {
                onClick.invoke(movie.title.toString())
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchMovieTitleAdapter.SearchMovieTitleViewHolder {
        val view =
            SearchTitleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchMovieTitleViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SearchMovieTitleAdapter.SearchMovieTitleViewHolder,
        position: Int
    ) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size
}