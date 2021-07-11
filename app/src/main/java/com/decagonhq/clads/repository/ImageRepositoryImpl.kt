package com.decagonhq.clads.repository

import androidx.room.withTransaction
import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.domain.images.UserProfileImage
import com.decagonhq.clads.data.local.CladsDatabase
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.data.remote.images.ImageDTOMapper
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.SafeApiCall
import com.decagonhq.clads.util.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ImageRepositoryImpl(
    private val apiService: ApiService,
    private val imageDTOMapper: ImageDTOMapper,
    private val database: CladsDatabase
) : ImageRepository, SafeApiCall() {
    override suspend fun uploadMediaImage(image: MultipartBody.Part): Flow<Resource<UserProfileImage>> =
        networkBoundResource(
            fetchFromLocal = {
                database.profileImageDao().readUserProfileImage().map {
                    it
                }
            },
            shouldFetchFromRemote = {
                true
            },
            fetchFromRemote = {
                delay(2000)
                apiService.uploadImage(image)
            },
            saveToLocalDB = {
                database.withTransaction {
                    database.profileImageDao().deleteUserProfileImage()
                    database.profileImageDao().addUserProfileImage(
                        it.payload
                    )
                }
            }
        )

    override suspend fun getGalleryImage(): Flow<Resource<GenericResponseClass<List<UserProfileImage>>>> =
        flow {
            emit(
                safeApiCall {
                    apiService.getGalleryImages()
                }
            )
        }

    override suspend fun getUserImage(): Flow<Resource<UserProfileImage>> {
        return database.profileImageDao().readUserProfileImage().map {
            Resource.Success(it)
        }
    }

    override suspend fun uploadGalleryImage(image: MultipartBody.Part, description: String): Flow<Resource<GenericResponseClass<UserProfileImage>>> =
        flow {
            emit(
                safeApiCall {
                    apiService.uploadGalleryImage(image, description)
                }
            )
        }

    override suspend fun uploadGallery(requestBody: RequestBody): Flow<Resource<GenericResponseClass<UserProfileImage>>> =
        flow {
            emit(
                safeApiCall {
                    apiService.uploadGallery(requestBody)
                }
            )
        }
}
