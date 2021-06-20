package com.decagonhq.clads.util

import android.view.View

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.toggleVisibility() {
    if (this.visibility == View.VISIBLE) {
        this.visibility = View.GONE
    } else if (this.visibility == View.GONE) {
        this.visibility = View.VISIBLE
    }
}
