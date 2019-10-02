package com.example.weatherapp.model

data class WeatherList(
    val dt: Int,
    val temp: Temp,
    val pressure: Float,
    val humidity: Int,
    val weather: Weather
)