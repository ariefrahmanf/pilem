package com.ariefrahmanfajri.pilem.data.model


import com.ariefrahmanfajri.pilem.domain.model.ProductionCountry
import com.google.gson.annotations.SerializedName

data class ProductionCountryModel(
    @SerializedName("iso_3166_1")
    val iso31661: String?,
    @SerializedName("name")
    val name: String?
) {
    fun toProductionCountry() = ProductionCountry(
        iso31661, name
    )
}