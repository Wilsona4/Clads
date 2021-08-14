package com.decagonhq.clads.util

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.decagonhq.clads.R
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

/**
 * An extension function for loading images from server using glide
 * with provisions for loading and error state
 */
fun ImageView.loadImage(imageUrl: String?) {
    val imgUri = imageUrl?.toUri()
    Glide.with(this)
        .load(imgUri).apply(
            RequestOptions()
                .placeholder(R.drawable.nav_drawer_profile_avatar)
                .error(R.drawable.ic_broken_image)
        ).into(this)
}
