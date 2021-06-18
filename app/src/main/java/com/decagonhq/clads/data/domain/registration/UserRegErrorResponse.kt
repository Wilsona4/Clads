package com.decagonhq.clads.data.domain.registration

data class UserRegErrorResponse(
    val error: String,
    val message: String,
    val status: Int
)
