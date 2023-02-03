package com.ariefrahmanfajri.pilem.data.model


import com.ariefrahmanfajri.pilem.domain.model.BelongsToCollection
import com.google.gson.annotations.SerializedName

data class BelongsToCollectionModel(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("poster_path")
    val posterPath: String?
) {
    fun toBelongCollection() = BelongsToCollection(
        backdropPath, id, name, posterPath
    )
}