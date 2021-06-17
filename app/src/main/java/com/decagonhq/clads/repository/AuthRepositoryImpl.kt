package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.registration.UserRegSuccessResponse
import com.decagonhq.clads.data.domain.registration.UserRegistration
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.data.remote.UserRegDTOMapper
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl constructor(
    private val apiService: ApiService,
    private val userRegDTOMapper: UserRegDTOMapper
) : AuthRepository, SafeApiCall() {

    override suspend fun registerUser(user: UserRegistration): Flow<Resource<UserRegSuccessResponse>> =
        flow {
            safeApiCall {
                apiService.registerUser(userRegDTOMapper.mapFromDomainModel(user))
            }
        }
}
