package com.decagonhq.clads.data.remote

import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.domain.login.UserRole
import com.decagonhq.clads.data.remote.login.LoginCredentialsDTO
import com.decagonhq.clads.data.remote.registration.UserRegistrationDTO
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    /*Register User */
    @POST("artisans/register")
    suspend fun registerUser(
        @Body user: UserRegistrationDTO
    ): GenericResponseClass

    /*Email Login*/
    @POST("login")
    suspend fun login(
        @Body loginCredentials: LoginCredentialsDTO
    ): GenericResponseClass

    /*Google Login*/
    @POST("login/google")
    suspend fun googleLogin(
        @Body userRole: UserRole
    ): GenericResponseClass

    /*Upload Profile Picture*/
    @Multipart
    @POST("upload")
    suspend fun uploadImage(@Part image: MultipartBody.Part): GenericResponseClass

    /*Get User Profile*/
    @GET("me/profile")
    suspend fun getUserProfile(): GenericResponseClass
}
