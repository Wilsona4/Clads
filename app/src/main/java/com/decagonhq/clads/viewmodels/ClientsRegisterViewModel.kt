package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagonhq.clads.data.domain.DressMeasurementModel
import com.decagonhq.clads.data.domain.GenericResponseClass
import com.decagonhq.clads.data.domain.client.Client
import com.decagonhq.clads.data.domain.client.ClientReg
import com.decagonhq.clads.data.domain.client.Measurement
import com.decagonhq.clads.repository.ClientsRepository
import com.decagonhq.clads.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientsRegisterViewModel @Inject constructor(
    private val clientsRepository: ClientsRepository
): ViewModel(){

//    private var _clientRegData = MutableLiveData<Resource<GenericResponseClass<Client>>>()
//    val clientRegData: LiveData<Resource<GenericResponseClass<Client>>> get() = _clientRegData


    private val _clientData = MutableLiveData<ClientReg>()
    val clientData: LiveData<ClientReg> get() = _clientData

    private val list = mutableListOf<DressMeasurementModel>()


    fun setClient(client: ClientReg) {
        _clientData.value = client
    }

    private val _measurementData = MutableLiveData<MutableList<DressMeasurementModel>>()
    val measurementData: LiveData<MutableList<DressMeasurementModel>> get() = _measurementData


    fun addMeasurements(measurement: DressMeasurementModel) {

        list.add( measurement)
        _measurementData.value = list

    }

    fun editMeasurement(position:Int,measurement: DressMeasurementModel) {
        list[position] = measurement
        _measurementData.value = list
    }


    fun registerClient(client: Client) {
        viewModelScope.launch {
            clientsRepository.addClient(client)
        }
    }

//    /*Save To Local Db*/
//    fun saveClientToLocalDatabase() {
//        viewModelScope.launch {
//            //clientsRepository.saveClientToLocalDatabase()
//        }
//    }

}