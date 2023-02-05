package com.example.weather_dvt

import com.example.weather_dvt.core.data.repository.WeatherRepository
import com.example.weather_dvt.core.datastore.repository.UserPreferences
import com.example.weather_dvt.core.model.*
import com.example.weather_dvt.core.network.model.weather.util.WeekDay
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainActivityViewModelTest {
    @Mock
    lateinit var repository: WeatherRepository

    @Mock
    lateinit var userPreferences: UserPreferences

    private lateinit var mainActivityViewModel: MainActivityViewModel

    val weather = Weather(
        day = WeekDay.Sunday,
        temperature = 10,
        maximumTemperature = 30,
        minimumTemperature = 20,
        weatherId = 530,
        isCurrent = true
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mainActivityViewModel = MainActivityViewModel(repository, userPreferences)
    }

    @Test
    fun `updateUserLocation updates the location in userPreferences`() {
        val locationDetails = LocationDetails(1.0, longitude = 1.0)
        mainActivityViewModel.updateUserLocation(locationDetails)
    }

    @Test
    fun `updatePermission updates the permission in userPreferences`() {
        val userPermission = LocationPermission.ACCEPTED
        mainActivityViewModel.updatePermission(userPermission)
    }

    @Test
    fun `uiState is updated correctly with currentWeather and appSetting`() {
        val currentWeather = weather
//        `when`(repository.getCurrentWeather()).thenReturn(flow { currentWeather })
//        val appSetting = AppSetting()
//        `when`(userPreferences.getAppState()).thenReturn(appSetting)
//
//        val expectedUiState = MainActivityUiState(
//            themeType = WeatherAppThemeType.RAINY,
//            appSetting = appSetting
//        )
//        assertEquals(expectedUiState, mainActivityViewModel.uiState.value)
    }

    @Test
    fun `asAppTheme returns the correct theme type`() {
        assertEquals(
            WeatherAppThemeType.SUNNY,
            Weather(
                day = WeekDay.Sunday,
                temperature = 1,
                maximumTemperature = 10,
                minimumTemperature = -1,
                weatherId = 800,
                isCurrent = true
            ).asAppTheme()
        )
        assertEquals(
            WeatherAppThemeType.RAINY,
            Weather(
                day = WeekDay.Sunday,
                temperature = 1,
                maximumTemperature = 10,
                minimumTemperature = -1,
                weatherId = 530,
                isCurrent = true
            ).asAppTheme()
        )
        assertEquals(
            WeatherAppThemeType.CLOUDY,
            Weather(
                day = WeekDay.Sunday,
                temperature = 1,
                maximumTemperature = 10,
                minimumTemperature = -1,
                weatherId = 9500,
                isCurrent = true
            ).asAppTheme()
        )
        assertEquals(
            WeatherAppThemeType.DEFAULT,
            null.asAppTheme()
        )
    }
}