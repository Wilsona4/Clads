package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.domain.login.UserRole
import com.decagonhq.clads.repository.UserProfileRepository
import com.decagonhq.clads.util.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(
        private val authProfileRepository: UserProfileRepository
) : ViewModel() {
    private var _userProfile = MutableLiveData<Resource<GenericResponseClass>>()
    val userProfile: LiveData<Resource<GenericResponseClass>> get() = _userProfile

    /*Login with google*/
    fun loginUserWithGoogle() {
        viewModelScope.launch {
            _userProfile.value = Resource.Loading("Loading...")
            val response = authProfileRepository.getUserProfile()
            response.collect {
                _userProfile.value = it
            }
        }
    }
}