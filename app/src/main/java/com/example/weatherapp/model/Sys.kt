package com.example.weatherapp.model

data class Sys(
    val type: Int,
    val id: Int,
    val message: Float,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)