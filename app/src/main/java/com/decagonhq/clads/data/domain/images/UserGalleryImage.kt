package com.decagonhq.clads.data.domain.images

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gallery_image_table")
data class UserGalleryImage(
    val downloadUri: String,
    @PrimaryKey
    val fileId: String,
    val fileName: String,
    val fileType: String,
    val description: String,
    val uploadStatus: Boolean
)
