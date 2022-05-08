package com.amine.tmdb.domain.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.amine.tmdb.data.local.model.MovieEntity
import com.amine.tmdb.data.remote.MovieService
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MovieSource(
    private val service: MovieService,
    private val query: String,
    private val language: String = "en-US",
    private val includeAdult: Boolean = false
) : PagingSource<Int, MovieEntity>() {

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = service.fetchMoviesList(
                MovieService.API_KEY,
                page = position,
                query = query,
                language = language,
                includeAdult = includeAdult
            )

            val repos = response.response
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = repos,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

}