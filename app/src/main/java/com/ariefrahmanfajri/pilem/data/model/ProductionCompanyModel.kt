package com.ariefrahmanfajri.pilem.data.model


import com.ariefrahmanfajri.pilem.domain.model.ProductionCompany
import com.google.gson.annotations.SerializedName

data class ProductionCompanyModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?
) {
    fun toProductionCompany() = ProductionCompany(
        id, logoPath, name, originCountry
    )
}