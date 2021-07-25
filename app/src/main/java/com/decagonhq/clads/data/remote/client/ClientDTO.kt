package com.decagonhq.clads.data.remote.client

import com.decagonhq.clads.data.domain.client.DeliveryAddress
import com.decagonhq.clads.data.domain.client.Measurement

data class ClientDTO(
    val id: Int? = null,
    val artisanId: Int? = null,
    val email: String,
    val fullName: String,
    val gender: String,
    val phoneNumber: String,
    val deliveryAddresses: List<DeliveryAddress>? = null,
    val measurements: List<Measurement>? = null
)
