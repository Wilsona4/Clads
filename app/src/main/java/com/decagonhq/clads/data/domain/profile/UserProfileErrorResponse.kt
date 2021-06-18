package com.decagonhq.clads.data.domain.profile

data class UserProfileErrorResponse(
    val error: String,
    val message: String,
    val status: Int
)
