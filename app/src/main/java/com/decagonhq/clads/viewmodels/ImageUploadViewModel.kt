package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.clads.data.domain.GenericResponseClass
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
    private var _userProfileImage = MutableLiveData<Resource<GenericResponseClass>>()
    val userProfileImage: LiveData<Resource<GenericResponseClass>> get() = _userProfileImage

    fun mediaImageUpload(image: RequestBody) {
        viewModelScope.launch {
            _userProfileImage.value = Resource.Loading("Uploading...")
            val response = imageRepository.uploadMediaImage(image)
            response.collect {
                _userProfileImage.value = it
            }
        }
    }
}