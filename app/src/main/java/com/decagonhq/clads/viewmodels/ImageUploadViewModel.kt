package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
) : ViewModel() {

    private var _userProfileImage = MutableLiveData<Resource<UserProfileImage>>()
    val userProfileImage: LiveData<Resource<UserProfileImage>> get() = _userProfileImage
    private var _uploadGalleryImage =
        MutableLiveData<Resource<GenericResponseClass<UserProfileImage>>>()
    val uploadGalleryImage: LiveData<Resource<GenericResponseClass<UserProfileImage>>> get() = _uploadGalleryImage

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
            val response = imageRepository.getUserImage()
            response.collect {
                _userProfileImage.value = it
            }
        }
    }

    fun uploadGalleryImage(requestBody: RequestBody) {
        viewModelScope.launch {
            _uploadGalleryImage.value = Resource.Loading(null, "uploading...")
            val response = imageRepository.uploadGalleryImage(requestBody)
            response.collect {
                _uploadGalleryImage.value = it
            }
        }
    }
}
