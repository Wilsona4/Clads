package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.clads.data.domain.client.Client
import com.decagonhq.clads.repository.ClientsRepository
import com.decagonhq.clads.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val clientsRepository: ClientsRepository
) : ViewModel() {

    private var _client = MutableLiveData<Resource<List<Client>>>()
    val client: LiveData<Resource<List<Client>>> get() = _client

    fun getClients() {
        viewModelScope.launch(Dispatchers.IO) {
            clientsRepository.getClients().collect {
                _client.postValue(it)
            }
        }
    }

    fun getLocalDatabaseClients() {
        viewModelScope.launch(Dispatchers.IO) {
            clientsRepository.getLocalDatabaseClient().collect {
                _client.postValue(it)
            }
        }
    }

    fun addClient(client: Client) {
        _client.value = Resource.Loading(null, "Saving...")
        viewModelScope.launch(Dispatchers.IO) {
            clientsRepository.addClient(client).collect {
                _client.postValue(it)
            }
        }
    }

    fun deleteClient(clientId: Int) {
        _client.value = Resource.Loading(null, "Deleting...")
        viewModelScope.launch(Dispatchers.IO) {
            clientsRepository.deleteClient(clientId).collect {
                _client.postValue(it)
            }
        }
    }
}
