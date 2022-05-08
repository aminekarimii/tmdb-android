package com.amine.tmdb.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey @SerializedName("id") val id: Int,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("overview") val overview: String,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("backdrop_path") @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") @ColumnInfo(name = "vote_average") val voteAverage: String,
    @SerializedName("first_air_date") @ColumnInfo(name = "first_air_date") val firstAirDate: String?,
    @SerializedName("original_language") @ColumnInfo(name = "original_language") val originalLanguage: String,
    @SerializedName("poster_path") @ColumnInfo(name = "poster_path") val posterPath: String?,
    @SerializedName("vote_count") @ColumnInfo(name = "vote_count") val voteCount: Int
)