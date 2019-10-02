package com.example.weatherapp.repository.Local

class LocationMapper {
    internal fun fromRealm(locationsRealm: LocationsDatabase): Location {
        val location = Location("", 0.0, 0.0)
        location.name = locationsRealm.city!!
        location.lat = locationsRealm.lat!!
        location.lng = locationsRealm.lng!!
        return location
    }
}