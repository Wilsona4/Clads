package com.decagonhq.clads.util

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.RoomDatabase
import com.decagonhq.clads.R
import com.decagonhq.clads.data.local.CladsDatabase
import retrofit2.Retrofit
import javax.inject.Inject

fun Fragment.showView(view: View) {

    if (view.visibility == View.GONE || view.visibility == View.INVISIBLE) {
        view.visibility = View.VISIBLE
    }
}

fun <T> Fragment.handleApiError(
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

/*Extension function to observe Live Data only once*/
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(
        lifecycleOwner,
        object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        }
    )
}

/*Function to Show Progress Dialog*/
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
