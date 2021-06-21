package com.decagonhq.clads.data.remote

import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.domain.login.UserRole
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("artisans/register")
    suspend fun registerUser(
        @Body user: UserRegistrationDTO
    ): GenericResponseClass

    @POST("login")
    suspend fun login(
        @Body loginCredentials: LoginCredentialsDTO
    ): GenericResponseClass

    @POST("login/google")
    suspend fun googleLogin(
        @Body userRole: UserRole
    ): GenericResponseClass

    @Multipart
    @POST("upload")
    fun uploadImage(@Part image: MultipartBody.Part): GenericResponseClass
}
