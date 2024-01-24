package com.cc221042.shutterscout.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "weather_responses")
@TypeConverters(WeatherTypeConverters::class)
data class WeatherResponse(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Primary key added
    val lat: String,
    val lon: String,
    val elevation: Int,
    val timezone: String,
    val units: String,
    val current: WeatherCurrentWeather,
    val hourly: WeatherHourlyForecast,
    //val daily: DailyForecast? // Assuming daily can be null
)