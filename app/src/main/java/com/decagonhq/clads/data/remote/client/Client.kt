package com.decagonhq.clads.data.remote.client

import com.decagonhq.clads.data.domain.DeliveryAddressModel

data class Client(
    val id: Int,
    val artisanId:Int,
    val fullName: String,
    val phoneNumber: String,
    val email: String,
    val gender: String,
    val deliveryAddresses: List<DeliveryAddressModel>? =null,
    val measurements: List<Measurement>? = null
)