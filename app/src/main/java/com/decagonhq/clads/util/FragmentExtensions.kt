package com.decagonhq.clads.util

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.decagonhq.clads.R
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

fun Fragment.showLoadingBar(message: String): Dialog {
    val dialog by lazy {
        Dialog(requireContext(), R.style.Theme_MaterialComponents_Dialog).apply {
            setContentView(R.layout.layout_loading_dialog)
            setCanceledOnTouchOutside(false)
            setTitle(message)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }
    return dialog
}
