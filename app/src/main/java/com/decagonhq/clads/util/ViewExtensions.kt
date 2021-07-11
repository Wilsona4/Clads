package com.decagonhq.clads.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showView() {
    this.visibility = View.VISIBLE
}

fun View.hideView() {
    this.visibility = View.GONE
}

fun View.toggleViewVisibility() {
    if (this.visibility == View.VISIBLE) {
        this.visibility = View.GONE
    } else if (this.visibility == View.GONE) {
        this.visibility = View.VISIBLE
    }
}

// Extension function for showing snack bar
fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}
