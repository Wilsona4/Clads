package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.domain.login.LoginCredentials
import com.decagonhq.clads.data.domain.login.UserRole
import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.data.domain.registration.UserRegistration
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun registerUser(user: UserRegistration): Flow<Resource<GenericResponseClass<UserProfile>>>
    suspend fun loginUser(loginCredentials: LoginCredentials): Flow<Resource<GenericResponseClass<String>>>
    suspend fun loginUserWithGoogle(userRole: UserRole?): Flow<Resource<GenericResponseClass<String>>>
    suspend fun verifyAuthToken(token: String): Flow<Resource<GenericResponseClass<String>>>
}
