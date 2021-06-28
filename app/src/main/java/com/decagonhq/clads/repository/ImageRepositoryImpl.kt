package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.domain.images.UserProfileImage
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.data.remote.ImageDTOMapper
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody

class ImageRepositoryImpl(
    private val apiService: ApiService,
    private val imageDTOMapper: ImageDTOMapper
) : ImageRepository, SafeApiCall() {
    override suspend fun uploadMediaImage(image: MultipartBody.Part): Flow<Resource<GenericResponseClass<UserProfileImage>>> =
        flow {
            emit(
                safeApiCall {
                    apiService.uploadImage(image)
                }
            )
        }

    override suspend fun getUserImage(): Flow<Resource<GenericResponseClass<UserProfileImage>>> =
        flow {
            emit(
                safeApiCall {
                    apiService.getUploadedImage()
                }
            )
        }
}
