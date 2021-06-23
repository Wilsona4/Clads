package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.data.domain.userprofile.Userprofile
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    suspend fun getUserProfile(): Flow<Resource<Userprofile>>
    suspend fun updateUserProfile(userProfile: UserProfile): Flow<Resource<GenericResponseClass>>
}
