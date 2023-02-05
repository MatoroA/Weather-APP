package com.example.weather_dvt.core.datastore.di

import android.content.Context
import android.content.SharedPreferences
import com.example.weather_dvt.core.datastore.repository.UserPreferences
import com.example.weather_dvt.core.datastore.repository.UserPreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val USER_PREFERENCES_NAME = "user_preferences"

    @Provides
    @Singleton
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.applicationContext.getSharedPreferences(
            USER_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreRepository {
    @Binds
    fun bindsUserRepository(userPreferencesImpl: UserPreferencesImpl): UserPreferences
}