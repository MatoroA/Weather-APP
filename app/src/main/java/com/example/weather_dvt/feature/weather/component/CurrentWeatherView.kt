package com.example.weather_dvt.feature.weather.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather_dvt.R
import com.example.weather_dvt.core.common.result.theme.WeatherDVTTheme
import com.example.weather_dvt.core.model.Weather
import com.example.weather_dvt.core.network.model.weather.util.WeekDay


@Composable
fun CurrentWeatherView(modifier: Modifier = Modifier, weather: Weather) {
    Column(modifier = modifier.padding(bottom = 6.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = weather.weatherType.imageId),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = weather.temperature.toString(), style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    text = weather.weatherType.name, style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        fontSize = 26.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                TemperatureView(
                    label = stringResource(R.string.min),
                    temperature = weather.minimumTemperature
                )
            }

            TemperatureView(
                label = stringResource(R.string.current),
                temperature = weather.temperature,
                temperatureStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            )

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                TemperatureView(
                    label = stringResource(R.string.max),
                    temperature = weather.maximumTemperature
                )
            }
        }
    }
}

@Composable
private fun TemperatureView(
    label: String,
    temperature: Int,
    temperatureStyle: TextStyle = MaterialTheme.typography.body1,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(temperature.toString(), style = temperatureStyle)
        Text(label)
    }
}

@Preview
@Composable
fun CurrentWeatherViewPreview() {
    WeatherDVTTheme {
        CurrentWeatherView(
            weather = Weather(
                day = WeekDay.Sunday,
                temperature = 30,
                maximumTemperature = 50,
                minimumTemperature = 20,
                weatherId = 530,
                isCurrent = true
            )
        )
    }
}