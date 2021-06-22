package com.decagonhq.clads.data.domain.images

data class ImageModel(
    val downloadUri: String,
    val fileId: String,
    val fileName: String,
    val fileType: String,
    val uploadStatus: Boolean
)
