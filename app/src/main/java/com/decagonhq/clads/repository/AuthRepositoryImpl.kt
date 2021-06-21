package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.login.EmailLoginSuccessResponse
import com.decagonhq.clads.data.domain.login.GoogleLoginSuccessResponse
import com.decagonhq.clads.data.domain.login.LoginCredentials
import com.decagonhq.clads.data.domain.login.UserRole
import com.decagonhq.clads.data.domain.profileimage.UserProfileImageResponse
import com.decagonhq.clads.data.domain.registration.UserRegSuccessResponse
import com.decagonhq.clads.data.domain.registration.UserRegistration
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.data.remote.LoginCredentialsDTOMapper
import com.decagonhq.clads.data.remote.UserRegDTOMapper
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody

class AuthRepositoryImpl constructor(
    private val apiService: ApiService,
    private val userRegDTOMapper: UserRegDTOMapper,
    private val loginCredentialsDTOMapper: LoginCredentialsDTOMapper,

    ) : AuthRepository, SafeApiCall() {

    override suspend fun registerUser(user: UserRegistration): Flow<Resource<UserRegSuccessResponse>> =
        flow {
            emit(
                safeApiCall {
                    apiService.registerUser(userRegDTOMapper.mapFromDomainModel(user))
                }
            )
        }

    override suspend fun loginUser(loginCredentials: LoginCredentials): Flow<Resource<EmailLoginSuccessResponse>> =
        flow {
            emit(
                safeApiCall {
                    apiService.login(loginCredentialsDTOMapper.mapFromDomainModel(loginCredentials))
                }
            )
        }

    override suspend fun loginUserWithGoogle(userRole: UserRole): Flow<Resource<GoogleLoginSuccessResponse>> =
        flow {
            emit(
                safeApiCall {
                    apiService.googleLogin(userRole)
                }
            )
        }

    override suspend fun userProfileImage(userProfileImage: MultipartBody.Part): Flow<Resource<UserProfileImageResponse>> =
        flow {
            emit(
                safeApiCall {
                    apiService.uploadImage(userProfileImage)
                }
            )
        }
}
