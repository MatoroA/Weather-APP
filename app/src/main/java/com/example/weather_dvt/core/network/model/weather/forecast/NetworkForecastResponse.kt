package com.example.weather_dvt.core.network.model.weather

import com.example.weather_dvt.core.database.model.WeatherEntity
import com.example.weather_dvt.core.network.model.weather.util.DayOfWeek
import com.example.weather_dvt.core.network.model.weather.util.DayOfWeekSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.ceil

@Serializable
data class NetworkForecastResponse(
    val list: List<NetworkData>,
    val city: NetworkCity
)

@Serializable
data class NetworkData(
    @SerialName("dt")
    @Serializable(DayOfWeekSerializer::class)
    val dayOfWeek: DayOfWeek,
    val main: NetworkMain,
    val weather: List<NetworkWeatherResponse>,
)


@Serializable
data class NetworkCity(
    @SerialName("id")
    val cityId: Int,
    @SerialName("name")
    val cityName: String
)


fun NetworkForecastResponse.asListOfWeatherEntity(): List<WeatherEntity> {
    val forecastGroupedByDay = this.list.groupBy { it.dayOfWeek.dayOfWeek }
    val weekDays = forecastGroupedByDay.keys.toList()

    return weekDays.map { dayOfWeek ->
        val dayTemperatureSum = forecastGroupedByDay[dayOfWeek]?.let {
            it.map { networkData ->
                networkData.main.temperature
            }.reduce { acc, temp -> acc + temp }
        } ?: 0.0

        val averageDayTemperature =
            forecastGroupedByDay[dayOfWeek].calculateAverage(dayTemperatureSum)

        val weatherIdSum = forecastGroupedByDay[dayOfWeek]?.let { networkData ->
            networkData.map { it.weather[0].id }.toList()
                .reduce { acc, weatherId -> acc + weatherId }
        } ?: 0

        val weatherIdAverage =
            forecastGroupedByDay[dayOfWeek].calculateAverage(weatherIdSum.toDouble())

        WeatherEntity(
            dayOfWeek = dayOfWeek,
            cityId = this.city.cityId,
            maximumTemperature = 0,
            minimumTemperature = 0,
            temperature = ceil(averageDayTemperature).toInt(),
            weatherId = weatherIdAverage.toInt()
        )
    }
}

private fun List<NetworkData>?.calculateAverage(sum: Double): Double {
    return this?.let {
        sum / it.size
    } ?: 0.0
}