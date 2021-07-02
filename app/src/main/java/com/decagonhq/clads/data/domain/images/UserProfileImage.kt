package com.decagonhq.clads.data.domain.images

import androidx.room.Entity

@Entity
data class UserProfileImage(
    val downloadUri: String,
    val fileId: String,
    val fileName: String,
    val fileType: String,
    val uploadStatus: Boolean
)
