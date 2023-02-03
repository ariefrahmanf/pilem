package com.ariefrahmanfajri.pilem.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ariefrahmanfajri.pilem.domain.usecase.MovieUseCase
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(val movieUseCase: MovieUseCase): ViewModel() {

    fun getMovieDetail(movieId: String) =
        movieUseCase.getMovieDetail(movieId).asLiveData()

    fun getCredit(movieId: String) =
        movieUseCase.getCredit(movieId).asLiveData()

    fun getReviews(id: Int) =
        movieUseCase.getMovieReviews(id).asLiveData()

    fun getVideo(id: Int) =
        movieUseCase.getVideo(id).asLiveData()
}