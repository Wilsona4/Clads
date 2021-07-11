package com.decagonhq.clads.data.remote.images

data class ImageDTO(
    val downloadUri: String,
    val fileId: String,
    val fileName: String,
    val fileType: String,
    val uploadStatus: Boolean
)
