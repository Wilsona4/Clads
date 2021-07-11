package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.clads.data.domain.GenericResponseClass
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
        getUserImage()
        getGalleryImage()
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
    fun getUserImage() {
        viewModelScope.launch {
            val response = imageRepository.getUserImage()
            response.collect {
                _userProfileImage.value = it
            }
        }
    }

    /*Get Gallery Images*/
    fun getGalleryImage(){
        viewModelScope.launch {
            val response = imageRepository.getGalleryImage()
            response.collect{
                _uploadGallery.value = it
            }
        }
    }

    /*Upload Gallery Image*/
    fun uploadGallery(requestBody: RequestBody) {
        viewModelScope.launch {
            _uploadGallery.value = Resource.Loading(null, "uploading...")
            val response = imageRepository.uploadGallery(requestBody)
            response.collect {
                _uploadGallery.value = it
            }
        }
    }
}
