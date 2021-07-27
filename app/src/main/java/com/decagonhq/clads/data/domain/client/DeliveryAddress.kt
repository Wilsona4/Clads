package com.decagonhq.clads.data.domain.client

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeliveryAddress(
    @ColumnInfo(name = "deliveryAddress_street")
    val street: String? = null,

    @ColumnInfo(name = "deliveryAddress_city")
    val city: String? = null,

    @ColumnInfo(name = "deliveryAddress_state")
    val state: String? = null
) : Parcelable
