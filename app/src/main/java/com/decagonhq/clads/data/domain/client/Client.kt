package com.decagonhq.clads.data.domain.client

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "client_details_table")
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
)
