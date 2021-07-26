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
import kotlinx.coroutines.Dispatchers
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

    var imageResponseCallback: MutableLiveData<String> = MutableLiveData<String>()

    init {
        getRemoteGalleryImages()
    }

    /*Upload Profile Picture*/
    fun mediaImageUpload(image: MultipartBody.Part) {
        _userProfileImage.value = Resource.Loading(null, "Uploading...")
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.uploadMediaImage(image).collect {
                _userProfileImage.postValue(it)
            }
        }
    }

    /*Get Profile Picture from Room Data*/
    fun getUserProfileImage() {
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.getUserImage().collect {
                _userProfileImage.postValue(it)
            }
        }
    }

    /*Get Gallery Images*/
    fun getRemoteGalleryImages() {
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.getRemoteGalleryImage()
        }
    }

    /*Upload Gallery Image*/
    fun uploadGallery(requestBody: RequestBody) {
        _uploadGallery.value = Resource.Loading(null, "uploading...")
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.uploadGallery(requestBody).collect {
                _uploadGallery.postValue(it)
            }
        }
    }

    fun getLocalDatabaseGalleryImages() {
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.getLocalDatabaseGalleryImages().collect {
                _uploadGallery.postValue(it)
            }
        }
    }

    fun deleteGalleryImage(fileId: String) {
        _uploadGallery.value = Resource.Loading(null, "Deleting...")
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.deleteGalleryImage(fileId, imageResponseCallback)
        }
    }

    fun editGalleryImage(fileId: String, requestBody: RequestBody) {
        _uploadGallery.value = Resource.Loading(null, "uploading...")
        viewModelScope.launch(Dispatchers.IO) {
            imageRepository.editDescription(fileId, requestBody).collect {
                _uploadGallery.postValue(it)
            }
        }
    }
}
