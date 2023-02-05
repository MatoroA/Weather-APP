package com.example.weather_dvt.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather_dvt.core.model.Weather
import com.example.weather_dvt.core.network.model.weather.util.asWeekDay

@Entity(
    tableName = "weather_forecast"
)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dayOfWeek: Int,
    val temperature: Int,
    @ColumnInfo("max_temp") val maximumTemperature: Int,
    @ColumnInfo("min_temp") val minimumTemperature: Int,
    val weatherId: Int,
    @ColumnInfo("city_id") val cityId: Int,
    @ColumnInfo("is_current_weather") val isCurrentDayWeather: Boolean = false
)

fun WeatherEntity.asExternalModel() = Weather(
    day = dayOfWeek.asWeekDay(),
    isCurrent = isCurrentDayWeather,
    maximumTemperature = maximumTemperature,
    minimumTemperature = minimumTemperature,
    temperature = temperature,
    weatherId = weatherId,
)