package com.decagonhq.clads.data.domain.client

import androidx.room.ColumnInfo

data class Measurement(
    @ColumnInfo(name = "measurement_title")
    val title: String? = null,

    @ColumnInfo(name = "measurement_value")
    val value: Int
)
