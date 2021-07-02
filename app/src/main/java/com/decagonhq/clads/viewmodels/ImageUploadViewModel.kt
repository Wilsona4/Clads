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
import javax.inject.Inject

@HiltViewModel
class ImageUploadViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : AndroidViewModel(Application()) {
    private var _userProfileImage = MutableLiveData<Resource<GenericResponseClass<UserProfileImage>>>()
    val userProfileImage: LiveData<Resource<GenericResponseClass<UserProfileImage>>> get() = _userProfileImage

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
}
