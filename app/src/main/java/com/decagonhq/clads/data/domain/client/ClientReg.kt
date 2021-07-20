package com.decagonhq.clads.data.domain.client

// for first step registration
data class ClientReg(
    val id: Int? = null,
    val artisanId: Int? = null,
    val email: String,
    val fullName: String,
    val gender: String,
    val phoneNumber: String
)
