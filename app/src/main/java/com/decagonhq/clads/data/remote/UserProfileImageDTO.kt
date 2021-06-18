package com.decagonhq.clads.data.remote

import com.google.gson.annotations.SerializedName

class UserProfileImageDTO(
    @SerializedName("downloadUri")
    val downloadUri: String,
    @SerializedName("fileId")
    val fileId: String,
    @SerializedName("fileName")
    val fileName: String,
    @SerializedName("fileType")
    val fileType: String,
    @SerializedName("uploadStatus")
    val uploadStatus: Boolean
)