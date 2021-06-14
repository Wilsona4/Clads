package com.decagonhq.clads.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal
@Parcelize
data class DressMeasurementModel(
    val measurementName: String,
    val measurement: BigDecimal
) : Parcelable
