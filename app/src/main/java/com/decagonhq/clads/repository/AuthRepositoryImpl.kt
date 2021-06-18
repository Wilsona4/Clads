package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.login.EmailLoginSuccessResponse
import com.decagonhq.clads.data.domain.login.LoginCredentials
import com.decagonhq.clads.data.domain.registration.UserRegSuccessResponse
import com.decagonhq.clads.data.domain.registration.UserRegistration
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.data.remote.LoginCredentialsDTOMapper
import com.decagonhq.clads.data.remote.UserRegDTOMapper
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl constructor(
    private val apiService: ApiService,
    private val userRegDTOMapper: UserRegDTOMapper,
    private val loginCredentialsDTOMapper: LoginCredentialsDTOMapper
) : AuthRepository, SafeApiCall() {

    override suspend fun registerUser(user: UserRegistration): Flow<Resource<UserRegSuccessResponse>> =
        flow {
            safeApiCall {
                apiService.registerUser(userRegDTOMapper.mapFromDomainModel(user))
            }
        }

    override suspend fun loginUser(loginCredentials: LoginCredentials): Flow<Resource<EmailLoginSuccessResponse>> = flow {
        safeApiCall {
            apiService.login(loginCredentialsDTOMapper.mapFromDomainModel(loginCredentials))
        }
    }
}
