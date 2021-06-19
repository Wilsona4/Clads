package com.decagonhq.clads.util

import androidx.lifecycle.MutableLiveData

object DataListener {

    var imageListener = MutableLiveData<Boolean>()

    init {
        imageListener.value = false
    }
}
