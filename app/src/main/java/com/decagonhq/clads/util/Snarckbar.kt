package com.decagonhq.clads.util

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.errorSnack(message: String, length: Int = Snackbar.LENGTH_LONG) {
    val snack = Snackbar.make(this, message, length)
    snack.setActionTextColor(Color.parseColor("#FFFFFF"))
    snack.view.setBackgroundColor(Color.parseColor("#C62828"))
    snack.show()
}
