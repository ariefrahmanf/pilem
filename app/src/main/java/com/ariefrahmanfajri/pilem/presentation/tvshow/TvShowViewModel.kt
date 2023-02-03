package com.ariefrahmanfajri.pilem.presentation.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ariefrahmanfajri.pilem.domain.usecase.MovieUseCase
import javax.inject.Inject

class TvShowViewModel @Inject constructor(val useCase: MovieUseCase): ViewModel() {
    fun getPopularTvShow() = useCase.getPopularTvShow().asLiveData()
    fun getTopRatedTvShow() = useCase.getTopRatedTvShow().asLiveData()
    fun getTvShowDetail(tvId: Int) = useCase.getTvShowDetail(tvId).asLiveData()
    fun getVideo(id: Int) = useCase.getVideo(id).asLiveData()
    fun getTvReviews(id: Int) = useCase.getTvReviews(id).asLiveData()
    fun getCredit(id: String) = useCase.getCredit(id).asLiveData()
}