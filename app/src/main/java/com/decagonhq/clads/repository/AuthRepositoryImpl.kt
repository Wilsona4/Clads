package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.domain.login.LoginCredentials
import com.decagonhq.clads.data.domain.login.UserRole
import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.data.domain.registration.UserRegistration
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.data.remote.login.LoginCredentialsDTOMapper
import com.decagonhq.clads.data.remote.registration.UserRegDTOMapper
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.SafeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val userRegDTOMapper: UserRegDTOMapper,
    private val loginCredentialsDTOMapper: LoginCredentialsDTOMapper,
) : AuthRepository, SafeApiCall() {

    override suspend fun registerUser(user: UserRegistration): Flow<Resource<GenericResponseClass<UserProfile>>> =
        flow {
            emit(
                safeApiCall {
                    apiService.registerUser(userRegDTOMapper.mapFromDomainModel(user))
                }
            )
        }

    override suspend fun loginUser(loginCredentials: LoginCredentials): Flow<Resource<GenericResponseClass<String>>> =
        flow {
            emit(
                safeApiCall {
                    apiService.login(loginCredentialsDTOMapper.mapFromDomainModel(loginCredentials))
                }
            )
        }

    override suspend fun loginUserWithGoogle(userRole: UserRole?): Flow<Resource<GenericResponseClass<String>>> =
        flow {
            emit(
                safeApiCall {
                    if (userRole != null) {
                        apiService.googleLogin(userRole)
                    } else {
                        apiService.googleLogin()
                    }
                }
            )
        }

    override suspend fun verifyAuthToken(token: String): Flow<Resource<GenericResponseClass<String>>> =
        flow {
            emit(
                safeApiCall {
                    apiService.verifyAuthToken(token)
                }
            )
        }
}
