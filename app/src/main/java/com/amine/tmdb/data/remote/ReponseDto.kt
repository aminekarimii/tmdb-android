package com.amine.tmdb.data.remote

data class PagedResponseDto<T>(
    val page: Int = 0,
    val totalResults: Int = 0,
    val totalPages: Int = 0,
    val response: List<T> = emptyList()
)
