package com.ariefrahmanfajri.pilem.domain.repository

import androidx.paging.PagingData
import com.ariefrahmanfajri.pilem.data.entity.ResultEntity
import com.ariefrahmanfajri.pilem.domain.model.*
import com.ariefrahmanfajri.pilem.util.Resource
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getNowPlaying(): Flow<Resource<List<Result>>>
    fun getPopular(): Flow<Resource<List<Result>>>
    fun searchMovie(query: String): Flow<Resource<List<Result>>>
    fun getMovieDetail(movieId: String): Flow<Resource<MovieDetail>>
    fun getCredit(movieId: String): Flow<Resource<Credit>>
    fun getPopularTvShow(): Flow<Resource<List<TvShow>>>
    fun getTopRatedTvShow(): Flow<Resource<List<TvShow>>>
    fun getTvShowDetail(tvId: Int): Flow<Resource<DetailTvShow>>
    fun getVideo(id: Int): Flow<Resource<Video>>
    fun getTvReviews(id: Int): Flow<Resource<Review>>
    fun getMovieReviews(id: Int): Flow<Resource<Review>>
    fun getMoviePaging(): Flow<PagingData<Result>>
}