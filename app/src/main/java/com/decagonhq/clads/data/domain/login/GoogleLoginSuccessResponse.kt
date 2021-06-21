package com.decagonhq.clads.data.domain.login

data class GoogleLoginSuccessResponse(
    val message: String,
    val payload: String,
    val status: Int
)
