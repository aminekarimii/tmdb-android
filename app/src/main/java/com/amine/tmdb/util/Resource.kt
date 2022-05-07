package com.amine.tmdb.util

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(val isLoading:Boolean) : Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String?) : Resource<T>(message = message)
}