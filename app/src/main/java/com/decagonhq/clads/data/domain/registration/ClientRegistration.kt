package com.decagonhq.clads.data.domain.registration

data class ClientRegistration(
    val email: String,
    val firstName: String,
    val gender: String,
    val lastName: String,
    val password: String,
    val phoneNumber: String
)
