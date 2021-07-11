package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.clads.data.domain.profile.UserProfile
import com.decagonhq.clads.data.remote.client.Client
import com.decagonhq.clads.repository.AuthRepository
import com.decagonhq.clads.repository.ClientsRepository
import com.decagonhq.clads.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val clientsRepository:ClientsRepository
) : ViewModel() {

    private var _client = MutableLiveData<Resource<List<Client>>>()
    val client: LiveData<Resource<List<Client>>> get() = _client

    fun getClients() {
        viewModelScope.launch {
            clientsRepository.getClients().collect {
                _client.value = it
            }
        }
    }

    fun addClient(client:Client) {
        viewModelScope.launch {
            clientsRepository.addClient(client).collect{
                _client.value = it
            }
        }
    }
}