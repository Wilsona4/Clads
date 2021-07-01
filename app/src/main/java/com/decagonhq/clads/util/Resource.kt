package com.decagonhq.clads.util

import retrofit2.Response

// sealed class Resource<out T> {
//    data class Success<out T>(val value: T) : Resource<T>()
//    data class Error(
//        val isNetworkError: Boolean,
//        val errorCode: Int?,
//        val errorBody: Response<Any>?
//    ) : Resource<Nothing>()
//
//    data class Loading<out T>(val message: String) : Resource<T>()
// }

sealed class Resource<T>(
    val data: T? = null,
    val errorBody: Response<Any>? = null,
    val isNetworkError: Boolean? = null,
    val message: String? = null,
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null, message: String? = null) : Resource<T>(data)
    class Error<T>(
        isNetworkError: Boolean,
        errorBody: Response<Any>?,
        data: T? = null
    ) :
        Resource<T>(data, errorBody, isNetworkError)
}
