package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
<<<<<<< HEAD
=======
import com.decagonhq.clads.data.domain.login.EmailLoginSuccessResponse
import com.decagonhq.clads.data.domain.login.GoogleLoginSuccessResponse
import com.decagonhq.clads.data.domain.login.LoginCredentials
import com.decagonhq.clads.data.domain.login.UserRole
import com.decagonhq.clads.data.domain.profileimage.UserProfileImageResponse
>>>>>>> 429776185ec36f229058eef925cabaa906560e90
import com.decagonhq.clads.data.domain.registration.UserRegSuccessResponse
import com.decagonhq.clads.data.domain.registration.UserRegistration
import com.decagonhq.clads.repository.AuthRepository
import com.decagonhq.clads.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
<<<<<<< HEAD
=======
import okhttp3.MultipartBody
>>>>>>> 429776185ec36f229058eef925cabaa906560e90
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _userRegData = MutableLiveData<Resource<UserRegSuccessResponse>>()
    val userRegData: LiveData<Resource<UserRegSuccessResponse>> get() = _userRegData

<<<<<<< HEAD
=======
    private var _loginUser = MutableLiveData<Resource<EmailLoginSuccessResponse>>()
    val loginUser: LiveData<Resource<EmailLoginSuccessResponse>> get() = _loginUser

    private var _userProfileImage = MutableLiveData<Resource<UserProfileImageResponse>>()
    val userProfileImage: LiveData<Resource<UserProfileImageResponse>> get() = _userProfileImage

    private var _loginUserWithGoogle = MutableLiveData<Resource<GoogleLoginSuccessResponse>>()
    val loginUserWithGoogle: LiveData<Resource<GoogleLoginSuccessResponse>> get() = _loginUserWithGoogle

>>>>>>> 429776185ec36f229058eef925cabaa906560e90
    fun registerUser(user: UserRegistration) {

        viewModelScope.launch {
            val response = authRepository.registerUser(user)
            response.collect {
                _userRegData.value = it
            }
        }
    }
<<<<<<< HEAD
=======

    /*Login in with email*/
    fun loginUser(loginCredentials: LoginCredentials) {
        viewModelScope.launch {
            val response = authRepository.loginUser(loginCredentials)
            response.collect {
                _loginUser.value = it
            }
        }
    }

    /*Login with google*/
    fun loginUserWithGoogle(userRole: UserRole) {
        viewModelScope.launch {
            val response = authRepository.loginUserWithGoogle(userRole)
            response.collect {
                _loginUserWithGoogle.value = it
            }
        }
    }

    fun userProfileImage(userProfileImage: MultipartBody.Part) {
        viewModelScope.launch {
            val response = authRepository.userProfileImage(userProfileImage)
            response.collect {
                _userProfileImage.value = it
            }
        }
    }
>>>>>>> 429776185ec36f229058eef925cabaa906560e90
}
