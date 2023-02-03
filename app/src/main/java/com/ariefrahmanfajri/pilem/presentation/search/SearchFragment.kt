package com.ariefrahmanfajri.pilem.presentation.search

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.ariefrahmanfajri.pilem.R
import com.ariefrahmanfajri.pilem.databinding.FragmentSearchBinding
import com.ariefrahmanfajri.pilem.di.ApiModule
import com.ariefrahmanfajri.pilem.di.DaggerAppComponent
import com.ariefrahmanfajri.pilem.util.Resource
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var searchViewModel: SearchViewModel
    lateinit var binding: FragmentSearchBinding
    lateinit var searchMovieAdapter: SearchMovieAdapter
    private val args by navArgs<SearchFragmentArgs>()
    private val nav by lazy { activity?.findNavController(R.id.containerFragment) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        DaggerAppComponent.builder()
            .apiModule(ApiModule(application = activity?.application as Application))
            .build()
            .inject(this)
        super.onViewCreated(view, savedInstanceState)

        searchMovie()
    }

    private fun searchMovie() {
        val query = args.query
        searchViewModel.searchMovie(query).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { movies ->
                        searchMovieAdapter = SearchMovieAdapter(movies) { movie ->
                            toMovieDetailFragment(movie.id.toString())
                        }
                        binding.rvSearchMovieResult.apply {
                            layoutManager = GridLayoutManager(context, 2)
                            adapter = searchMovieAdapter
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

    private fun toMovieDetailFragment(movieId: String) {
        val direction = SearchFragmentDirections.actionSearchFragmentToMovieDetailFragment(movieId)
        nav?.navigate(direction)
    }
}