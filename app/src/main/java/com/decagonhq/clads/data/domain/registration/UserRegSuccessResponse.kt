package com.decagonhq.clads.data.domain.registration

data class UserRegSuccessResponse(
    val status: Int,
    val message: String,
    val payload: String
)
