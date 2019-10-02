package com.example.weatherapp.model

data class FiveDaysWeather(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<DetailList>,
    val message: Double
)
data class Rain(
    val `3h`: Double
)
