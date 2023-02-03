package com.ariefrahmanfajri.pilem.data.model


import com.google.gson.annotations.SerializedName

data class DatesModel(
    @SerializedName("maximum")
    val maximum: String?,
    @SerializedName("minimum")
    val minimum: String?
)