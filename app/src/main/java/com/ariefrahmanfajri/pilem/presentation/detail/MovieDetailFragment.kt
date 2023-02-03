package com.ariefrahmanfajri.pilem.presentation.detail

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefrahmanfajri.pilem.R
import com.ariefrahmanfajri.pilem.databinding.FragmentMovieDetailBinding
import com.ariefrahmanfajri.pilem.di.ApiModule
import com.ariefrahmanfajri.pilem.di.DaggerAppComponent
import com.ariefrahmanfajri.pilem.presentation.tvshow.detail.ReviewAdapter
import com.ariefrahmanfajri.pilem.util.Resource
import com.ariefrahmanfajri.pilem.util.loadImage
import com.google.android.material.chip.Chip
import com.google.android.material.resources.TextAppearance
import com.google.android.material.transition.MaterialContainerTransform
import javax.inject.Inject

class MovieDetailFragment : Fragment() {

    @Inject
    lateinit var movieDetailViewModel: MovieDetailViewModel
    private lateinit var binding: FragmentMovieDetailBinding
    private val args by navArgs<MovieDetailFragmentArgs>()
    private lateinit var casterAdapter: CastAdapter
    private lateinit var reviewAdapter: ReviewAdapter
    private val nav by lazy { activity?.findNavController(R.id.containerFragment) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        DaggerAppComponent.builder()
            .apiModule(ApiModule(application = activity?.application as Application))
            .build()
            .inject(this)
        super.onViewCreated(view, savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
        }
        ViewCompat.setTransitionName(binding.ivMoviePoster, "image-${args.movieId}")

        fetchMovieDetail()
        fetchCredit()
        fetchReviews()
    }

    private fun fetchReviews() {
        movieDetailViewModel.getReviews(args.movieId.toInt()).observe(viewLifecycleOwner) {
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

    private fun fetchCredit() {
        movieDetailViewModel.getCredit(args.movieId).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.apply {
                        it.data?.let { credit ->
                            credit.cast?.let { casts ->
                                val filteredCasts =
                                    casts.filter { it.knownForDepartment == "Acting" }
                                casterAdapter = CastAdapter(filteredCasts)
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

                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun fetchMovieDetail() {
        movieDetailViewModel.getMovieDetail(args.movieId).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.apply {
                        it.data?.let {
                            movie = it
                            ivMoviePoster.loadImage(it.posterPath.toString(), binding.root.context)

                            binding.ivMoviePoster.setOnClickListener {
                                toPlayVideoFragment(it.id)
                            }

                            val ratingFormat = String.format("%.1f", it.voteAverage?.toFloat())
                            rating = ratingFormat

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

                            it.genres?.forEach {
                                val chip = Chip(binding.root.context)
                                chip.text = it.name
                                chip.layoutParams = layoutParams
                                chip.setTextAppearance(textAppearance)
                                binding.cgGenre.addView(chip)
                            }
                        }
                    }

                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun toPlayVideoFragment(id: Int) {
        val direction =
            MovieDetailFragmentDirections.actionMovieDetailFragmentToPlayVideoFragment(id)
        nav?.navigate(direction)
    }

}