package com.decagonhq.clads.data.domain.login

data class GoogleLoginErrorResponse(
    val error: String,
    val message: String,
    val status: Int
)
