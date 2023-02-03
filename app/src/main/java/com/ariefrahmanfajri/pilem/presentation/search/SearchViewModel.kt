package com.ariefrahmanfajri.pilem.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ariefrahmanfajri.pilem.domain.usecase.MovieUseCase
import javax.inject.Inject

class SearchViewModel @Inject constructor(val movieUseCase: MovieUseCase) : ViewModel() {
    fun searchMovie(query: String) =
        movieUseCase.searchMovie(query).asLiveData()
}