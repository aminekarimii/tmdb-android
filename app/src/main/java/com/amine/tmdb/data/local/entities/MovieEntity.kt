package com.amine.tmdb.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey val id: Int,
    @field:SerializedName("popularity") val popularity: Double,
    @field:SerializedName("overview") val overview: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("originalName") val originalName: String,
    @field:SerializedName("backdrop_path") @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @field:SerializedName("first_air_date") @ColumnInfo(name = "vote_average") val voteAverage: Int,
    @field:SerializedName("original_language") @ColumnInfo(name = "first_air_date") val firstAirDate: String,
    @field:SerializedName("poster_path") @ColumnInfo(name = "original_language") val originalLanguage: String,
    @field:SerializedName("vote_average") @ColumnInfo(name = "poster_path") val posterPath: String?,
    @field:SerializedName("vote_count") @ColumnInfo(name = "vote_count") val voteCount: Int
)