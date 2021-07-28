package com.decagonhq.clads.data.domain.client

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "client_details_table")
@Parcelize
data class Client(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val artisanId: Int? = null,
    val email: String,
    val fullName: String,
    val gender: String,
    val phoneNumber: String,
    val measurements: List<Measurement>? = null,
    val deliveryAddresses: List<DeliveryAddress>? = null
) : Parcelable
