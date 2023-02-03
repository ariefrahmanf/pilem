package com.ariefrahmanfajri.pilem.data.model


import com.ariefrahmanfajri.pilem.domain.model.Review
import com.google.gson.annotations.SerializedName

data class ReviewModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<ReviewItemModel>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
) {
    fun toReview() = Review(
        id,
        page,
        results?.map { it.toReviewItem() },
        totalPages, totalResults
    )
}