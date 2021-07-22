package com.decagonhq.clads.repository

import androidx.room.withTransaction
import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.data.local.CladsDatabase
import com.decagonhq.clads.data.local.UserProfileEntity
import com.decagonhq.clads.data.local.UserProfileEntityMapper
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.data.remote.profile.UserProfileDTOMapper
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.SafeApiCall
import com.decagonhq.clads.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserProfileRepositoryImpl(
    private val apiService: ApiService,
    private val userProfileDTOMapper: UserProfileDTOMapper,
    private val userProfileEntityMapper: UserProfileEntityMapper,
    private val database: CladsDatabase
) : UserProfileRepository, SafeApiCall() {

    override suspend fun getUserProfile(): Flow<Resource<UserProfileEntity>> =
        networkBoundResource(
            fetchFromLocal = {
                database.userProfileDao().readUserProfile()
//                    .map {
//                    userProfileEntityMapper.mapToDomainModel(it)
//                }
            },
            shouldFetchFromRemote = {
                true
            },
            fetchFromRemote = {
//                delay(2000)
                apiService.getUserProfile()
            },
            saveToLocalDB = {
                database.withTransaction {
                    database.userProfileDao().deleteUserProfile()
                    database.userProfileDao().addUserProfile(
                        it.payload
//                        userProfileEntityMapper.mapFromDomainModel(it.payload)
                    )
                }
            }
        )

    override suspend fun getLocalDatabaseUserProfile(): Flow<Resource<UserProfileEntity>> {
        return database.userProfileDao().readUserProfile().map {
            Resource.Success(it)
//            Resource.Success(userProfileEntityMapper.mapToDomainModel(it))
        }
    }

    override suspend fun updateUserProfile(userProfile: UserProfile) {
        val response = safeApiCall {
            apiService.updateUserProfile(userProfile)
        }
        if (response is Resource.Success) {
            database.withTransaction {
                database.userProfileDao().deleteUserProfile()
                response.data?.payload?.let { userProfileEntityMapper.mapFromDomainModel(it) }
                    ?.let {
                        database.userProfileDao().addUserProfile(
                            it
                        )
                    }
            }
        }
    }

    override suspend fun saveUserProfileToLocalDatabase() {
        val response = safeApiCall {
            apiService.getUserProfile()
        }
        if (response is Resource.Success) {
            database.withTransaction {
                database.userProfileDao().deleteUserProfile()
                response.data?.payload?.let {
                    database.userProfileDao().addUserProfile(
                        it
                    )
                }
            }
        }
    }
}
