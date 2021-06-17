package com.decagonhq.clads.util

import retrofit2.Response

// // Generic Resource Class to handle network errors
// data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
//    companion object {
//        fun <T> success(data: T?): Resource<T> {
//            return Resource(Status.SUCCESS, data, null)
//        }
//
//        fun <T> error(msg: String, data: T?): Resource<T> {
//            return Resource(Status.ERROR, data, msg)
//        }
//
//        fun <T> loading(data: T?): Resource<T> {
//            return Resource(Status.LOADING, data, null)
//        }
//    }
// }
//
// enum class Status{
//    SUCCESS, ERROR, LOADING
// }

// A generic class that contains data and status about loading this data.
// sealed class Resource<T>(
//    val data: T? = null,
//    val message: String? = null
// ) {
//    class Success<T>(data: T) : Resource<T>(data)
//    class Loading<T>(data: T? = null) : Resource<T>(data)
//    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
// }

// sealed class Resource<out T>(val data: T? = null) {
//
//    class Success<out T>(data: T): Resource<T>(data)
//    data class Error(val exception: Exception): Resource<Nothing>()
//    object Loading: Resource<Nothing>()
//
// }

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Error(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: Response<Any>?
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}
