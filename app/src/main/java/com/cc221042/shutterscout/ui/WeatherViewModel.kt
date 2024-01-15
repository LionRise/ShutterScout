package com.cc221042.shutterscout.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cc221042.shutterscout.data.WeatherRepository
import com.cc221042.shutterscout.data.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    // LiveData to expose weather data to the UI
    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

    // Function to fetch weather data
    fun loadWeather(latitude: Double, longitude: Double, apiToken: String) {
        viewModelScope.launch {
            try {
                val response = weatherRepository.getWeather(latitude, longitude, apiToken)
                _weatherData.value = response
            } catch (e: Exception) {
                // Handle exceptions
            }
        }
    }
}