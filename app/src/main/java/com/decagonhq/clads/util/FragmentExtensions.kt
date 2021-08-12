package com.decagonhq.clads.util

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.decagonhq.clads.R
import com.decagonhq.clads.data.local.CladsDatabase
import com.decagonhq.clads.ui.authentication.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

fun Fragment.showView(view: View) {

    if (view.visibility == View.GONE || view.visibility == View.INVISIBLE) {
        view.visibility = View.VISIBLE
    }
}

fun Fragment.navigateTo(id: Int) {
    findNavController().navigate(id)
}

fun Fragment.navigateTo(direction: NavDirections) {
    findNavController().navigate(direction)
}

fun <T> Fragment.handleApiError(
    failure: Resource.Error<T>,
    retrofit: Retrofit,
    view: View,
    sessionManager: SessionManager,
    database: CladsDatabase
) {
    val errorResponseUtil = ErrorResponseUtil(retrofit)
    when (failure.isNetworkError) {
        true -> {
            view.showSnackBar("Poor Internet Connection. Retry")
        }
        else -> {
            try {
                val error = failure.errorBody?.let { it1 -> errorResponseUtil.parseError(it1) }
                val errorMessage = error?.message
                if (errorMessage != null) {
                    if (errorMessage == "Invalid username/password" && findNavController().currentDestination?.id != R.id.login_fragment) {
                        logOut(sessionManager, database)
                    } else if (errorMessage == "401") {
                        view.showSnackBar("Session Timeout")
                        logOut(sessionManager, database)
                    } else {
                        view.showSnackBar(errorMessage)
                    }
                } else {
                    view.showSnackBar("Something went wrong!... Retry")
                }
            } catch (e: Exception) {
                view.showSnackBar("Bad request. Check Input again.")
            }
        }
    }
}

fun Fragment.logOut(sessionManager: SessionManager, database: CladsDatabase) {
    Intent(requireActivity(), MainActivity::class.java).also {
        sessionManager.clearSharedPref()
        sessionManager.saveToSharedPref(
            getString(R.string.login_status),
            getString(R.string.log_out)
        )
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                database.clearAllTables()
            }
        }
        startActivity(it)
        requireActivity().finish()
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
