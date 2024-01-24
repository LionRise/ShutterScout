    package com.cc221042.shutterscout.ui

    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.cc221042.shutterscout.data.WeatherRepository
    import com.cc221042.shutterscout.data.WeatherResponse
    import kotlinx.coroutines.launch

    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.flow.catch
    import kotlinx.coroutines.flow.flow
    import kotlinx.coroutines.launch
    import java.util.logging.Logger;
    import android.util.Log
    import com.cc221042.shutterscout.data.WeatherDB

    class WeatherViewModel(
        private val weatherRepository: WeatherRepository,
        private val db: WeatherDB
    ) : ViewModel() {

        // MutableStateFlow to internally manage weather data
        private val _weatherData = MutableStateFlow<WeatherResponse?>(null)

        // StateFlow to expose weather data to the UI
        val weatherData: StateFlow<WeatherResponse?> = _weatherData.asStateFlow()

        // Function to fetch weather data
        fun loadWeather(latitude: Double, longitude: Double, sections: String, apiToken: String) {
            viewModelScope.launch {
                try {
                    // First, try to get the cached data
                    val cachedWeather = db.weatherDao().getCachedWeatherResponse()
                    if (cachedWeather == null || isCacheStale(cachedWeather)) {
                        // Cache is stale or non-existent, fetch new data
                        val freshData = weatherRepository.getWeather(latitude, longitude, sections, apiToken)
                        freshData.lastUpdated = System.currentTimeMillis()
                        db.weatherDao().insert(freshData)  // Cache the fresh data
                        _weatherData.value = freshData  // Update the UI with fresh data
                    } else {
                        // Cache is fresh, use the cached data
                        _weatherData.value = cachedWeather
                    }
                } catch (e: Exception) {
                    Log.e("WeatherViewModel", "Error fetching weather data", e)
                    // Handle error, update UI accordingly
                }
            }
        }

        // Check if the cached data is stale
        private fun isCacheStale(weatherResponse: WeatherResponse): Boolean {
            val currentTime = System.currentTimeMillis()
            val lastUpdatedTime = weatherResponse.lastUpdated
            val thirtyMinutesInMillis = 30 * 60 * 1000 // 30 minutes in milliseconds

            return (currentTime - lastUpdatedTime) > thirtyMinutesInMillis
        }
    }
