package com.decagonhq.clads.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.decagonhq.clads.data.domain.GenericResponseClass
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
) : AndroidViewModel(Application()) {
    private var _userProfileImage = MutableLiveData<Resource<GenericResponseClass<UserProfileImage>>>()
    val userProfileImage: LiveData<Resource<GenericResponseClass<UserProfileImage>>> get() = _userProfileImage
    private var _uploadGalleryImage = MutableLiveData<Resource<GenericResponseClass<UserProfileImage>>>()
    val uploadGalleryImage: LiveData<Resource<GenericResponseClass<UserProfileImage>>> get() = _uploadGalleryImage

    private var _uploadGallery = MutableLiveData<Resource<GenericResponseClass<UserProfileImage>>>()
    val uploadGallery: LiveData<Resource<GenericResponseClass<UserProfileImage>>> get() = _uploadGallery

    init {
        getUserImage()
    }

    fun mediaImageUpload(image: MultipartBody.Part) {
        viewModelScope.launch {
            _userProfileImage.value = Resource.Loading(null, "Uploading...")
            val response = imageRepository.uploadMediaImage(image)
            response.collect {
                _userProfileImage.value = it
            }
        }
    }

    fun getUserImage() {
        viewModelScope.launch {
            _userProfileImage.value = Resource.Loading(null, "Loading...")
            val response = imageRepository.getUserImage()
            response.collect {
                _userProfileImage.value = it
            }
        }
    }
    fun uploadGalleryImage(image: MultipartBody.Part, description: String) {
        viewModelScope.launch {
            _uploadGalleryImage.value = Resource.Loading(null, "uploading...")
            val response = imageRepository.uploadGalleryImage(image, description)
            response.collect {
                _uploadGalleryImage.value = it
            }
        }
    }

    fun uploadGallery(requestBody: RequestBody) {
        viewModelScope.launch {
            _uploadGalleryImage.value = Resource.Loading(null, "uploading...")
            val response = imageRepository.uploadGallery(requestBody)
            response.collect {
                _uploadGallery.value = it
            }
        }
    }
}
