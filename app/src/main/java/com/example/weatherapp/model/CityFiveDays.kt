package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class CityFiveDays(
val id: Int,
val name: String,
val coord: Coord,
val country: String,
@SerializedName("timezone")
val timeZone: Int,
val cod: String,
val message: Double,
val cnt: Int,
val list: List<DetailList>
)