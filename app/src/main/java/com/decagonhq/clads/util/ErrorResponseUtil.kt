package com.decagonhq.clads.util

import android.util.Log
import com.decagonhq.clads.data.domain.GenericResponseClass
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class ErrorResponseUtil @Inject constructor(var retrofit: Retrofit) {
    fun parseError(response: Response<*>): GenericResponseClass {
        val converter: Converter<ResponseBody, GenericResponseClass> = retrofit.responseBodyConverter(GenericResponseClass::class.java, arrayOfNulls<Annotation>(0))
        return try {
            Timber.d(response.message())
            converter.convert(response.errorBody()!!)!!
        } catch (e: IOException) {
            Timber.d(e.message)
            return GenericResponseClass("IOException", "null", 69)
//            converter.convert(response.errorBody())!!
        }
    }
}
