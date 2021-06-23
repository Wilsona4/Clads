package com.decagonhq.clads.repository

import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ImageRepository {
    suspend fun uploadMediaImage(image: RequestBody): Flow<Resource<GenericResponseClass>>
}
