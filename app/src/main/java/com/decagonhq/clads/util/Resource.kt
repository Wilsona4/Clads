package com.decagonhq.clads.util

import retrofit2.Response

sealed class Resource<T>(
    val data: T? = null,
    val errorBody: Response<Any>? = null,
    val isNetworkError: Boolean? = null,
    val message: String? = null,
) {
    class Success<T>(data: T, message: String? = null) : Resource<T>(data, message = message)
    class Loading<T>(data: T? = null, message: String? = null) : Resource<T>(data)
    class Error<T>(
        isNetworkError: Boolean,
        errorBody: Response<Any>?,
        data: T? = null,
        message: String? = null
    ) : Resource<T>(data, errorBody, isNetworkError, message)
}
