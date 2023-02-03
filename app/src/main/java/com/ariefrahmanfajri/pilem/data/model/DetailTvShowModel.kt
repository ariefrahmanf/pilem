package com.ariefrahmanfajri.pilem.data.model


import com.ariefrahmanfajri.pilem.domain.model.DetailTvShow
import com.google.gson.annotations.SerializedName

data class DetailTvShowModel(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("created_by")
    val createdBy: List<CreatedByModel>?,
    @SerializedName("episode_run_time")
    val episodeRunTime: List<Any>?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("genres")
    val genres: List<GenreModel>?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("in_production")
    val inProduction: Boolean?,
    @SerializedName("languages")
    val languages: List<String>?,
    @SerializedName("last_air_date")
    val lastAirDate: String?,
    @SerializedName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAirModel?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("networks")
    val networks: List<NetworkModel>?,
    @SerializedName("next_episode_to_air")
    val nextEpisodeToAir: NextEpisodeToAirModel?,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int?,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int?,
    @SerializedName("origin_country")
    val originCountry: List<String>?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompanyModel>?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountryModel>?,
    @SerializedName("seasons")
    val seasons: List<SeasonModel>?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageModel>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) {
    fun toDetailTvShow() = DetailTvShow(
        adult,
        backdropPath,
        createdBy?.map { it.toCreatedBy() },
        episodeRunTime,
        firstAirDate,
        genres?.map { it.toGenre() },
        homepage,
        id,
        inProduction,
        languages,
        lastAirDate,
        lastEpisodeToAir?.toLastEpisodeToAir(),
        name,
        networks?.map { it.toNetwork() },
        nextEpisodeToAir?.toNextEpisodeToAir(),
        numberOfEpisodes,
        numberOfSeasons,
        originCountry,
        originalLanguage,
        originalName,
        overview,
        popularity,
        posterPath,
        productionCompanies?.map { it.toProductionCompany() },
        productionCountries?.map { it.toProductionCountry() },
        seasons?.map { it.toSeason() },
        spokenLanguages?.map { it.toSpokenLanguage() },
        status,
        tagline,
        type,
        voteAverage,
        voteCount
    )
}