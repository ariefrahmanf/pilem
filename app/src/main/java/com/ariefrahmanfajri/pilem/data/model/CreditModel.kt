package com.ariefrahmanfajri.pilem.data.model


import com.ariefrahmanfajri.pilem.domain.model.Credit
import com.google.gson.annotations.SerializedName

data class CreditModel(
    @SerializedName("cast")
    val cast: List<CastModel>?,
    @SerializedName("crew")
    val crew: List<CrewModel>?,
    @SerializedName("id")
    val id: Int?
) {
    fun toCredit() = Credit(
        cast?.map { it.toCast() },
        crew?.map { it.toCrew() },
        id
    )
}