package com.example.weather_dvt.core.network.model.weather.current

import com.example.weather_dvt.core.database.model.WeatherEntity
import com.example.weather_dvt.core.network.model.weather.NetworkMain
import com.example.weather_dvt.core.network.model.weather.NetworkWeatherResponse
import com.example.weather_dvt.core.network.model.weather.util.DayOfWeek
import com.example.weather_dvt.core.network.model.weather.util.DayOfWeekSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.ceil

@Serializable
data class CurrentWeatherResponse(
    val weather: List<NetworkWeatherResponse>,
    val main: NetworkMain,
    @SerialName("dt")
    @Serializable(DayOfWeekSerializer::class)
    val dayOfWeek: DayOfWeek,
    @SerialName("id")
    val cityId: Int,
    @SerialName("name")
    val cityName: String
)

fun CurrentWeatherResponse.asWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        dayOfWeek = dayOfWeek.dayOfWeek,
        cityId = cityId,
        isCurrentDayWeather = true,
        maximumTemperature = ceil(main.maximumTemperature).toInt(),
        minimumTemperature = ceil(main.minimumTemperature).toInt(),
        temperature = ceil(main.temperature).toInt(),
        weatherId = weather.first().id,
    )
}


