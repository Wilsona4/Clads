package com.decagonhq.clads.util

import com.decagonhq.clads.data.domain.GenericResponseClass
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject

class ErrorResponseUtil @Inject
constructor
(var retrofit: Retrofit) {
    fun parseError(response: Response<*>): GenericResponseClass<String> {
        val converter: Converter<ResponseBody, GenericResponseClass<String>> = retrofit
            .responseBodyConverter(GenericResponseClass::class.java, arrayOfNulls<Annotation>(0))

        return try {
            converter.convert(response.errorBody()!!)!!
        } catch (e: IOException) {
            return GenericResponseClass("IO Exception ", "null", 69)
        }
    }
}
