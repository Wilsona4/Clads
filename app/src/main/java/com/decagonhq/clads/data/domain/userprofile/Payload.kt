package com.decagonhq.clads.data.domain.userprofile

data class Payload(
    val country: String,
    val email: String,
    val firstName: String,
    val gender: String,
    val id: Int,
    val lastName: String,
    val phoneNumber: String,
    val role: String,
    val showroomAddress: ShowroomAddress,
    val thumbnail: String,
    val union: Union,
    val workshopAddress: WorkshopAddress
)