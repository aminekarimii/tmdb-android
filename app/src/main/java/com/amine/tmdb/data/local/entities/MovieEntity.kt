package com.amine.tmdb.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val popularity: Double,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "vote_average") val voteAverage: Int,
    val overview: String,
    val name: String,
    val originalName: String,
    @ColumnInfo(name = "first_air_date") val firstAirDate: String,
    @ColumnInfo(name = "original_language") val originalLanguage: String,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "vote_count") val voteCount: Int
)