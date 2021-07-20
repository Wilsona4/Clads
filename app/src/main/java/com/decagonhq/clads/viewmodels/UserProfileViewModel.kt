package com.decagonhq.clads.viewmodels

import android.util.Log
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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository
) : ViewModel() {
    private val TAG = UserProfileViewModel::class.java.simpleName
    private var _userProfile = MutableLiveData<Resource<UserProfile>>()
    val userProfile: LiveData<Resource<UserProfile>> get() = _userProfile

    init {
        saveUserProfileToLocalDatabase()
    }

    fun getUserProfile() {
        viewModelScope.launch {
            userProfileRepository.getUserProfile().collect {
                _userProfile.value = it
                Log.i(TAG, "getUserProfile: ${it.data}")
            }
        }
    }

    /* update users endpoint data */
    fun updateUserProfile(userProfile: UserProfile) {

        viewModelScope.launch {
            Timber.d(userProfile.toString())
            userProfileRepository.updateUserProfile(userProfile)
        }
    }

    /*Get user profile data in the local database*/
    fun getLocalDatabaseUserProfile() {
        viewModelScope.launch {
            userProfileRepository.getLocalDatabaseUserProfile().collect {
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
