package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.images.UserProfileImage
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ImageRepository {
    suspend fun uploadMediaImage(image: MultipartBody.Part): Flow<Resource<UserProfileImage>>
    suspend fun getUserImage(): Flow<Resource<UserProfileImage>>
}
