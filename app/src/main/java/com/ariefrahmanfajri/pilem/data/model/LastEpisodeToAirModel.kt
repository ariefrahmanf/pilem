package com.ariefrahmanfajri.pilem.data.model


import com.ariefrahmanfajri.pilem.domain.model.LastEpisodeToAir
import com.google.gson.annotations.SerializedName

data class LastEpisodeToAirModel(
    @SerializedName("air_date")
    val airDate: String?,
    @SerializedName("episode_number")
    val episodeNumber: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("production_code")
    val productionCode: String?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("season_number")
    val seasonNumber: Int?,
    @SerializedName("show_id")
    val showId: Int?,
    @SerializedName("still_path")
    val stillPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) {
    fun toLastEpisodeToAir() = LastEpisodeToAir(
        airDate,
        episodeNumber,
        id,
        name,
        overview,
        productionCode,
        runtime,
        seasonNumber,
        showId,
        stillPath,
        voteAverage,
        voteCount
    )
}