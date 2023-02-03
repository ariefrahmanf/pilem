package com.ariefrahmanfajri.pilem.data.repository

import androidx.paging.*
import com.ariefrahmanfajri.pilem.data.model.CreditModel
import com.ariefrahmanfajri.pilem.data.model.MovieDetailModel
import com.ariefrahmanfajri.pilem.data.model.ResultModel
import com.ariefrahmanfajri.pilem.data.pagingsource.MovieRemoteMediator
import com.ariefrahmanfajri.pilem.data.reqres.local.LocalDataSource
import com.ariefrahmanfajri.pilem.data.reqres.network.MovieApi
import com.ariefrahmanfajri.pilem.data.reqres.network.MovieApiClient
import com.ariefrahmanfajri.pilem.domain.model.*
import com.ariefrahmanfajri.pilem.domain.repository.IMovieRepository
import com.ariefrahmanfajri.pilem.util.ApiResponse
import com.ariefrahmanfajri.pilem.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepository @Inject constructor(
    val apiClient: MovieApiClient,
    val movieApi: MovieApi,
    val localDataSource: LocalDataSource
) : IMovieRepository {

    override fun getNowPlaying(): Flow<Resource<List<Result>>> {
        val result = flow<Resource<List<Result>>> {
            emit(Resource.Loading())
            val response: Flow<ApiResponse<List<ResultModel>>> = movieApi.getNowPlaying()
            when (val apiResponse = response.first()) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    emit(Resource.Success(data.map { it.toResult() }))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.message))
                }
                else -> {}
            }
        }
        return result
    }

    override fun getPopular(): Flow<Resource<List<Result>>> {
        val result = flow<Resource<List<Result>>> {
            val response: Flow<ApiResponse<List<ResultModel>>> = movieApi.getPopular()
            when (val apiResponse = response.first()) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    emit(Resource.Success(data.map { it.toResult() }))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.message))
                }
                else -> {}
            }
        }
        return result
    }

    override fun searchMovie(query: String): Flow<Resource<List<Result>>> {
        val result = flow<Resource<List<Result>>> {
            val response: Flow<ApiResponse<List<ResultModel>>> = movieApi.searchMovie(query)
            when (val apiResponse = response.first()) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    emit(Resource.Success(data.map { it.toResult() }))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.message))
                }
                else -> {}
            }
        }
        return result
    }

    override fun getMovieDetail(movieId: String): Flow<Resource<MovieDetail>> {
        val result = flow<Resource<MovieDetail>> {
            val response: Flow<ApiResponse<MovieDetailModel>> = movieApi.getMovieDetail(movieId)
            when (val apiResponse = response.first()) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    emit(Resource.Success(data.toMovieDetail()))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.message))
                }
                else -> {}
            }
        }
        return result
    }

    override fun getCredit(movieId: String): Flow<Resource<Credit>> {
        val result = flow<Resource<Credit>> {
            val response: Flow<ApiResponse<CreditModel>> = movieApi.getCredit(movieId)
            when (val apiResponse = response.first()) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    emit(Resource.Success(data.toCredit()))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.message))
                }
                else -> {}
            }
        }
        return result
    }

    override fun getPopularTvShow(): Flow<Resource<List<TvShow>>> {
        val result = flow {
            val response = movieApi.getPopularTvShow()
            when (val apiResponse = response.first()) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    emit(Resource.Success(data.map { it.toTvShow() }))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.message))
                }
                else -> {}
            }
        }
        return result
    }

    override fun getTopRatedTvShow(): Flow<Resource<List<TvShow>>> {
        val result = flow {
            val response = movieApi.getTopRatedTvShow()
            when (val apiResponse = response.first()) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    emit(Resource.Success(data.map { it.toTvShow() }))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.message))
                }
                else -> {}
            }
        }
        return result
    }

    override fun getTvShowDetail(tvId: Int): Flow<Resource<DetailTvShow>> {
        val result = flow {
            val response = movieApi.getTvShowDetail(tvId)
            when (val apiResponse = response.first()) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    emit(Resource.Success(data.toDetailTvShow()))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.message))
                }
                else -> {}
            }
        }
        return result
    }

    override fun getVideo(id: Int): Flow<Resource<Video>> {
        val result = flow {
            val response = movieApi.getVideo(id)
            when (val apiResponse = response.first()) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    emit(Resource.Success(data.toVideo()))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.message))
                }
                else -> {}
            }
        }
        return result
    }

    override fun getTvReviews(id: Int): Flow<Resource<Review>> {
        val result = flow {
            val response = movieApi.getTvReviews(id)
            when (val apiResponse = response.first()) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    emit(Resource.Success(data.toReview()))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.message))
                }
                else -> {}
            }
        }
        return result
    }

    override fun getMovieReviews(id: Int): Flow<Resource<Review>> {
        val result = flow {
            val response = movieApi.getMovieReviews(id)
            when (val apiResponse = response.first()) {
                is ApiResponse.Success -> {
                    val data = apiResponse.data
                    emit(Resource.Success(data.toReview()))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.message))
                }
                else -> {}
            }
        }
        return result
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getMoviePaging(): Flow<PagingData<Result>> {
        val source = { localDataSource.getAllMovies() }
        return Pager(
            config = PagingConfig(pageSize = 5, prefetchDistance = 2),
            remoteMediator = MovieRemoteMediator(apiClient, localDataSource),
            pagingSourceFactory = source
        ).flow.map { pagingData ->
            pagingData.map { resultEntity ->
                resultEntity.toResult()
            }
        }
    }
}