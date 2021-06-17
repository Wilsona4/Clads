package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.clads.data.domain.registration.UserRegSuccessResponse
import com.decagonhq.clads.data.domain.registration.UserRegistration
import com.decagonhq.clads.repository.AuthRepository
import com.decagonhq.clads.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _userRegData = MutableLiveData<Resource<UserRegSuccessResponse>>()
    val userRegData: LiveData<Resource<UserRegSuccessResponse>> get() = _userRegData

    fun registerUser(user: UserRegistration) {
        viewModelScope.launch {
            val response = authRepository.registerUser(user)
            response.collect {
                _userRegData.value = it
            }
        }
    }
}
