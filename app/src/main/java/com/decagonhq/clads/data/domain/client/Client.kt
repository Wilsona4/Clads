package com.decagonhq.clads.data.domain.client

import com.decagonhq.clads.data.domain.DeliveryAddressModel
import com.decagonhq.clads.data.domain.DressMeasurementModel

data class Client(
    val id: Int? = null,
    val artisanId:Int? = null,
    val email: String,
    val fullName: String,
    val gender: String,
    val phoneNumber: String,
    //val measurements: List<Measurement>? = null,
    val measurements: List<DressMeasurementModel>? = null,
    val deliveryAddresses: List<DeliveryAddress>? = null)

