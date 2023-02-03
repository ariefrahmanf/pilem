package com.ariefrahmanfajri.pilem.data.model

import com.ariefrahmanfajri.pilem.domain.model.MovieDetail
import com.google.gson.annotations.SerializedName

data class MovieDetailModel(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollectionModel?,
    @SerializedName("budget")
    val budget: Int?,
    @SerializedName("genres")
    val genres: List<GenreModel>?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
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
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("revenue")
    val revenue: Int?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageModel>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) {
    fun toMovieDetail() = MovieDetail(
        adult,
        backdropPath,
        belongsToCollection?.toBelongCollection(),
        budget,
        genres?.map { it.toGenre() },
        homepage,
        id,
        imdbId,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        productionCompanies?.map { it.toProductionCompany() },
        productionCountries?.map { it.toProductionCountry() },
        releaseDate,
        revenue,
        runtime,
        spokenLanguages?.map { it.toSpokenLanguage() },
        status,
        tagline,
        title,
        video,
        voteAverage, voteCount
    )
}