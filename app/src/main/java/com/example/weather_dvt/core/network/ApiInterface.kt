package com.example.weather_dvt.core.network

import com.example.weather_dvt.core.network.model.weather.NetworkForecastResponse
import com.example.weather_dvt.core.network.model.weather.current.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("cnt") cnt: Int = 40,
        @Query("units") units: String = "metric"
    ): NetworkForecastResponse

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") units: String = "metric"
    ): CurrentWeatherResponse
}