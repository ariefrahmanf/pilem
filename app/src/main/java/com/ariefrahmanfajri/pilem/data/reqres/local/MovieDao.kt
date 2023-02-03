package com.ariefrahmanfajri.pilem.data.reqres.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ariefrahmanfajri.pilem.data.entity.MovieRemoteKeys
import com.ariefrahmanfajri.pilem.data.entity.ResultEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ResultEntity>)

    @Query("SELECT * FROM ResultEntity")
    fun getAllMovies(): PagingSource<Int, ResultEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(list: List<MovieRemoteKeys>)

    @Query("SELECT * FROM MovieRemoteKeys WHERE id = :id")
    suspend fun getALlMovieKeys(id: Int): MovieRemoteKeys?

    @Query("DELETE FROM ResultEntity")
    suspend fun deleteAllMovies()

    @Query("DELETE FROM  MovieRemoteKeys")
    suspend fun deleteAllRemoteKeys()
}