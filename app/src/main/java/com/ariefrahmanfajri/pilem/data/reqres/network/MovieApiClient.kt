package com.ariefrahmanfajri.pilem.data.reqres.network

import com.ariefrahmanfajri.pilem.data.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiClient {

    @GET("movie/now_playing?api_key=809e0a1f6d47ca83b2ae328adb012a0b")
    suspend fun getNowPlaying(
        @Query("page") page: Int? = null
    ): NowPlayingModel

    @GET("movie/popular?api_key=809e0a1f6d47ca83b2ae328adb012a0b")
    suspend fun getPopular(): PopularModel

    @GET("search/movie?api_key=809e0a1f6d47ca83b2ae328adb012a0b&language=en-US")
    suspend fun searchMovie(
        @Query("query") query: String
    ): SearchModel

    @GET("movie/{movie_id}?api_key=809e0a1f6d47ca83b2ae328adb012a0b&language=en-US")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: String
    ): MovieDetailModel

    @GET("movie/{movie_id}/credits?api_key=809e0a1f6d47ca83b2ae328adb012a0b&language=en-US")
    suspend fun getCredit(@Path("movie_id") movieId: String): CreditModel

    @GET("tv/popular?api_key=809e0a1f6d47ca83b2ae328adb012a0b")
    suspend fun getPopularTvShow(): ResultTvShowModel

    @GET("tv/top_rated?api_key=809e0a1f6d47ca83b2ae328adb012a0b")
    suspend fun getTopRatedTvShow(): ResultTvShowModel

    @GET("tv/{tv_id}?api_key=809e0a1f6d47ca83b2ae328adb012a0b")
    suspend fun getTvShowDetail(@Path("tv_id") tvId: Int): DetailTvShowModel

    @GET("tv/{id}/videos?api_key=809e0a1f6d47ca83b2ae328adb012a0b")
    suspend fun getVideo(@Path("id") id: Int): VideoModel

    @GET("tv/{id}/reviews?api_key=809e0a1f6d47ca83b2ae328adb012a0b")
    suspend fun getTvReviews(@Path("id") id: Int): ReviewModel

    @GET("movie/{id}/reviews?api_key=809e0a1f6d47ca83b2ae328adb012a0b")
    suspend fun getMovieReviews(@Path("id") id: Int): ReviewModel

}
