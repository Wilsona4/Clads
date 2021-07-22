package com.decagonhq.clads.repository

import androidx.lifecycle.MutableLiveData
import com.decagonhq.clads.data.domain.images.UserGalleryImage
import com.decagonhq.clads.data.domain.images.UserProfileImage
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ImageRepository {

    suspend fun uploadMediaImage(image: MultipartBody.Part): Flow<Resource<UserProfileImage>>
    suspend fun getUserImage(): Flow<Resource<UserProfileImage>>
    suspend fun getRemoteGalleryImage()
    suspend fun uploadGallery(requestBody: RequestBody): Flow<Resource<List<UserGalleryImage>>>
    suspend fun getLocalDatabaseGalleryImages(): Flow<Resource<List<UserGalleryImage>>>
    suspend fun editDescription(
        fileId: String,
        requestBody: RequestBody,
    ): Flow<Resource<List<UserGalleryImage>>>

    suspend fun deleteGalleryImage(fileId: String, result: MutableLiveData<String>)
}
