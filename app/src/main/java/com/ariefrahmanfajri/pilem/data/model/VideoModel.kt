package com.ariefrahmanfajri.pilem.data.model


import com.ariefrahmanfajri.pilem.domain.model.Video
import com.google.gson.annotations.SerializedName

data class VideoModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val results: List<VideoItemModel>?
) {
    fun toVideo() = Video(
        id,
        results?.map { it.toVideoItem() }
    )
}