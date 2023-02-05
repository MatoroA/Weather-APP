package com.example.weather_dvt.core.database.dao

import androidx.room.*
import com.example.weather_dvt.core.database.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query(value = "select * from weather_forecast where is_current_weather = 0")
    fun getWeatherForecast(): Flow<List<WeatherEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherForecast(forecast: List<WeatherEntity>)

    @Query("DELETE FROM weather_forecast WHERE city_id = :cityId AND is_current_weather = :isCurrentWeather")
    suspend fun deletePreviousWeatherRecords(cityId: Int, isCurrentWeather: Boolean = false)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentWeather(weather: WeatherEntity)

    @Query("select * from weather_forecast where is_current_weather = 1")
    fun getCurrentWeather(): Flow<WeatherEntity?>
}