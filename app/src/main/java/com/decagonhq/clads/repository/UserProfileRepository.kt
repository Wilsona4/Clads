package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    suspend fun getUserProfile(): Flow<Resource<UserProfile>>
    suspend fun getLocalDatabaseUserProfile(): Flow<Resource<UserProfile>>
    suspend fun updateUserProfile(userProfile: UserProfile)
    suspend fun saveUserProfileToLocalDatabase()
}
