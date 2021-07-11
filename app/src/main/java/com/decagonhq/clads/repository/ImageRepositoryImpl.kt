package com.decagonhq.clads.repository

import androidx.room.withTransaction
import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.domain.images.UserGalleryImage
import com.decagonhq.clads.data.domain.images.UserProfileImage
import com.decagonhq.clads.data.local.CladsDatabase
import com.decagonhq.clads.data.remote.ApiService
import com.decagonhq.clads.data.remote.images.ImageDTOMapper
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.SafeApiCall
import com.decagonhq.clads.util.networkBoundResource
import kotlinx.coroutines.delay
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
                delay(2000)
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

    override suspend fun getGalleryImage(): Flow<Resource<List<UserGalleryImage>>> {
        return database.galleryImageDao().readUserGalleryImage().map{
            Resource.Success(it)
        }
    }

//        networkBoundResource(
//            fetchFromLocal = {
//                database.galleryImageDao().readUserGalleryImage().map {
//                    it
//                }
//            },
//            shouldFetchFromRemote = {
//                true
//            },
//            fetchFromRemote = {
//                mainApiService.getGalleryImages()
//            },
//            saveToLocalDB = { galleryImageList ->
//                for (galleryImage in galleryImageList.payload) {
//                    database.galleryImageDao().addUserGalleryImage(galleryImage)
//                }
//            }
//        )


    override suspend fun getUserImage(): Flow<Resource<UserProfileImage>> {
        return database.profileImageDao().readUserProfileImage().map {
            Resource.Success(it)
        }
    }

    override suspend fun uploadGallery(requestBody: RequestBody): Flow<Resource<List<UserGalleryImage>>> =
//        flow {
//            val response = safeApiCall {
//                mainApiService.uploadGallery(requestBody)
//            }
//            if (response is Resource.Success) {
//                response.data?.payload?.let {
//                    database.galleryImageDao().addUserGalleryImage(it)
//                }
//
//                database.galleryImageDao().readUserGalleryImage().collect {
//                    emit(Resource.Success(it))
//                }
//            }
//        }

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
                database.galleryImageDao().addUserGalleryImage(it.payload)
            }
        )
}
