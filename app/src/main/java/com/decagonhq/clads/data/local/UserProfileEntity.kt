package com.decagonhq.clads.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.decagonhq.clads.data.domain.profile.MeasurementOption
import com.decagonhq.clads.data.domain.profile.ShowroomAddress
import com.decagonhq.clads.data.domain.profile.Union
import com.decagonhq.clads.data.domain.profile.WorkshopAddress

@Entity(tableName = "user_profile_table")
data class UserProfileEntity(
    val country: String?,
    val deliveryTime: String? = null,
    val email: String,
    val firstName: String,
    val gender: String?,
    val genderFocus: List<String>,
    val lastName: String,
    @Embedded(prefix = "user_profile_table_measurement_options")
    val measurementOption: MeasurementOption,
    val phoneNumber: String?,
    val role: String,
    @Embedded(prefix = "user_profile_table_showroom_address")
    val showroomAddress: ShowroomAddress? = null,
    val specialties: List<String>? = null,
    val thumbnail: String,
    val trained: Boolean,
    @Embedded(prefix = "user_profile_table_union")
    val union: Union? = null,
    @Embedded(prefix = "user_profile_table_workshop_address")
    val workshopAddress: WorkshopAddress? = null,
    val paymentTerms: List<String>? = null,
    val paymentOptions: List<String>? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
