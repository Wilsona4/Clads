package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.domain.login.LoginCredentials
import com.decagonhq.clads.data.domain.login.UserRole
import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.data.domain.registration.UserRegistration
import com.decagonhq.clads.repository.AuthRepository
import com.decagonhq.clads.util.Resource
import com.decagonhq.clads.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _userRegData = MutableLiveData<Resource<GenericResponseClass<UserProfile>>>()
    val userRegData: LiveData<Resource<GenericResponseClass<UserProfile>>> get() = _userRegData

    private var _loginUser = MutableLiveData<Resource<GenericResponseClass<String>>>()
    val loginUser: LiveData<Resource<GenericResponseClass<String>>> get() = _loginUser

    private var _loginUserWithGoogle = MutableLiveData<Resource<GenericResponseClass<String>>>()
    val loginUserWithGoogle: LiveData<Resource<GenericResponseClass<String>>> get() = _loginUserWithGoogle

    private val _authenticationToken = SingleLiveEvent<Resource<GenericResponseClass<String>>>()
    val authenticationToken: LiveData<Resource<GenericResponseClass<String>>> = _authenticationToken

    fun registerUser(user: UserRegistration) {
        _userRegData.value = Resource.Loading(null, "Signing Up...")
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.registerUser(user).collect {
                _userRegData.postValue(it)
            }
        }
    }

    /*Login in with email*/
    fun loginUser(loginCredentials: LoginCredentials) {
        _loginUser.value = Resource.Loading(null, "Loading...")
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.loginUser(loginCredentials).collect {
                _loginUser.postValue(it)
            }
        }
    }

    /*Login with google*/
    fun loginUserWithGoogle(userRole: UserRole?) {
        _loginUserWithGoogle.value = Resource.Loading(null, "Loading...")
        viewModelScope.launch(Dispatchers.IO) {

            authRepository.loginUserWithGoogle(userRole).collect {
                _loginUserWithGoogle.postValue(it)
            }
        }
    }

    /* Verify authentication token from sign up */
    fun verifyAuthToken(token: String) {
        _authenticationToken.value = Resource.Loading(null, "Verifying email")
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.verifyAuthToken(token).collect {
                _authenticationToken.postValue(it)
            }
        }
    }
}
