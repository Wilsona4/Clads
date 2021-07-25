package com.decagonhq.clads.data.domain.client

import androidx.room.ColumnInfo

data class DeliveryAddress(
    @ColumnInfo(name = "deliveryAddress_street")
    val street: String? = null,

    @ColumnInfo(name = "deliveryAddress_city")
    val city: String? = null,

    @ColumnInfo(name = "deliveryAddress_state")
    val state: String? = null
)
