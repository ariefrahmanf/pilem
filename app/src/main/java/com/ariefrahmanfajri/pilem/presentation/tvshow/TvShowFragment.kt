package com.ariefrahmanfajri.pilem.presentation.tvshow

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefrahmanfajri.pilem.R
import com.ariefrahmanfajri.pilem.databinding.FragmentTvShowBinding
import com.ariefrahmanfajri.pilem.di.ApiModule
import com.ariefrahmanfajri.pilem.di.DaggerAppComponent
import com.ariefrahmanfajri.pilem.util.Resource
import com.google.android.material.transition.MaterialElevationScale
import javax.inject.Inject

class TvShowFragment : Fragment() {

    private lateinit var binding: FragmentTvShowBinding

    @Inject
    lateinit var tvShowViewModel: TvShowViewModel
    lateinit var tvShowAdapter: TvshowAdapter
    private val tvShowNav by lazy { activity?.findNavController(R.id.containerFragment) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        DaggerAppComponent.builder()
            .apiModule(ApiModule(application = activity?.application as Application))
            .build()
            .inject(this)
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
        exitTransition = MaterialElevationScale(false)
        reenterTransition = MaterialElevationScale(true)

        fetchPopularTvShow()
        fetchTopRatedTvShow()
    }

    private fun fetchPopularTvShow() {
        tvShowViewModel.getPopularTvShow().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { tvShows ->
                        tvShowAdapter = TvshowAdapter(tvShows) { view, tvShow ->
                            toTvShowDetailFragment(view, tvShow.id)
                        }
                        binding.rvPopularTvShow.apply {
                            layoutManager = LinearLayoutManager(
                                binding.root.context,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            adapter = tvShowAdapter
                        }
                    }

                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }
    }

    private fun toTvShowDetailFragment(view: ImageView, id: Int?) {
        val fragmentNavigatorExtras = FragmentNavigatorExtras(view to view.transitionName)
        id?.let {
            val direction = TvShowFragmentDirections.actionTvShowFragmentToTvShowDetailFragment(it)
            tvShowNav?.navigate(direction, fragmentNavigatorExtras)
        }
    }

    private fun fetchTopRatedTvShow() {
        tvShowViewModel.getTopRatedTvShow().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { tvShows ->
                        tvShowAdapter = TvshowAdapter(tvShows) { view, tvShow ->
                            toTvShowDetailFragment(view, tvShow.id)
                        }
                        binding.rvTopRatedTvShow.apply {
                            layoutManager = LinearLayoutManager(
                                binding.root.context,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            adapter = tvShowAdapter
                        }
                    }

                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }
    }
}