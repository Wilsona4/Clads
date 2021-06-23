package com.decagonhq.clads.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.decagonhq.clads.data.domain.profile.ShowroomAddress
import com.decagonhq.clads.data.domain.profile.Union
import com.decagonhq.clads.data.domain.profile.WorkshopAddress

@Entity(tableName = "user_profile_table")
data class UserProfileEntity(
    val country: String,
    val email: String,
    val firstName: String,
    val gender: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val lastName: String,
    val phoneNumber: String,
    val role: String,
    @Embedded(prefix = "user_profile__table_showroom_address")
    val showroomAddress: ShowroomAddress,
    val thumbnail: String,
    @Embedded(prefix = "user_profile_table_union")
    val union: Union,
    @Embedded(prefix = "user_profile_table_workshop_address")
    val workshopAddress: WorkshopAddress
)
