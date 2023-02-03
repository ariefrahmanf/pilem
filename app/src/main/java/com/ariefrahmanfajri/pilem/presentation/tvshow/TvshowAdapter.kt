package com.ariefrahmanfajri.pilem.presentation.tvshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ariefrahmanfajri.pilem.databinding.MovieTrendingItemBinding
import com.ariefrahmanfajri.pilem.databinding.SearchMovieItemBinding
import com.ariefrahmanfajri.pilem.domain.model.Result
import com.ariefrahmanfajri.pilem.domain.model.TvShow
import com.ariefrahmanfajri.pilem.util.loadImage

class TvshowAdapter(private val tvShows: List<TvShow>, val onClick: (ImageView, TvShow) -> Unit?) :
    RecyclerView.Adapter<TvshowAdapter.TvShowViewHolder>() {
    inner class TvShowViewHolder(val binding: MovieTrendingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShow) {
            binding.apply {
                ivPoster.loadImage(tvShow.posterPath.toString(), binding.root.context)
                ivPoster.setOnClickListener {
                    onClick.invoke(binding.ivPoster, tvShow)
                }
                binding.ivPoster.transitionName = "image-${tvShow.id}"
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvshowAdapter.TvShowViewHolder {
        val view =
            MovieTrendingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TvshowAdapter.TvShowViewHolder,
        position: Int
    ) {
        holder.bind(tvShows[position])
    }

    override fun getItemCount() = tvShows.size
}