package com.amine.tmdb.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.amine.tmdb.data.local.AppDatabase
import com.amine.tmdb.data.local.model.MovieEntity
import com.amine.tmdb.data.remote.MovieService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val service: MovieService
) : MovieRepository {

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }

    override fun getMoviesList(query: String?): Flow<PagingData<MovieEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = MovieRemoteMediator(
                query = "action",
                service = service,
                database = database
            ),
            pagingSourceFactory = {
                database.getMovieDao().getAllMoviesByName(query.orEmpty())
            }
        ).flow
    }
}