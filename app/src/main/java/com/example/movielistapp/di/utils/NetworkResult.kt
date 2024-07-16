package com.example.movielistapp.di.utils

sealed class NetworkResult<T>(val data: T? = null, val message: String? = null, val cod: T? = null) {
    class Success<T>(data: T?): NetworkResult<T>(data)
    class Error<T>(cod: T? = null, message: String): NetworkResult<T>(cod, message)
}