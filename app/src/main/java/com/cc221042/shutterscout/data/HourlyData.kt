package com.cc221042.shutterscout.data

data class HourlyData(
    val date: String,
    val weather: String,
    val icon: Int,
    val summary: String,
    val temperature: Double,
    val cloud_cover: CloudCover,
    val precipitation: Precipitation
)

