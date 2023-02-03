package com.ariefrahmanfajri.pilem.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ariefrahmanfajri.pilem.databinding.CastItemBinding
import com.ariefrahmanfajri.pilem.domain.model.Cast
import com.ariefrahmanfajri.pilem.util.loadCircularImage

class CastAdapter(private val casts: List<Cast>) :
    RecyclerView.Adapter<CastAdapter.CastViewHolder>() {
    class CastViewHolder(val binding: CastItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast) {
            cast.profilePath?.let {
                binding.ivCaster.loadCircularImage(cast.profilePath, binding.root.context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastAdapter.CastViewHolder {
        val view = CastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastAdapter.CastViewHolder, position: Int) {
        holder.bind(casts[position])
    }

    override fun getItemCount() = casts.size
}