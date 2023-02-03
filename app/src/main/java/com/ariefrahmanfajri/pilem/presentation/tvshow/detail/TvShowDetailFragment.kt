package com.ariefrahmanfajri.pilem.presentation.tvshow.detail

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefrahmanfajri.pilem.R
import com.ariefrahmanfajri.pilem.databinding.FragmentTvShowDetailBinding
import com.ariefrahmanfajri.pilem.di.ApiModule
import com.ariefrahmanfajri.pilem.di.DaggerAppComponent
import com.ariefrahmanfajri.pilem.presentation.detail.CastAdapter
import com.ariefrahmanfajri.pilem.presentation.tvshow.TvShowViewModel
import com.ariefrahmanfajri.pilem.util.Resource
import com.ariefrahmanfajri.pilem.util.loadImage
import com.google.android.material.chip.Chip
import com.google.android.material.resources.TextAppearance
import com.google.android.material.transition.MaterialContainerTransform
import javax.inject.Inject


class TvShowDetailFragment : Fragment() {
    @Inject
    lateinit var tvShowViewModel: TvShowViewModel
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentTvShowDetailBinding.inflate(layoutInflater)
    }
    private val navArgs by navArgs<TvShowDetailFragmentArgs>()
    private val nav by lazy { activity?.findNavController(R.id.containerFragment) }
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var casterAdapter: CastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        DaggerAppComponent.builder()
            .apiModule(ApiModule(application = activity?.application as Application))
            .build()
            .inject(this)
        super.onViewCreated(view, savedInstanceState)

        fetchTvShowDetail()
        fetchTvReviews()
        fetchCaster()
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
        }
        ViewCompat.setTransitionName(binding.ivTvShowPoster, "image-${navArgs.tvId}")

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun fetchCaster() {
        tvShowViewModel.getCredit(navArgs.tvId.toString()).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    it.data?.let { credit ->
                        credit.cast?.let { casts ->
                            val filteredCast = casts.filter { it.knownForDepartment == "Acting" }
                            casterAdapter = CastAdapter(filteredCast)
                            binding.rvCaster.apply {
                                layoutManager = LinearLayoutManager(
                                    binding.root.context,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                                adapter = casterAdapter
                            }
                        }
                    }
                }
                is Resource.Error -> {

                }
            }
        }
    }

    private fun fetchTvReviews() {
        tvShowViewModel.getTvReviews(navArgs.tvId).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    it.data?.let {
                        it.results?.let { reviews ->
                            reviewAdapter = ReviewAdapter(reviews)

                            binding.rvReviews.apply {
                                layoutManager = LinearLayoutManager(
                                    binding.root.context,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                adapter = reviewAdapter
                            }
                        }
                    }
                }
                is Resource.Error -> {

                }

            }
        }
    }

    @SuppressLint("RestrictedApi", "UseCompatLoadingForDrawables")
    private fun fetchTvShowDetail() {
        tvShowViewModel.getTvShowDetail(navArgs.tvId).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    it.data?.let { detailTvShow ->
                        binding.tvShow = detailTvShow

                        val ratingFormat = String.format("%.1f", detailTvShow.voteAverage?.toFloat())
                        binding.rating = ratingFormat

                        val textAppearance = TextAppearance(
                            binding.root.context,
                            R.style.TextAppearancePilem_BodyMedium
                        )

                        val layoutParams = ActionBar.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                        )
                        layoutParams.setMargins(8, 8, 8, 8)

                        binding.cgGenre.removeAllViews()

                        detailTvShow.genres?.forEach {
                            val chip = Chip(binding.root.context)
                            chip.text = it.name
                            chip.layoutParams = layoutParams
                            chip.setTextAppearance(textAppearance)
                            binding.cgGenre.addView(chip)
                        }

                        binding.ivTvShowPoster.loadImage(
                            detailTvShow.posterPath.toString(),
                            binding.root.context
                        )

                        binding.ivTvShowPoster.setOnClickListener {
                            toVideoPlayerFragment(detailTvShow.id as Int)
                        }
                    }
                }
                is Resource.Error -> {

                }

            }
        }
    }

    private fun toVideoPlayerFragment(id: Int) {
        val direction =
            TvShowDetailFragmentDirections.actionTvShowDetailFragmentToPlayVideoFragment(id)
        nav?.navigate(direction)
    }
}