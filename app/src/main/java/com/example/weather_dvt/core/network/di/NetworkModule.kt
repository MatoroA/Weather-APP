package com.example.weather_dvt.core.network.di

import com.example.weather_dvt.core.common.utils.Constant.Companion.apiKey
import com.example.weather_dvt.core.network.ApiInterface
import com.example.weather_dvt.core.network.datasource.WeatherNetworkDataSource
import com.example.weather_dvt.core.network.datasource.WeatherNetworkDataSourceImpl
import com.example.weather_dvt.core.network.util.ServiceInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Named(apiKey)
    fun userApiKey(): String = "505b2db913befd32000cc5d021a28249"

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }


    @Singleton
    @Provides
    fun providesApiInterface(networkJson: Json): ApiInterface {

        val networkApi = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(ServiceInterceptor(userApiKey()))
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                    )
                    .build()
            )
            .addConverterFactory(
                @OptIn(ExperimentalSerializationApi::class)
                networkJson.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(ApiInterface::class.java)

        return networkApi
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface DataSource {
    @Binds
    fun bindsNetworkDataSource(
        networkDataSourceImpl: WeatherNetworkDataSourceImpl
    ): WeatherNetworkDataSource

}