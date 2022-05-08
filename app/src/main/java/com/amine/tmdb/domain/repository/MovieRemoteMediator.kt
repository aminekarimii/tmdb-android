package com.amine.tmdb.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.amine.tmdb.data.local.AppDatabase
import com.amine.tmdb.data.local.model.MovieEntity
import com.amine.tmdb.data.local.model.RemoteKeysEntity
import com.amine.tmdb.data.remote.MovieService
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val query: String = "action",
    private val language: String = "en-US",
    private val includeAdult: Boolean = false,
    private val service: MovieService,
    private val database: AppDatabase
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {

        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = state.anchorPosition?.let { position ->
                    state.closestItemToPosition(position)?.id?.let { movieId ->
                        database.remoteKeysDao().remoteKeysMovieId(movieId.toLong())
                    }
                }

                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys =
                    state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
                        ?.let { repo ->
                            // Get the remote keys of the first items retrieved
                            database.remoteKeysDao().remoteKeysMovieId(repo.id.toLong())
                        }

                remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
                    ?.let { movie ->
                        // Get the remote keys of the last item retrieved
                        database.remoteKeysDao().remoteKeysMovieId(movie.id.toLong())
                    }

                remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {

            val response = service.fetchMoviesList(
                MovieService.API_KEY,
                page = page,
                query = query,
                language = language,
                includeAdult = includeAdult
            )

            val moviesFromApi = response.response

            val endOfPaginationReached = moviesFromApi.isEmpty()

            database.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.getMovieDao().clearMovies()
                }

                val prevKey = null
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = moviesFromApi.map {
                    RemoteKeysEntity(movieId = it.id.toLong(), prevKey = prevKey, nextKey = nextKey)
                }

                database.remoteKeysDao().insertAll(keys)
                database.getMovieDao().insertAll(moviesFromApi)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)

        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }


}
