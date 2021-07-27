package com.decagonhq.clads.repository

import androidx.lifecycle.MutableLiveData
import androidx.room.withTransaction
import com.decagonhq.clads.data.domain.images.UserGalleryImage
import com.decagonhq.clads.data.domain.images.UserProfileImage
import com.decagonhq.clads.data.local.CladsDatabase
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.data.remote.images.ImageDTOMapper
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.SafeApiCall
import com.decagonhq.clads.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ImageRepositoryImpl(
    private val mainApiService: ApiService,
    private val imageApiService: ApiService,
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
                imageApiService.uploadImage(image)
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

    override suspend fun getUserImage(): Flow<Resource<UserProfileImage>> {
        return database.profileImageDao().readUserProfileImage().map {
            Resource.Success(it)
        }
    }

    override suspend fun getRemoteGalleryImage() {
        val response = safeApiCall {
            mainApiService.getGalleryImages()
        }
        if (response is Resource.Success) {
            database.withTransaction {
                response.data?.payload?.let { galleryImageList ->
                    for (galleryImage in galleryImageList) {
                        database.galleryImageDao().addUserGalleryImage(galleryImage)
                    }
                }
            }
        }
    }

    override suspend fun uploadGallery(requestBody: RequestBody): Flow<Resource<List<UserGalleryImage>>> =
        networkBoundResource(
            fetchFromLocal = {
                database.galleryImageDao().readUserGalleryImage().map {
                    it
                }
            },
            shouldFetchFromRemote = {
                true
            },
            fetchFromRemote = {
                mainApiService.uploadGallery(requestBody)
            },
            saveToLocalDB = {
                database.withTransaction {
                    database.galleryImageDao().addUserGalleryImage(
                        it.payload
                    )
                }
            }
        )

    override suspend fun getLocalDatabaseGalleryImages(): Flow<Resource<List<UserGalleryImage>>> =
        flow {
            database.galleryImageDao().readUserGalleryImage().collect {
                emit(Resource.Success(it))
            }
        }

    override suspend fun editDescription(
        fileId: String,
        requestBody: RequestBody,
    ): Flow<Resource<List<UserGalleryImage>>> =
        networkBoundResource(
            fetchFromLocal = {
                database.galleryImageDao().readUserGalleryImage().map {
                    it
                }
            },
            shouldFetchFromRemote = {
                true
            },
            fetchFromRemote = {
                mainApiService.editDescription(fileId, requestBody)
            },
            saveToLocalDB = {
                database.withTransaction {
                    database.galleryImageDao().updateUserGalleryImage(
                        it.payload
                    )
                }
            }
        )

    override suspend fun deleteGalleryImage(fileId: String, result: MutableLiveData<String>) {
        val response = safeApiCall {
            mainApiService.deleteGalleryImage(fileId)
        }
        if (response is Resource.Success) {
            database.galleryImageDao().deleteUserGalleryImage(fileId)
            result.postValue("Deleted successfully")
        }
    }
}
