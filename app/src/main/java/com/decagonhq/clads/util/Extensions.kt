package com.decagonhq.clads.util

import android.view.View
import androidx.fragment.app.Fragment

fun Fragment.showView(view: View) {

    if (view.visibility == View.GONE || view.visibility == View.INVISIBLE) {
        view.visibility = View.VISIBLE
    }
}
