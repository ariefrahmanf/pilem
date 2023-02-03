package com.ariefrahmanfajri.pilem.domain.model


data class Review(
    val id: Int?,
    val page: Int?,
    val results: List<ReviewItem>?,
    val totalPages: Int?,
    val totalResults: Int?
)