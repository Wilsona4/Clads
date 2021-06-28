package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.domain.images.UserProfileImage
import com.decagonhq.clads.repository.ImageRepository
import com.decagonhq.clads.util.Resource
import javax.inject.Inject

class ImageDownloadViewModel @Inject constructor(private val imageRepository: ImageRepository) :
    ViewModel() {

    private var _getImage = MutableLiveData<Resource<GenericResponseClass<UserProfileImage>>>()
    val getImage: LiveData<Resource<GenericResponseClass<UserProfileImage>>> get() = _getImage
}
