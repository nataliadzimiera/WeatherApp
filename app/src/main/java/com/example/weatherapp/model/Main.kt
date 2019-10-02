package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class Main(
    val temp: Float,
    val pressure: Double,
    val humidity: Int,
    @SerializedName("temp_min")
    val tempMin: Float,
    @SerializedName("temp_max")
    val tempMax: Float
)