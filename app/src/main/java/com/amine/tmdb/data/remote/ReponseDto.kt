package com.amine.tmdb.data.remote

import com.google.gson.annotations.SerializedName

data class PagedResponseDto<T>(
    val page: Int,
    val totalResults: Int,
    val totalPages: Int,
    val response: List<T>
)

data class MovieDto(
    val id: Int,
    val name: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("vote_average") val voteAverage: Int,
    @SerializedName("vote_count") val voteCount: Int
)