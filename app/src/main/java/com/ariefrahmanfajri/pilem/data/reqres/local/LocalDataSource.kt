package com.ariefrahmanfajri.pilem.data.reqres.local

import androidx.paging.PagingSource
import com.ariefrahmanfajri.pilem.data.entity.MovieRemoteKeys
import com.ariefrahmanfajri.pilem.data.entity.ResultEntity

class LocalDataSource(private val dao: MovieDao) {
    suspend fun insertAll(list: List<ResultEntity>) {
        dao.insertAll(list)
    }

    fun getAllMovies(): PagingSource<Int, ResultEntity> {
        return dao.getAllMovies()
    }

    suspend fun insertAllRemoteKeys(list: List<MovieRemoteKeys>) {
        dao.insertAllRemoteKeys(list)
    }

    suspend fun getMovieKeys(id: Int): MovieRemoteKeys? {
        return dao.getALlMovieKeys(id)
    }

    suspend fun deleteAllRemoteKeys() {
        dao.deleteAllRemoteKeys()
    }

    suspend fun deleteAllMovies() {
        dao.deleteAllMovies()
    }
}