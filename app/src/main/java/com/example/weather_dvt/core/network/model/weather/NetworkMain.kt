package com.example.weather_dvt.core.network.model.weather

import kotlinx.serialization.SerialName
import  kotlinx.serialization.Serializable

@Serializable
data class NetworkMain(
    @SerialName("temp")
    val temperature: Double,
    @SerialName("temp_max")
    val maximumTemperature: Double,
    @SerialName("temp_min")
    val minimumTemperature: Double
)