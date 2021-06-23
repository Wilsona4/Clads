package com.decagonhq.clads.data.remote.profile

import com.decagonhq.clads.data.domain.profile.ShowroomAddress
import com.decagonhq.clads.data.domain.profile.Union
import com.decagonhq.clads.data.domain.profile.WorkshopAddress

data class UserProfileDTO(
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
