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

//    private var _deleteClientResponse = MutableLiveData<Resource<GenericResponseClass<Client>>>()
//    val deleteClientResponse: LiveData<Resource<GenericResponseClass<Client>>> get() = _deleteClientResponse
//
//    private var _addClientResponse = MutableLiveData<Resource<Event<GenericResponseClass<Client>>>>()
//    val addClientResponse: LiveData<Resource<Event<GenericResponseClass<Client>>>> get() = _addClientResponse
//
//    private var _deleteFromDBResponse = MutableLiveData<Resource<Int>>()
//    val deleteFromDBResponse: LiveData<Resource<Int>> get() = _deleteFromDBResponse
//
//    private var _addToDBResponse = MutableLiveData<Resource<Event<Client>>>()
//    val addToDBResponse: LiveData<Resource<Event<Client>>> get() = _addToDBResponse

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
            clientsRepository.deleteClient(clientId)
        }
    }

//    fun deleteClientFromDb(clients: Client) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _deleteFromDBResponse.postValue(clientsRepository.deleteClientFromDb(clients))
//        }
//    }
//
//    fun addClientToDb(client: Client) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _addToDBResponse.postValue(clientsRepository.addClientToDb(client))
//        }
//    }
}
