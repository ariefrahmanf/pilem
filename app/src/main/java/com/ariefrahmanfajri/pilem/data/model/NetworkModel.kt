package com.ariefrahmanfajri.pilem.data.model


import com.ariefrahmanfajri.pilem.domain.model.Network
import com.google.gson.annotations.SerializedName

data class NetworkModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?
) {
    fun toNetwork() = Network(id, logoPath, name, originCountry)
}