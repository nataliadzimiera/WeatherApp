package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class DetailList (
    val clouds: Clouds,
    val dt: Int,
    @SerializedName("dt_txt")
    val dt_txt: String,
    val main: Main,
    val rain: Rain,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind
)