package com.decagonhq.clads.data.domain.profile

import androidx.room.ColumnInfo

data class ShowroomAddress(
    @ColumnInfo(name = "showroom_address_city")
    val city: String? = null,
    @ColumnInfo(name = "showroom_address_state")
    val state: String? = null,
    @ColumnInfo(name = "showroom_address_street")
    val street: String? = null
)
