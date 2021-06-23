package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.domain.login.LoginCredentials
import com.decagonhq.clads.data.domain.login.UserRole
import com.decagonhq.clads.data.domain.registration.UserRegistration
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.data.remote.login.LoginCredentialsDTOMapper
import com.decagonhq.clads.data.remote.registration.UserRegDTOMapper
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody

class AuthRepositoryImpl constructor(
    private val apiService: ApiService,
    private val userRegDTOMapper: UserRegDTOMapper,
    private val loginCredentialsDTOMapper: LoginCredentialsDTOMapper,
) : AuthRepository, SafeApiCall() {

    override suspend fun registerUser(user: UserRegistration): Flow<Resource<GenericResponseClass>> =
        flow {
            emit(
                safeApiCall {
                    apiService.registerUser(userRegDTOMapper.mapFromDomainModel(user))
                }
            )
        }

    override suspend fun loginUser(loginCredentials: LoginCredentials): Flow<Resource<GenericResponseClass>> =
        flow {
            emit(
                safeApiCall {
                    apiService.login(loginCredentialsDTOMapper.mapFromDomainModel(loginCredentials))
                }
            )
        }

    override suspend fun loginUserWithGoogle(userRole: UserRole): Flow<Resource<GenericResponseClass>> =
        flow {
            emit(
                safeApiCall {
                    apiService.googleLogin(userRole)
                }
            )
        }
}
