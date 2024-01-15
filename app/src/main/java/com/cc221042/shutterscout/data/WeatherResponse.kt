package com.cc221042.shutterscout.data

data class WeatherResponse(
    val lat: String,
    val lon: String,
    val elevation: Int,
    val timezone: String,
    val units: String,
    val current: CurrentWeather,
    val hourly: HourlyForecast,
    //val daily: DailyForecast? // Assuming daily can be null
)