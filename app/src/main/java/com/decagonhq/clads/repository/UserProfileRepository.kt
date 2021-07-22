package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.data.local.UserProfileEntity
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    suspend fun getUserProfile(): Flow<Resource<UserProfileEntity>>
    suspend fun getLocalDatabaseUserProfile(): Flow<Resource<UserProfileEntity>>
    suspend fun updateUserProfile(userProfile: UserProfile)
    suspend fun saveUserProfileToLocalDatabase()
}
