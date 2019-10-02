package com.example.weatherapp.repository.Local

import io.realm.RealmObject
import io.realm.annotations.Required


open class LocationsDatabase : RealmObject() {

    @Required
    var city: String? = ""
    var lat: Double? = 0.0
    var lng: Double? = 0.0



}