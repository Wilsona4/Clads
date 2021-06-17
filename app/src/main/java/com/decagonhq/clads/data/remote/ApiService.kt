package com.decagonhq.clads.data.remote

import com.decagonhq.clads.data.domain.registration.UserRegSuccessResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("artisans/register")
    suspend fun registerUser(
        @Body user: UserRegistrationDTO
    ): UserRegSuccessResponse
}
