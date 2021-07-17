package com.decagonhq.clads.util

import com.decagonhq.clads.data.domain.DeliveryAddressModel
import androidx.room.TypeConverter
import com.decagonhq.clads.data.domain.DressMeasurementModel
import com.decagonhq.clads.data.domain.client.DeliveryAddress
import com.decagonhq.clads.data.domain.client.Measurement
import com.google.gson.Gson

class TypeConverter {

    @TypeConverter
    fun AddressListToJsonString(value: List<DeliveryAddress>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToAddressList(value: String) = Gson().fromJson(value, Array<DeliveryAddress>::class.java).toList()
    @TypeConverter
    //fun MeasurementListToJsonString(value: List<Measurement>?): String = Gson().toJson(value)
    fun MeasurementListToJsonString(value: List<DressMeasurementModel>?): String = Gson().toJson(value)

    @TypeConverter
    //fun jsonStringToMeasurementList(value: String) = Gson().fromJson(value, Array<Measurement>::class.java).toList()
    fun jsonStringToMeasurementList(value: String) = Gson().fromJson(value, Array<DressMeasurementModel>::class.java).toList()


}