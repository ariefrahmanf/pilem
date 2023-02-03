package com.ariefrahmanfajri.pilem.data.model


import com.ariefrahmanfajri.pilem.domain.model.CreatedBy
import com.google.gson.annotations.SerializedName

data class CreatedByModel(
    @SerializedName("credit_id")
    val creditId: String?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("profile_path")
    val profilePath: String?
) {
    fun toCreatedBy() = CreatedBy(creditId, gender, id, name, profilePath)
}