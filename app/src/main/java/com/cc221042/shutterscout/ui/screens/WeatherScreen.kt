package com.cc221042.shutterscout.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.cc221042.shutterscout.ui.WeatherViewModel

@Composable
fun WeatherDisplay(viewModel: WeatherViewModel) {
    val weatherData by viewModel.weatherData.collectAsState()
    weatherData?.let { data ->
        // Assuming 'description' is a field in WeatherCurrentWeather. Replace it with the actual field.
        val weatherDescription = data.current.temperature

        Text(text = "Current Weather: $weatherDescription C")
    } ?: Text(text = "Loading weather data...")
}