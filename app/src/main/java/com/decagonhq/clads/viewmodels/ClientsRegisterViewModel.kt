package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decagonhq.clads.data.domain.DeliveryAddressModel
import com.decagonhq.clads.data.domain.client.ClientReg
import com.decagonhq.clads.data.remote.client.Measurement
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

    fun setClient(client: ClientReg) {
        _clientData.value = client
    }

    private val _measurementData = MutableLiveData<MutableList<Measurement>>()
    val measurementData: LiveData<MutableList<Measurement>> get() = _measurementData

    fun addMeasurements(measurement: Measurement) {

        list.add(measurement)
        _measurementData.value = list
    }

    fun editMeasurement(position: Int, measurement: Measurement) {
        list[position] = measurement
        _measurementData.value = list
    }

    private val _clientAddress = MutableLiveData<DeliveryAddressModel>()
    val clientAddress: LiveData<DeliveryAddressModel> get() = _clientAddress

    /**client address is added */
    fun clientNewAddress(address: DeliveryAddressModel) {
        _clientAddress.value = address
    }
}
