package com.decagonhq.clads.data.domain.images

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_image_table")
data class UserProfileImage(
    val downloadUri: String,
    @PrimaryKey
    val fileId: String,
    val fileName: String,
    val fileType: String,
    val uploadStatus: Boolean
)
