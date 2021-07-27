package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.data.local.UserProfileEntity
import com.decagonhq.clads.repository.UserProfileRepository
import com.decagonhq.clads.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {
    private var _userProfile = MutableLiveData<Resource<UserProfileEntity>>()
    val userProfile: LiveData<Resource<UserProfileEntity>> get() = _userProfile

    fun getUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            userProfileRepository.getUserProfile().collect {
                _userProfile.postValue(it)
            }
        }
    }

    /* update users endpoint data */
    fun updateUserProfile(userProfile: UserProfile) {
        _userProfile.value = Resource.Loading(null, "Updating")
        viewModelScope.launch(Dispatchers.IO) {
            userProfileRepository.updateUserProfile(userProfile)
        }
    }

    /*Get user profile data in the local database*/
    fun getLocalDatabaseUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            userProfileRepository.getLocalDatabaseUserProfile().collect {
                _userProfile.postValue(it)
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
