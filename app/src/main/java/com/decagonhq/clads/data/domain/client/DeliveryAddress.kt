package com.decagonhq.clads.data.domain.client

import androidx.room.ColumnInfo

data class DeliveryAddress(
    @ColumnInfo(name = "deliveryaddress_address")
    val street: String? = null,

    @ColumnInfo(name = "deliveryaddress_city")
    val city: String? = null,

    @ColumnInfo(name = "deliveryaddress_state")
    val state: String? = null)