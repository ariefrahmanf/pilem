package com.ariefrahmanfajri.pilem.data.reqres.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ariefrahmanfajri.pilem.data.entity.MovieRemoteKeys
import com.ariefrahmanfajri.pilem.data.entity.ResultEntity
import com.ariefrahmanfajri.pilem.util.GenresIdConverter

@Database(
    entities = [ResultEntity::class, MovieRemoteKeys::class],
    version = 3,
    exportSchema = false
)

@TypeConverters(GenresIdConverter::class)
abstract class PilemDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}