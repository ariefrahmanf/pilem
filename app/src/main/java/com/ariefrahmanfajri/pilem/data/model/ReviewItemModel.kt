package com.ariefrahmanfajri.pilem.data.model


import com.ariefrahmanfajri.pilem.domain.model.ReviewItem
import com.google.gson.annotations.SerializedName

data class ReviewItemModel(
    @SerializedName("author")
    val author: String?,
    @SerializedName("author_details")
    val authorDetails: AuthorDetailsModel?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("url")
    val url: String?
) {
    fun toReviewItem() = ReviewItem(
        author,
        authorDetails?.toAuthorDetails(),
        content, createdAt, id, updatedAt, url
    )
}