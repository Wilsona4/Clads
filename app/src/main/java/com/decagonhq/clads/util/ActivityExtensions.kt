package com.decagonhq.clads.util

import android.app.Activity
import android.view.View
import retrofit2.Retrofit

fun <T> Activity.handleApiError(
    failure: Resource.Error<T>,
    retrofit: Retrofit,
    view: View
) {
    val errorResponseUtil = ErrorResponseUtil(retrofit)
    when {
        failure.isNetworkError == true -> {
            view.showSnackBar("Poor Internet Connection. Retry")
        }
        else -> {
            try {
                val error = failure.errorBody?.let { it1 -> errorResponseUtil.parseError(it1) }
                val errorMessage = error?.message
                if (errorMessage != null) {
                    view.showSnackBar(errorMessage)
                } else {
                    view.showSnackBar("Something went wrong!... Retry")
                }
            } catch (e: Exception) {
                view.showSnackBar("Bad request. Check Input again.")
            }
        }
    }
}
