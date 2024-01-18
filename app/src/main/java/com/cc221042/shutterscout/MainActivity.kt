package com.cc221042.shutterscout

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.cc221042.shutterscout.data.WeatherMeteosourceService
import com.cc221042.shutterscout.data.WeatherRepository
import com.cc221042.shutterscout.data.secrets
import com.cc221042.shutterscout.ui.WeatherViewModel
import com.cc221042.shutterscout.ui.WeatherViewModelFactory
import com.cc221042.shutterscout.ui.screens.HomeScreen
import com.cc221042.shutterscout.ui.theme.ShutterScoutTheme



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("DEBUG", "Test Log Message")
        super.onCreate(savedInstanceState)

        // Initialize WeatherMeteosourceService using the companion object's create method
        val weatherMeteosourceService = WeatherMeteosourceService.create()

        val weatherRepository = WeatherRepository(weatherMeteosourceService)

        val viewModelFactory = WeatherViewModelFactory(weatherRepository)
        val weatherViewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)

        // Load weather data
        val latitude: Double = 48.208176 // Replace with actual latitude
        val longitude: Double = 16.373819 // Replace with actual longitude
        val sections: String = "all"
        val apiToken = secrets.weatherAPI // Replace with your API token
        weatherViewModel.loadWeather(latitude, longitude, sections, apiToken)

        setContent {
//            print("TEST")
//            Log.d("DEBUG", "MainActivity Loaded")
//            WeatherDisplay(weatherViewModel)

            // testing home screen
            HomeScreen()
        }
    }

    @Composable
    fun WeatherDisplay(viewModel: WeatherViewModel) {
        val weatherData by viewModel.weatherData.collectAsState()
        //Text("AAAAA")
//        Text(weatherData?.lat.toString())
        weatherData?.let { data ->
            // Assuming 'description' is a field in WeatherCurrentWeather. Replace it with the actual field.
            val weatherDescription = data.current.temperature

            Text(text = "Current Weather: $weatherDescription C")
            Text(text = "TEST")
        } ?: Text(text = "Loading weather data...")
    }

}