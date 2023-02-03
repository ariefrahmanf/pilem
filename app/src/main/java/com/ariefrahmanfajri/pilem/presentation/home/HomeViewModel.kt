package com.ariefrahmanfajri.pilem.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ariefrahmanfajri.pilem.data.entity.ResultEntity
import com.ariefrahmanfajri.pilem.domain.model.Result
import com.ariefrahmanfajri.pilem.domain.usecase.MovieUseCase
import com.ariefrahmanfajri.pilem.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun getNowPlaying(): LiveData<Resource<List<Result>>> {
        return movieUseCase.getNowPlaying().asLiveData()
    }

    fun getPopular(): LiveData<Resource<List<Result>>> {
        return movieUseCase.getPopular().asLiveData()
    }

    fun searchMovie(query: String): LiveData<Resource<List<Result>>> {
        return movieUseCase.searchMovie(query).asLiveData()
    }

    private var moviePaginatedData: Flow<PagingData<Result>>? = null
    fun getMoviePaging(): Flow<PagingData<Result>> {
        return if (moviePaginatedData != null) {
            Log.e("IF", "TIdak null")
            moviePaginatedData as Flow<PagingData<Result>>
        } else {
            Log.e("ELSE", "null")

            moviePaginatedData = movieUseCase.getMoviePaging().cachedIn(viewModelScope)
            moviePaginatedData as Flow<PagingData<Result>>
        }
    }
}