package com.example.weatherapp.repository.Remote

import com.example.weatherapp.model.CurrentWeather
import com.example.weatherapp.model.FiveDaysWeather
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

public interface Api {

    @GET("/data/2.5/weather?")
    fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") APPID: String

        ): Observable<CurrentWeather>


    @GET("/data/2.5/forecast?")
    fun get5DayWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") APPID: String
    ): Observable<FiveDaysWeather>
}
