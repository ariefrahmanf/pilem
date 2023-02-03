package com.ariefrahmanfajri.pilem.domain.model

data class NowPlaying(
    val datesModel: Dates?,
    val page: Int?,
    val resultModels: List<Result>?,
    val totalPages: Int?,
    val totalResults: Int?
)