package com.decagonhq.clads.data.domain.profile

data class UserProfile(
    val country: String,
    val deliveryTime: Any,
    val email: String,
    val firstName: String,
    val gender: String,
    val genderFocus: List<String>,
    val id: Int,
    val lastName: String,
    val measurementOption: MeasurementOption,
    val phoneNumber: String,
    val role: String,
    val showroomAddress: ShowroomAddress,
    val specialties: List<Any>,
    val thumbnail: String,
    val trained: Boolean,
    val union: Union,
    val workshopAddress: WorkshopAddress
)