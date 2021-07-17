package com.decagonhq.clads.data.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class DressMeasurementModel(
//    val measurementName: String,
//    val measurement: BigDecimal
    val title: String,
    val value: Int
) : Parcelable
