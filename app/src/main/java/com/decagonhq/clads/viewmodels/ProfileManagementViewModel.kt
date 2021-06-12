package com.decagonhq.clads.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileManagementViewModel : ViewModel() {

    val firstNameInputLiveData = MutableLiveData<String>()
    val lastNameInputLiveData = MutableLiveData<String>()
    val otherNameInputLiveData = MutableLiveData<String>()
    val genderInputLiveData = MutableLiveData<String>()
    val stateLiveData = MutableLiveData<String>()
    val cityLiveData = MutableLiveData<String>()
    val streetLiveData = MutableLiveData<String>()
    val showroomAddressLiveData = MutableLiveData<String>()
    val numberOfEmployeeLiveData = MutableLiveData<String>()
    val legalStatusLiveData = MutableLiveData<String>()
    val nameOfUnionLiveData = MutableLiveData<String>()
    val wardOfUnionLiveData = MutableLiveData<String>()
    val stateOfUnionLiveData = MutableLiveData<String>()
    val lgaOfUnionLiveData = MutableLiveData<String>()
    val obiomaTrainedLiveData = MutableLiveData<String>()

    var deliveryValueInNumber = MutableLiveData<String>()
    var deliveryDuration = MutableLiveData<String>()
}
