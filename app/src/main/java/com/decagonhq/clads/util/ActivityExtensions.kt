package com.decagonhq.clads.util

import android.app.Activity
import android.content.Intent
import android.view.View
import com.decagonhq.clads.R
import com.decagonhq.clads.data.local.CladsDatabase
import com.decagonhq.clads.ui.authentication.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

fun <T> Activity.handleApiError(
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
                    if (errorMessage == "Invalid username/password" || errorMessage == "User not authorized.") {
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

fun Activity.logOut(sessionManager: SessionManager, database: CladsDatabase) {
    Intent(this, MainActivity::class.java).also {
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
        finish()
    }
}
