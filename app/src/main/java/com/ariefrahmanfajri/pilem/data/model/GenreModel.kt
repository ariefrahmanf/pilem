package com.ariefrahmanfajri.pilem.data.model


import com.ariefrahmanfajri.pilem.domain.model.Genre
import com.google.gson.annotations.SerializedName

data class GenreModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
) {
    fun toGenre() = Genre(
        id, name
    )
}