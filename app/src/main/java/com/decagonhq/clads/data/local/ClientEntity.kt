package com.decagonhq.clads.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.decagonhq.clads.data.domain.DeliveryAddressModel
import com.decagonhq.clads.data.domain.DressMeasurementModel
import com.decagonhq.clads.data.domain.client.DeliveryAddress
import com.decagonhq.clads.data.domain.client.Measurement

@Entity(tableName = "client_details_table")
class ClientEntity(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val artisanId:Int,
    val deliveryAddresses: List<DeliveryAddress>? = null,
    val email: String,
    val fullName: String,
    val gender: String,
    val phoneNumber: String,
    //val measurements: List<Measurement>? = null
    val measurements: List<DressMeasurementModel>? = null
)