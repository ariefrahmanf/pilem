package com.ariefrahmanfajri.pilem.data.pagingsource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ariefrahmanfajri.pilem.data.entity.MovieRemoteKeys
import com.ariefrahmanfajri.pilem.data.entity.ResultEntity
import com.ariefrahmanfajri.pilem.data.reqres.local.LocalDataSource
import com.ariefrahmanfajri.pilem.data.reqres.network.MovieApiClient
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator @Inject constructor(
    private val movieApi: MovieApiClient,
    private val db: LocalDataSource
) :
    RemoteMediator<Int, ResultEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ResultEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeysClosestToCurrentPosition(state)
                remoteKeys?.next?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.next
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeysForFirstItem(state)
                val prevKey = remoteKeys?.prev
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
        }

        return try {
            val response = movieApi.getNowPlaying(page)
            val isListEmpty = response.resultModels.isNullOrEmpty()

            if (loadType == LoadType.REFRESH) {
                db.deleteAllMovies()
                db.deleteAllRemoteKeys()
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (isListEmpty) null else page + 1

            val keys = response.resultModels?.map {
                MovieRemoteKeys(it.id as Int, prevKey, nextKey)
            }

            val movieEntities = response.resultModels?.map {
                ResultEntity(
                    it.adult ?: false,
                    it.backdropPath ?: "",
                    it.genreIds ?: listOf(),
                    it.id ?: 0,
                    it.originalLanguage ?: "",
                    it.originalTitle ?: "",
                    it.overview ?: "",
                    it.popularity ?: 0.0,
                    it.posterPath ?: "",
                    it.releaseDate ?: "",
                    it.title ?: "",
                    it.video ?: false,
                    it.voteAverage ?: 0.0,
                    it.voteCount ?: 0
                )
            }

            db.insertAllRemoteKeys(keys as List<MovieRemoteKeys>)
            db.insertAll(movieEntities as List<ResultEntity>)

            return MediatorResult.Success(endOfPaginationReached = isListEmpty)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeysForFirstItem(state: PagingState<Int, ResultEntity>): MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let {
            db.getMovieKeys(it.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ResultEntity>): MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            db.getMovieKeys(it.id)
        }
    }

    private suspend fun getRemoteKeysClosestToCurrentPosition(state: PagingState<Int, ResultEntity>): MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {
                db.getMovieKeys(it)
            }
        }
    }
}