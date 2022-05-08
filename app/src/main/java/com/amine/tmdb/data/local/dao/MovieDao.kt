package com.amine.tmdb.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.amine.tmdb.data.local.model.MovieEntity

@Dao
interface MovieDao : BaseDao<MovieEntity> {

    @Query(
        "SELECT * FROM movie WHERE " +
                "LOWER(name) LIKE '%' || LOWER(:queryString) || '%' " +
                "ORDER BY name ASC"
    )
    fun getAllMoviesByName(queryString: String): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movie")
    suspend fun clearMovies()
}