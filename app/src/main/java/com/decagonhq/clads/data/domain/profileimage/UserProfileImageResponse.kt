package com.decagonhq.clads.data.domain.profileimage

data class UserProfileImageResponse(
    val message: String,
    val payload: UserProfileImage,
    val status: Int
)