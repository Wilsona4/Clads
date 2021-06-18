package com.decagonhq.clads.data.domain.profileimage

data class UserProfileImage(
    val downloadUri: String,
    val fileId: String,
    val fileName: String,
    val fileType: String,
    val uploadStatus: Boolean
)