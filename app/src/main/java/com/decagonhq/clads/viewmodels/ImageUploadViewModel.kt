package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.clads.data.domain.images.UserGalleryImage
import com.decagonhq.clads.data.domain.images.UserProfileImage
import com.decagonhq.clads.repository.ImageRepository
import com.decagonhq.clads.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ImageUploadViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private var _userProfileImage = MutableLiveData<Resource<UserProfileImage>>()
    val userProfileImage: LiveData<Resource<UserProfileImage>> get() = _userProfileImage

    private var _uploadGallery = MutableLiveData<Resource<List<UserGalleryImage>>>()
    val uploadGallery: LiveData<Resource<List<UserGalleryImage>>> get() = _uploadGallery

    init {
        getUserProfileImage()
        getRemoteGalleryImages()
    }

    /*Upload Profile Picture*/
    fun mediaImageUpload(image: MultipartBody.Part) {
        viewModelScope.launch {
            _userProfileImage.value = Resource.Loading(null, "Uploading...")
            val response = imageRepository.uploadMediaImage(image)
            response.collect {
                _userProfileImage.value = it
            }
        }
    }

    /*Get Profile Picture from Room Data*/
    fun getUserProfileImage() {
        viewModelScope.launch {
            val response = imageRepository.getUserImage()
            response.collect {
                _userProfileImage.value = it
            }
        }
    }

    /*Get Gallery Images*/
    fun getRemoteGalleryImages() {
        viewModelScope.launch {
            imageRepository.getRemoteGalleryImage()
        }
    }

    /*Upload Gallery Image*/
    fun uploadGallery(requestBody: RequestBody) {
        viewModelScope.launch {
            _uploadGallery.value = Resource.Loading(null, "uploading...")
//            imageRepository.uploadGallery(requestBody)
            val response = imageRepository.uploadGallery(requestBody)
            response.collect {
                _uploadGallery.value = it
            }
        }
    }

    fun getLocalDatabaseGalleryImages() {
        viewModelScope.launch {
            imageRepository.getLocalDatabaseGalleryImages().collect {
                _uploadGallery.value = it
            }
        }
    }

    fun deleteGalleryImage(fileId: String) {
        viewModelScope.launch {
            _uploadGallery.value = Resource.Loading(null, "Deleting...")
            val response = imageRepository.deleteGalleryImage(fileId)
        }
    }

    fun editGalleryImage(fileId: String, requestBody: RequestBody) {
        viewModelScope.launch {
            _uploadGallery.value = Resource.Loading(null, "uploading...")
            val response = imageRepository.editDescription(fileId,  requestBody)
            response.collect {
                _uploadGallery.value = it
            }
        }
    }
}
