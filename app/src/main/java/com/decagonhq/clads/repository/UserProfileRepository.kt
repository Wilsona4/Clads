package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    suspend fun getUserProfile(): Flow<Resource<GenericResponseClass>>
    suspend fun updateUserProfile(): Flow<Resource<GenericResponseClass>>
}
