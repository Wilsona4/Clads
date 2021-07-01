package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.domain.login.LoginCredentials
import com.decagonhq.clads.data.domain.login.UserRole
import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.data.domain.profileimage.UserProfileImage
import com.decagonhq.clads.data.domain.registration.UserRegistration
import com.decagonhq.clads.repository.AuthRepository
import com.decagonhq.clads.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _userRegData = MutableLiveData<Resource<GenericResponseClass<UserProfile>>>()
    val userRegData: LiveData<Resource<GenericResponseClass<UserProfile>>> get() = _userRegData

    private var _loginUser = MutableLiveData<Resource<GenericResponseClass<String>>>()
    val loginUser: LiveData<Resource<GenericResponseClass<String>>> get() = _loginUser

    private var _userProfileImage =
        MutableLiveData<Resource<GenericResponseClass<UserProfileImage>>>()
    val userProfileImage: LiveData<Resource<GenericResponseClass<UserProfileImage>>> get() = _userProfileImage

    private var _loginUserWithGoogle = MutableLiveData<Resource<GenericResponseClass<String>>>()
    val loginUserWithGoogle: LiveData<Resource<GenericResponseClass<String>>> get() = _loginUserWithGoogle

    fun registerUser(user: UserRegistration) {
        viewModelScope.launch {
            _userRegData.value = Resource.Loading(null, "Signing Up...")
            val response = authRepository.registerUser(user)
            response.collect {
                _userRegData.value = it
            }
        }
    }

    /*Login in with email*/
    fun loginUser(loginCredentials: LoginCredentials) {
        viewModelScope.launch {
            _loginUser.value = Resource.Loading(null, "Loading...")
            val response = authRepository.loginUser(loginCredentials)
            response.collect {
                _loginUser.value = it
            }
        }
    }

    /*Login with google*/
    fun loginUserWithGoogle(userRole: UserRole) {
        viewModelScope.launch {
            _loginUserWithGoogle.value = Resource.Loading(null, "Loading...")
            val response = authRepository.loginUserWithGoogle(userRole)
            response.collect {
                _loginUserWithGoogle.value = it
            }
        }
    }

    fun userProfileImage(userProfileImage: MultipartBody.Part) {
        viewModelScope.launch {
            _userProfileImage.value = Resource.Loading(null, "Uploading...")
            val response = authRepository.userProfileImage(userProfileImage)
            response.collect {
                _userProfileImage.value = it
            }
        }
    }
}
