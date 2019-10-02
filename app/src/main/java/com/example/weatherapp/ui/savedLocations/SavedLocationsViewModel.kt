package com.example.weatherapp.ui.savedLocations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.repository.Local.Location
import com.example.weatherapp.repository.Local.LocationMapper
import javax.inject.Inject

class SavedLocationsViewModel @Inject constructor(): ViewModel() {
    var locationsLiveData = MutableLiveData<List<Location>>()


}