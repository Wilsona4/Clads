package com.decagonhq.clads.data.remote.client

import com.decagonhq.clads.data.domain.DeliveryAddressModel
import com.decagonhq.clads.data.domain.DressMeasurementModel

data class ClientDTO(
    val id: Int? = null,
    val artisanId: Int? = null,
    val email: String,
    val fullName: String,
    val gender: String,
    val phoneNumber: String,
    val deliveryAddresses: List<DeliveryAddressModel>? = null,
    val measurements: List<DressMeasurementModel>? = null
)
