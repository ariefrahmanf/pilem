package com.ariefrahmanfajri.pilem.presentation.home.nowplaying

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.LoadState
import androidx.paging.PagingSource
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ariefrahmanfajri.pilem.R
import com.ariefrahmanfajri.pilem.databinding.FragmentSeeMoreNowPlayingBinding
import com.ariefrahmanfajri.pilem.di.ApiModule
import com.ariefrahmanfajri.pilem.di.DaggerAppComponent
import com.ariefrahmanfajri.pilem.presentation.home.HomeViewModel
import com.ariefrahmanfajri.pilem.presentation.home.MoviePagingAdapter
import com.ariefrahmanfajri.pilem.util.PagingLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject


class SeeMoreNowPlayingFragment : Fragment() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentSeeMoreNowPlayingBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var homeViewModel: HomeViewModel
    private val moviePagingAdapter: MoviePagingAdapter by lazy {
        MoviePagingAdapter { view, result ->
            toMovieDetailFragment(view, result.id as Int)
        }
    }
    private val seeMoreNav by lazy { activity?.findNavController(R.id.containerFragment) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        DaggerAppComponent.builder()
            .apiModule(ApiModule(activity?.application as Application))
            .build()
            .inject(this)
        super.onViewCreated(view, savedInstanceState)

        fetchMoviePaging()
        binding.rvNowPlayingMovie.apply {
            layoutManager =
                GridLayoutManager(binding.root.context, 2)
            adapter = moviePagingAdapter
            adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    private fun fetchMoviePaging() {
        with(moviePagingAdapter) {
            binding.rvNowPlayingMovie.adapter = withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(this),
                footer = PagingLoadStateAdapter(this)
            )
            with(homeViewModel) {
                viewLifecycleOwner.lifecycleScope.launch {
                    getMoviePaging().collectLatest {
                        moviePagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                    }
                }
                /*viewLifecycleOwner.lifecycleScope.launch {
                    loadStateFlow.collectLatest {

                    }
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    loadStateFlow.distinctUntilChangedBy { it.refresh }
                        .filter { it.refresh is LoadState.NotLoading }
                        .collect { binding.rvNowPlayingMovie.scrollToPosition(0) }
                }*/
            }
        }
    }

    private fun toMovieDetailFragment(view: ImageView, movieId: Int) {
        val fragmentNavigatorExtras = FragmentNavigatorExtras(
            view to view.transitionName
        )
        val direction =
            SeeMoreNowPlayingFragmentDirections.actionSeeMoreNowPlayingFragmentToMovieDetailFragment(
                movieId.toString()
            )
        seeMoreNav?.navigate(direction, fragmentNavigatorExtras)
    }
}