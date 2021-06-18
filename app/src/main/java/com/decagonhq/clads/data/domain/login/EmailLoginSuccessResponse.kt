package com.decagonhq.clads.data.domain.login

data class EmailLoginSuccessResponse(
    val message: String,
    val payload: String,
    val status: Int
)