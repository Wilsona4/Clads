package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.data.local.UserProfileDao
import com.decagonhq.clads.data.local.UserProfileEntityMapper
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.data.remote.profile.UserProfileDTOMapper
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserProfileRepositoryImpl(
    private val apiService: ApiService,
    private val userProfileDTOMapper: UserProfileDTOMapper,
    private val userProfileEntityMapper: UserProfileEntityMapper,
    private val userProfileDao: UserProfileDao
) : UserProfileRepository, SafeApiCall() {

    override suspend fun getUserProfile(): Flow<Resource<GenericResponseClass<UserProfile>>> =
        flow {
            val response = safeApiCall {
                val response = apiService.getUserProfile()
                userProfileDao.addUserProfile(userProfileEntityMapper.mapFromDomainModel(response.payload))
            }
            emit(
                safeApiCall {
                    apiService.getUserProfile()
                }
            )
        }

    override suspend fun updateUserProfile(userProfile: UserProfile): Flow<Resource<GenericResponseClass<UserProfile>>> =
        flow {
            emit(
                safeApiCall {
                    apiService.getUserProfile()
                }
            )
        }
}
