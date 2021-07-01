package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.repository.UserProfileRepository
import com.decagonhq.clads.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {

    private var _userProfile = MutableLiveData<Resource<UserProfile>>()
    val userProfile: LiveData<Resource<UserProfile>> get() = _userProfile

    init {
        saveUserProfileToLocalDatabase()
    }

    fun getUserProfile() {
        viewModelScope.launch {
            val response = userProfileRepository.getUserProfile()
            response.collect {
                _userProfile.value = it
            }
        }
    }

    /*Save User Profile on Sign Up*/
    fun saveUserProfileToLocalDatabase() {
        viewModelScope.launch {
            userProfileRepository.saveUserProfileToLocalDatabase()
        }
    }
}
