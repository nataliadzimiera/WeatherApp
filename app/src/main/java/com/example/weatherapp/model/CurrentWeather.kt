package com.example.weatherapp.model

data class CurrentWeather(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val dt: Int,
    val sys: Sys,
    val timezone: Int,
    val name: String,
    val cod: Int
)