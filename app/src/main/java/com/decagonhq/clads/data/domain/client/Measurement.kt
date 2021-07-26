package com.decagonhq.clads.data.domain.client

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Measurement(
    @ColumnInfo(name = "measurement_title")
    val title: String? = null,

    @ColumnInfo(name = "measurement_value")
    val value: Int
) : Parcelable
