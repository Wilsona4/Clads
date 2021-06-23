package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.data.remote.ImageDTOMapper
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ImageRepositoryImpl(
    private val apiService: ApiService,
    private val imageDTOMapper: ImageDTOMapper
) : ImageRepository, SafeApiCall() {

    override suspend fun uploadMediaImage(image: RequestBody): Flow<Resource<GenericResponseClass>> =
        flow {
            emit(
                safeApiCall {
                    apiService.uploadImage(image)
                }
            )
        }
}

