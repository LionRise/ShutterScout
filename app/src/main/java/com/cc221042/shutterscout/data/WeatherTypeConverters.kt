package com.cc221042.shutterscout.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherTypeConverters {
    private val gson = Gson()

    // Converter for WeatherCurrentWeather
    @TypeConverter
    fun fromWeatherCurrentWeather(value: WeatherCurrentWeather): String = gson.toJson(value)

    @TypeConverter
    fun toWeatherCurrentWeather(value: String): WeatherCurrentWeather =
        gson.fromJson(value, WeatherCurrentWeather::class.java)

    // Converter for WeatherHourlyForecast
    @TypeConverter
    fun fromWeatherHourlyForecast(value: WeatherHourlyForecast): String = gson.toJson(value)

    @TypeConverter
    fun toWeatherHourlyForecast(value: String): WeatherHourlyForecast =
        gson.fromJson(value, WeatherHourlyForecast::class.java)

    // Converter for List<WeatherHourlyData>
    @TypeConverter
    fun fromWeatherHourlyDataList(value: List<WeatherHourlyData>): String = gson.toJson(value)

    @TypeConverter
    fun toWeatherHourlyDataList(value: String): List<WeatherHourlyData> {
        val listType = object : TypeToken<List<WeatherHourlyData>>() {}.type
        return gson.fromJson(value, listType)
    }

    // Converter for WeatherCloudCover
    @TypeConverter
    fun fromWeatherCloudCover(value: WeatherCloudCover): String = gson.toJson(value)

    @TypeConverter
    fun toWeatherCloudCover(value: String): WeatherCloudCover =
        gson.fromJson(value, WeatherCloudCover::class.java)

    // Converter for WeatherPrecipitation
    @TypeConverter
    fun fromWeatherPrecipitation(value: WeatherPrecipitation): String = gson.toJson(value)

    @TypeConverter
    fun toWeatherPrecipitation(value: String): WeatherPrecipitation =
        gson.fromJson(value, WeatherPrecipitation::class.java)

}