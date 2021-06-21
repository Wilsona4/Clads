package com.decagonhq.clads.util

import android.view.View
import androidx.fragment.app.Fragment
import retrofit2.Retrofit

fun Fragment.showView(view: View) {

    if (view.visibility == View.GONE || view.visibility == View.INVISIBLE) {
        view.visibility = View.VISIBLE
    }
}

fun Fragment.handleApiError(
    failure: Resource.Error,
    retrofit: Retrofit,
    view: View,
) {
    val errorResponseUtil = ErrorResponseUtil(retrofit)
    when {
        failure.isNetworkError -> {
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
