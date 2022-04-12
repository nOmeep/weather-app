package com.example.weatherreport.data.db.caching

sealed class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)

    class Loading<T>(data: T?) : Resource<T>(data)

    class Error<T>(data: T?, throwable: Throwable) : Resource<T>(data, throwable)
}