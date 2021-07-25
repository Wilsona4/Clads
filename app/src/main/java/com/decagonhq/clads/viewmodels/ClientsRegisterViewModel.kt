package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decagonhq.clads.data.domain.client.ClientReg
import com.decagonhq.clads.data.domain.client.DeliveryAddress
import com.decagonhq.clads.data.domain.client.Measurement
import com.decagonhq.clads.repository.ClientsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClientsRegisterViewModel @Inject constructor(
    private val clientsRepository: ClientsRepository
) : ViewModel() {

    private val _clientData = MutableLiveData<ClientReg>()
    val clientData: LiveData<ClientReg> get() = _clientData

    private val list = mutableListOf<Measurement>()

    private val _measurementData = MutableLiveData<MutableList<Measurement>>()
    val measurementData: LiveData<MutableList<Measurement>> get() = _measurementData

    private val _clientAddress = MutableLiveData<DeliveryAddress>()
    val clientAddress: LiveData<DeliveryAddress> get() = _clientAddress

    fun setClient(client: ClientReg) {
        _clientData.value = client
    }

    fun addMeasurements(measurement: Measurement) {
        list.add(measurement)
        _measurementData.value = list
    }

    fun editMeasurement(position: Int, measurement: Measurement) {
        list[position] = measurement
        _measurementData.value = list
    }

    /**client address is added */
    fun clientNewAddress(address: DeliveryAddress) {
        _clientAddress.value = address
    }
}
