package com.decagonhq.clads.data.domain.login

data class EmailLoginErrorResponse(
    val error: String,
    val message: String,
    val status: Int
)
