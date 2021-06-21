package com.decagonhq.clads.util

import retrofit2.Response

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Error(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: Response<Any>?
    ) : Resource<Nothing>()

    data class Loading<out T>(val message: String) : Resource<T>()
}
