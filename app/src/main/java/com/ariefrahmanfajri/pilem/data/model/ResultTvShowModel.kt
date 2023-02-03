package com.ariefrahmanfajri.pilem.data.model


import com.google.gson.annotations.SerializedName

data class ResultTvShowModel(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<TvShowModel>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)