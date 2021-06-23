package com.decagonhq.clads.data.remote.registration

import com.google.gson.annotations.SerializedName

data class UserRegistrationDTO(
    val category: String,
    val country: String,
    @SerializedName("deliveryddress")
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
