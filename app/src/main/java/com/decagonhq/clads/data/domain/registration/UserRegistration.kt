package com.decagonhq.clads.data.domain.registration

data class UserRegistration(
    val category: String,
    val country: String,
    val deliveryAddress: String,
    val email: String,
    val firstName: String,
    val gender: String,
    val lastName: String,
    val password: String,
    val phoneNumber: String,
    val role: String,
    val thumbnail: String
)
