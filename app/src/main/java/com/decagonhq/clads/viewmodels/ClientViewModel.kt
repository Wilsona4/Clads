package com.decagonhq.clads.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decagonhq.clads.data.domain.DeliveryAddressModel

class ClientViewModel : ViewModel() {

    val _clientAddress = MutableLiveData<DeliveryAddressModel>()
    val clientAddress: LiveData<DeliveryAddressModel> get() = _clientAddress

    /**client address is added */
    fun clientNewAddress(address: DeliveryAddressModel) {
        _clientAddress.value = address
    }
}
