package com.decagonhq.clads.data.domain.profile

import com.decagonhq.clads.data.domain.SpecialtyModel

data class UserProfile(
    val country: String,
    val deliveryTime: String? = null,
    val email: String,
    val firstName: String,
    val gender: String,
    val genderFocus: List<String>,
    val lastName: String,
    val measurementOption: MeasurementOption,
    val phoneNumber: String,
    val role: String,
    val workshopAddress: WorkshopAddress? = null,
    val showroomAddress: ShowroomAddress? = null,
    val specialties: List<SpecialtyModel>? = null,
    val thumbnail: String,
    val trained: Boolean,
    val union: Union? = null,
    val paymentTerms: List<String>? = null,
    val paymentOptions: List<String>? = null
)
