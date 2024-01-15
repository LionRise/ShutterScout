package com.cc221042.shutterscout.data

data class CurrentWeather(
    val icon: String,
    val icon_num: Int,
    val summary: String,
    val temperature: Double,
    val precipitation: Precipitation,
    val cloud_cover: Int
)
