package com.decagonhq.clads.data.domain

import okhttp3.MultipartBody

class GalleryImageModel(
    val image: MultipartBody.Part,
    val description: String
)
