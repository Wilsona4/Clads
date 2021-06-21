package com.decagonhq.clads.data.domain.profile

data class UserProfile(
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
