package com.cc221042.shutterscout.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cc221042.shutterscout.data.WeatherDB
import com.cc221042.shutterscout.data.WeatherRepository

class WeatherViewModelFactory(
    private val weatherRepository: WeatherRepository,
    private val weatherDB: WeatherDB
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(weatherRepository, weatherDB) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}