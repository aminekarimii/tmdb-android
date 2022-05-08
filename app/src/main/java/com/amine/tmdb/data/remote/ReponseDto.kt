package com.amine.tmdb.data.remote

import com.google.gson.annotations.SerializedName

data class PagedResponseDto<T>(
    @SerializedName("page") val page: Int = 0,
    @SerializedName("total_results") val totalResults: Int = 0,
    @SerializedName("total_pages")  val totalPages: Int = 0,
    @SerializedName("results") val response: List<T> = emptyList()
)
