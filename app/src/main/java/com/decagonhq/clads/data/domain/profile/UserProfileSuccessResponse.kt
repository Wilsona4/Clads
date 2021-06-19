package com.decagonhq.clads.data.domain.profile

data class UserProfileSuccessResponse(
    val message: String,
    val payload: UserProfile,
    val status: Int
)
