package com.cc221042.shutterscout.data

data class WeatherCurrentWeather(
    val icon: String,
    val icon_num: Int,
    val summary: String,
    val temperature: Double,
    val weatherPrecipitation: WeatherPrecipitation,
    val cloud_cover: Int
)
