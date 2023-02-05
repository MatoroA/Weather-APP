package com.example.weather_dvt.feature.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weather_dvt.core.common.result.theme.WeatherDVTTheme
import com.example.weather_dvt.core.model.Weather
import com.example.weather_dvt.core.network.model.weather.util.WeekDay
import com.example.weather_dvt.feature.weather.component.CurrentWeatherView

@Composable
fun WeatherRoute(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel(),
) {
    val weatherData by viewModel.weatherDataState.collectAsStateWithLifecycle()

    WeatherScreen(
        weatherDataUi = weatherData,
    )
}

@Composable
internal fun WeatherScreen(
    modifier: Modifier = Modifier,
    weatherDataUi: WeatherDataUi,
) {
    Scaffold(modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (weatherDataUi.currentWeather) {
                null -> EmptyCurrentWeather(modifier = Modifier.height(300.dp))
                else -> CurrentWeatherView(
                    modifier = Modifier.height(300.dp),
                    weather = weatherDataUi.currentWeather
                )
            }

            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(color = Color.White)
            )

            LazyColumn {
                items(weatherDataUi.forecast) { weather ->
                    DayForecast(weather = weather)
                }
            }
        }
    }
}

@Composable
fun EmptyCurrentWeather(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(
            text = "...", style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
        )
    }
}

@Composable
fun DayForecast(modifier: Modifier = Modifier, weather: Weather) {

    Row(
        modifier = modifier
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Text(
                text = weather.day.name,
                textAlign = TextAlign.Start
            )
        }
        Image(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp),
            painter = painterResource(id = weather.weatherType.iconId),
            contentDescription = null
        )
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
            Text(
                text = weather.temperature.toString(),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview
@Composable
fun DayForecastPreview() {
    DayForecast(
        weather = Weather(
            day = WeekDay.Sunday,
            temperature = 20,
            weatherId = 520,
            isCurrent = false,
            minimumTemperature = 10,
            maximumTemperature = 20

        )
    )
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPrev() {
    val weatherDataUi = WeatherDataUi(
        forecast = listOf(
            Weather(
                day = WeekDay.Sunday,
                temperature = 20,
                weatherId = 520,
                isCurrent = false,
                minimumTemperature = 10,
                maximumTemperature = 30
            )
        )
    )
    WeatherScreen(
        weatherDataUi = weatherDataUi,
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyCurrentWeatherPreview() {
    WeatherDVTTheme {
        EmptyCurrentWeather(modifier = Modifier.height(300.dp))
    }
}