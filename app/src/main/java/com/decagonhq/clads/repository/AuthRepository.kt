package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.domain.login.LoginCredentials
import com.decagonhq.clads.data.domain.login.UserRole
import com.decagonhq.clads.data.domain.registration.UserRegistration
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface AuthRepository {
    suspend fun registerUser(user: UserRegistration): Flow<Resource<GenericResponseClass>>
    suspend fun loginUser(loginCredentials: LoginCredentials): Flow<Resource<GenericResponseClass>>
    suspend fun userProfileImage(userProfileImage: MultipartBody.Part): Flow<Resource<GenericResponseClass>>
    suspend fun loginUserWithGoogle(userRole: UserRole): Flow<Resource<GenericResponseClass>>
}
