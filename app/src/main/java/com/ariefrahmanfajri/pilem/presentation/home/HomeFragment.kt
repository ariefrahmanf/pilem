package com.ariefrahmanfajri.pilem.presentation.home

import android.app.Application
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefrahmanfajri.pilem.R
import com.ariefrahmanfajri.pilem.databinding.FragmentHomeBinding
import com.ariefrahmanfajri.pilem.di.ApiModule
import com.ariefrahmanfajri.pilem.di.ApiModule_ProvideApplicationContextFactory.create
import com.ariefrahmanfajri.pilem.di.DaggerAppComponent
import com.ariefrahmanfajri.pilem.presentation.home.HomeFragment_MembersInjector.create
import com.ariefrahmanfajri.pilem.presentation.tvshow.detail.TvShowDetailFragment_MembersInjector.create
import com.ariefrahmanfajri.pilem.util.Resource
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class HomeFragment : Fragment() {
    @Inject
    lateinit var homeViewModel: HomeViewModel
    private lateinit var trendingMovieAdapter: TrendingMovieAdapter
    private lateinit var searchMovieTitleAdapter: SearchMovieTitleAdapter
    private lateinit var binding: FragmentHomeBinding
    private val homeNav by lazy { activity?.findNavController(R.id.containerFragment) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
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

        fetchNowPlaying()
        fetchSuggested()
        searchMovie()
        seeMore()
    }

    private fun seeMore() {
        binding.tvSeeMore.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToSeeMoreNowPlayingFragment()
            homeNav?.navigate(direction)
        }
    }

    private fun searchMovie() {
        binding.searchView.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!text.toString().isNullOrEmpty()) {
                    fetchMovieByQuery(text.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.searchView
            .editText
            .setOnEditorActionListener { v, actionId, event ->
                binding.searchBar.setText(binding.searchView.getText())
                binding.searchView.hide()
                if (binding.searchView.text.toString().isNotEmpty()) {
                    toSearchFragment(binding.searchView.text.toString())
                }
                false
            }
    }

    private fun toSearchFragment(query: String) {
        val direction = HomeFragmentDirections.actionHomeFragmentToSearchFragment(query)
        homeNav?.navigate(direction)
    }

    private fun fetchMovieByQuery(query: String) {
        homeViewModel.searchMovie(query).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { movies ->
                        searchMovieTitleAdapter = SearchMovieTitleAdapter(movies) { title ->
                            toSearchFragment(title)
                        }
                        binding.rvSearchMovieTitle.apply {
                            layoutManager = LinearLayoutManager(
                                binding.root.context,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            adapter = searchMovieTitleAdapter
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(binding.root.context, "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchNowPlaying() {
        homeViewModel.getNowPlaying().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { movies ->
                        trendingMovieAdapter = TrendingMovieAdapter(movies) { view, movie ->
                            toMovieDetailFragment(view, movie.id as Int)
                        }
                        binding.rvTrendingMovies.apply {
                            layoutManager = LinearLayoutManager(
                                binding.root.context,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            adapter = trendingMovieAdapter
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(binding.root.context, "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchSuggested() {
        homeViewModel.getPopular().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { movies ->
                        trendingMovieAdapter = TrendingMovieAdapter(movies) { imageView, movie ->
                            toMovieDetailFragment(imageView, movie.id as Int)
                        }
                        binding.rvSuggestedMovies.apply {
                            layoutManager = LinearLayoutManager(
                                binding.root.context,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            adapter = trendingMovieAdapter
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(binding.root.context, it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(binding.root.context, "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun toMovieDetailFragment(view: ImageView, movieId: Int) {
        val fragmentNavigatorExtras = FragmentNavigatorExtras(
            view to view.transitionName
        )
        val direction =
            HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(movieId.toString())
        homeNav?.navigate(direction, fragmentNavigatorExtras)
    }
}