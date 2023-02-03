package com.ariefrahmanfajri.pilem.domain.usecase

import androidx.paging.PagingData
import com.ariefrahmanfajri.pilem.data.entity.ResultEntity
import com.ariefrahmanfajri.pilem.data.repository.MovieRepository
import com.ariefrahmanfajri.pilem.di.DaggerAppComponent
import com.ariefrahmanfajri.pilem.domain.model.*
import com.ariefrahmanfajri.pilem.domain.repository.IMovieRepository
import com.ariefrahmanfajri.pilem.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(val movieRepository: IMovieRepository): MovieUseCase {

    override fun getNowPlaying(): Flow<Resource<List<Result>>> {
        return movieRepository.getNowPlaying()
    }

    override fun getPopular(): Flow<Resource<List<Result>>> {
        return movieRepository.getPopular()
    }

    override fun searchMovie(query: String): Flow<Resource<List<Result>>> {
        return movieRepository.searchMovie(query)
    }

    override fun getMovieDetail(movieId: String): Flow<Resource<MovieDetail>> {
        return movieRepository.getMovieDetail(movieId)
    }

    override fun getCredit(movieId: String): Flow<Resource<Credit>> {
        return movieRepository.getCredit(movieId)
    }

    override fun getPopularTvShow(): Flow<Resource<List<TvShow>>> {
        return movieRepository.getPopularTvShow()
    }

    override fun getTopRatedTvShow(): Flow<Resource<List<TvShow>>> {
        return movieRepository.getTopRatedTvShow()
    }

    override fun getTvShowDetail(tvId: Int): Flow<Resource<DetailTvShow>> {
        return movieRepository.getTvShowDetail(tvId)
    }

    override fun getVideo(id: Int): Flow<Resource<Video>> {
        return movieRepository.getVideo(id)
    }

    override fun getTvReviews(id: Int): Flow<Resource<Review>> {
        return movieRepository.getTvReviews(id)
    }

    override fun getMovieReviews(id: Int): Flow<Resource<Review>> {
        return movieRepository.getMovieReviews(id)
    }

    override fun getMoviePaging(): Flow<PagingData<Result>> {
        return movieRepository.getMoviePaging()
    }
}