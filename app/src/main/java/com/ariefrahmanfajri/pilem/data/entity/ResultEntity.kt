package com.ariefrahmanfajri.pilem.data.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ariefrahmanfajri.pilem.domain.model.Result

@Entity
data class ResultEntity(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
) {
    fun toResult() = Result(
        adult,
        backdropPath,
        genreIds,
        id,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        title,
        video,
        voteAverage,
        voteCount
    )
}