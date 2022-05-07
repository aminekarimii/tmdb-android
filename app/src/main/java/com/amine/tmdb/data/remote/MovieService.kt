package com.amine.tmdb.data.remote

import com.amine.tmdb.data.local.entities.MovieEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("search/tv?")
    suspend fun fetchMoviesList(
        @Query("api_key") apiKey:String,
        @Query("language") language:String,
        @Query("page") page:Int,
        @Query("query") query:String,
        @Query("include_adult") includeAdult:Boolean
    ) : PagedResponseDto<MovieEntity>

    companion object {
        const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "8d6a13d4ff986513574e73180f498ed9"
    }
}