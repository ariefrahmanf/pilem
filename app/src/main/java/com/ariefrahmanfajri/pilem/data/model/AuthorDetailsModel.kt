package com.ariefrahmanfajri.pilem.data.model


import com.ariefrahmanfajri.pilem.domain.model.AuthorDetails
import com.google.gson.annotations.SerializedName

data class AuthorDetailsModel(
    @SerializedName("avatar_path")
    val avatarPath: Any?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("username")
    val username: String?
) {
    fun toAuthorDetails() = AuthorDetails(
        avatarPath, name, rating, username
    )
}