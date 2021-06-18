package com.decagonhq.clads.data.remote

import com.google.gson.annotations.SerializedName

data class LoginCredentialsDTO(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)