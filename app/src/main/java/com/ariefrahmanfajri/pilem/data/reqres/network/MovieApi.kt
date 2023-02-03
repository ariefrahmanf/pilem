package com.ariefrahmanfajri.pilem.data.reqres.network

import com.ariefrahmanfajri.pilem.data.model.*
import com.ariefrahmanfajri.pilem.util.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

class MovieApi @Inject constructor(val api: MovieApiClient) {
    fun getNowPlaying(page: Int? = null): Flow<ApiResponse<List<ResultModel>>> {
        return flow {
            try {
                val response = api.getNowPlaying(page)
                val dataArray = response.resultModels
                if (dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(dataArray))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(message = e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getPopular(): Flow<ApiResponse<List<ResultModel>>> {
        return flow {
            try {
                val response = api.getPopular()
                val dataArray = response.resultModels
                if (dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(dataArray))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(message = e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun searchMovie(query: String): Flow<ApiResponse<List<ResultModel>>> {
        return flow {
            try {
                val response = api.searchMovie(query)
                val dataArray = response.resultModels
                if (dataArray.isNullOrEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(dataArray))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(message = e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMovieDetail(movieId: String): Flow<ApiResponse<MovieDetailModel>> {
        return flow {
            try {
                val response = api.getMovieDetail(movieId)
                if (response.id.toString().isEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(message = e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getCredit(movieId: String): Flow<ApiResponse<CreditModel>> {
        return flow {
            try {
                val response = api.getCredit(movieId)
                if (response.id.toString().isEmpty()) {
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(message = e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getPopularTvShow(): Flow<ApiResponse<List<TvShowModel>>> {
        return flow {
            try {
                val response = api.getPopularTvShow()
                if (response.results?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(message = e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getTopRatedTvShow(): Flow<ApiResponse<List<TvShowModel>>> {
        return flow {
            try {
                val response = api.getTopRatedTvShow()
                if (response.results?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(message = e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getTvShowDetail(tvId: Int): Flow<ApiResponse<DetailTvShowModel>> {
        return flow {
            try {
                val response = api.getTvShowDetail(tvId)
                if (!response.name.isNullOrEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(message = e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getVideo(id: Int): Flow<ApiResponse<VideoModel>> {
        return flow {
            try {
                val response = api.getVideo(id)
                if (!response.results.isNullOrEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(message = e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getTvReviews(id: Int): Flow<ApiResponse<ReviewModel>> {
        return flow {
            try {
                val response = api.getTvReviews(id)
                if (!response.results.isNullOrEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(message = e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMovieReviews(id: Int): Flow<ApiResponse<ReviewModel>> {
        return flow {
            try {
                val response = api.getMovieReviews(id)
                if (!response.results.isNullOrEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(message = e.localizedMessage))
            }
        }.flowOn(Dispatchers.IO)
    }
}