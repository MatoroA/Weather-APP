package com.example.weather_dvt.core.database.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weather_dvt.core.database.dao.WeatherDao
import com.example.weather_dvt.core.database.model.WeatherEntity

@Database(
    entities = [
        WeatherEntity::class
    ],
    version = 1,
    exportSchema = true,
)
//@TypeConverters(
//    InstantConverter::class,
//    NewsResourceTypeConverter::class,
//)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
