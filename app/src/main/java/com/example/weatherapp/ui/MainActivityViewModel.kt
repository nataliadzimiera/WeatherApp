package com.example.weatherapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.repository.Remote.Api
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val api: Api) : ViewModel() {

    val weatherLocationLiveData = MutableLiveData<LatLng>()
    val name = MutableLiveData<String>()
    var lat: Double? = 0.0
    var lng: Double? = 0.0
    var city: String? = ""

}