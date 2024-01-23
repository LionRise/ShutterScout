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

    class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

        // MutableStateFlow to internally manage weather data
        private val _weatherData = MutableStateFlow<WeatherResponse?>(null)

        // StateFlow to expose weather data to the UI
        val weatherData: StateFlow<WeatherResponse?> = _weatherData.asStateFlow()

        // Function to fetch weather data
        fun loadWeather(latitude: Double, longitude: Double, sections: String, apiToken: String) {
            print("load")
            Log.d("DEBUG", "load")
            viewModelScope.launch {

                Log.d("DEBUG", "launch")
                try {
                    _weatherData.value = weatherRepository.getWeather(latitude, longitude, sections, apiToken)

                }catch (e:Exception){
                    print(e)
                }

            }
        }
    }
