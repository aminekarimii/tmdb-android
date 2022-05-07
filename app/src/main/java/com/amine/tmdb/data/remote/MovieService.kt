package com.amine.tmdb.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("search/tv?")
    suspend fun fetchMoviesList(
        @Query("api_key") apiKey:String,
        @Query("language") language:String,
        @Query("page") page:String,
        @Query("query") query:String,
        @Query("include_adult") include_adult:String
    ) : Response<PagedResponseDto<MovieDto>>

    companion object {
        const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
    }
}