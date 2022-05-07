package com.amine.tmdb.domain.repository

import androidx.paging.PagingData
import com.amine.tmdb.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMoviesList(
        query: String? = null
    ): Flow<PagingData<MovieEntity>>
}