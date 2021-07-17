package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.remote.client.Client
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

    private var _deleteClientResponse = MutableLiveData<Resource<GenericResponseClass<List<Client>>>>()
    val deleteClientResponse: LiveData<Resource<GenericResponseClass<List<Client>>>> get() = _deleteClientResponse

    private var _addClientResponse = MutableLiveData<Resource<Client>>()
    val addClientResponse: LiveData<Resource<Client>> get() = _addClientResponse

    private var _deleteFromDBResponse = MutableLiveData<Resource<Int>>()
    val deleteFromDBResponse: LiveData<Resource<Int>> get() = _deleteFromDBResponse

    private var _addToDBResponse = MutableLiveData<Resource<Client>>()
    val addToDBResponse: LiveData<Resource<Client>> get() = _addToDBResponse

    fun getClients() {
        viewModelScope.launch(Dispatchers.IO) {
            clientsRepository.getClients().collect {
                _client.postValue(it)
            }
        }
    }

    fun addClient(client: Client) {
        viewModelScope.launch(Dispatchers.IO) {
            clientsRepository.addClientToServer(client).collect {
                _addClientResponse.postValue(it)
            }
        }
    }

    fun deleteClient(clientId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            clientsRepository.deleteClient(clientId).collect {
                _deleteClientResponse.postValue(it)
            }
        }
    }

    fun deleteClientFromDb(clients: List<Client>) {
        viewModelScope.launch(Dispatchers.IO) {
            _deleteFromDBResponse.postValue(clientsRepository.deleteClientFromDb(clients))
        }
    }

    fun addClientToDb(client: Client) {
        viewModelScope.launch(Dispatchers.IO) {
            _addToDBResponse.postValue(clientsRepository.addClientToDb(client))
        }
    }
}
