package com.cc221042.shutterscout.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cc221042.shutterscout.Place
import com.cc221042.shutterscout.data.PlaceDao
import com.cc221042.shutterscout.data.SunriseSunsetRepository
import com.cc221042.shutterscout.data.SunriseSunsetResponse
import com.cc221042.shutterscout.data.WeatherCurrentWeather
import com.cc221042.shutterscout.data.WeatherDB
import com.cc221042.shutterscout.data.WeatherHourlyForecast
import com.cc221042.shutterscout.data.WeatherRepository
import com.cc221042.shutterscout.data.WeatherResponse
import com.cc221042.shutterscout.data.secrets
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime


class MainViewModel(

    private val dao: PlaceDao,
    private val weatherRepository: WeatherRepository,
    private val weatherDB: WeatherDB,
    private val sunriseSunsetRepository: SunriseSunsetRepository
): ViewModel() {

    private val _placeState = MutableStateFlow(Place("", "", "", 0.0, 0.0))
    val placeState: StateFlow<Place> = _placeState.asStateFlow()
    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()
    private var inSearchMode = false
    private val weatherViewModel = WeatherViewModel(weatherRepository, weatherDB)
    private val goldenHourViewModel = GoldenHourViewModel()

    // New state for all places
    private val _allPlacesState = MutableStateFlow<List<Place>>(emptyList())
    val allPlacesState: StateFlow<List<Place>> = _allPlacesState.asStateFlow()

    private val _allPlacesWithConditionsMetState= MutableStateFlow<List<Place>>(emptyList())
    val allPlacesWithConditionsMet: StateFlow<List<Place>> = _allPlacesWithConditionsMetState.asStateFlow()

    // Add a state for weather data
    private val _weatherDataState = MutableStateFlow<WeatherResponse?>(null)
    val weatherDataState: StateFlow<WeatherResponse?> = _weatherDataState.asStateFlow()

    var weatherResponseFromRepo: WeatherResponse? = null

    private val _currentConditions = MutableStateFlow<List<String>>(emptyList())
    val currentConditions: StateFlow<List<String>> = _currentConditions.asStateFlow()

    // sunriseSunset api
    private val _sunriseSunset = MutableStateFlow<Result<SunriseSunsetResponse>?>(null)
    val sunriseSunset: StateFlow<Result<SunriseSunsetResponse>?> = _sunriseSunset.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        // Handle the exception, e.g., log it or update a LiveData/StateFlow
        Log.e("MainViewModel", "Exception in coroutine", exception)
        // Update LiveData/StateFlow here to notify the UI about the error
    }

    init {

        CoroutineScope(Dispatchers.IO).launch(coroutineExceptionHandler) {
            // Fetch all places initially
            val fetchJob = async { fetchAllPlaces() }

            // Wait for fetchAllPlaces() to complete before proceeding
            fetchJob.await()
        }

            // Now you can safely call getPlacesMatchingConditions()
//            getPlacesMatchingConditions(weatherViewModel, goldenHourViewModel)
            fetchWeatherAndDetermineConditions()

            fetchSunriseSunsetTimes(48.208176, 16.373819)


    }

     fun fetchAllPlaces() {
        viewModelScope.launch(coroutineExceptionHandler) {
            val allPlaces = dao.getPlaces()
            Log.d("MainViewModel", "fetchAllPlaces: ${_mainViewState.value}")
            _allPlacesState.value = allPlaces
        }
    }

    fun save(place: Place) {
        viewModelScope.launch(coroutineExceptionHandler) {
            dao.insertPlace(place)
            getPlaces()
        }
    }

    fun searchPlaces(query: String) {
        inSearchMode = true
        Log.d("MainViewModel", "searchPlaces starts with query: $query")
        viewModelScope.launch(coroutineExceptionHandler) {
            val allPlaces = dao.getPlaces()
            val filteredPlaces = allPlaces.filter { it.title.contains(query, ignoreCase = true) }
            Log.d("MainViewModel", "Before updating state in searchPlaces: ${_mainViewState.value}")
            _mainViewState.value = _mainViewState.value.copy(places = filteredPlaces)
            Log.d("MainViewModel", "After updating state in searchPlaces: ${_mainViewState.value}")
        }
        Log.d("MainViewModel", "searchPlaces ends")
    }

    fun getPlaces() {
        if (!inSearchMode) {
            Log.d("MainViewModel", "getPlaces starts")
            viewModelScope.launch(coroutineExceptionHandler) {
                val allPlaces = dao.getPlaces()
                Log.d(
                    "MainViewModel",
                    "Before updating state in getPlaces: ${_mainViewState.value}"
                )
                _mainViewState.update { it.copy(places = allPlaces) }
                Log.d(
                    "MainViewModel",
                    "After updating state in getPlaces: ${_mainViewState.value}"
                )
                }

        }
        Log.d("MainViewModel", "getPlaces ends")
    }

    // Call this method to reset the search mode, for example, when the user clears the search query
    fun resetSearchMode() {
        inSearchMode = false
        getPlaces() // To fetch all places again
    }

    fun editPlace(place: Place) {
        _placeState.value = place
        _mainViewState.update { it.copy(openDialog = true) }
    }

    fun updatePlace(place: Place) {
        viewModelScope.launch(coroutineExceptionHandler) {
            dao.updatePlace(place)
        }
        getPlaces()
        closeDialog()
    }

    fun closeDialog() {
        _mainViewState.update { it.copy(openDialog = false) }
    }

    fun deleteButton(place: Place) {
        viewModelScope.launch(coroutineExceptionHandler) {
            dao.deletePlace(place)
        }
        getPlaces()
    }

    fun selectScreen(screen: Screen) {
        _mainViewState.update { it.copy(selectedScreen = screen) }
    }

    // Golden hour
    private fun isItGoldenHour(goldenHourTimes: GoldenHourViewModel.GoldenHourTimes?): Boolean {
        val currentTime = LocalDateTime.now()
        return goldenHourTimes?.let {
            (currentTime.isAfter(it.morningStart) && currentTime.isBefore(it.morningEnd)) ||
                    (currentTime.isAfter(it.eveningStart) && currentTime.isBefore(it.eveningEnd))
        } ?: false
    }
    // Function to get places matching either current weather conditions or golden hour
    private fun getPlacesMatchingConditions() {
        viewModelScope.launch(coroutineExceptionHandler) {
            val allPlaces = dao.getPlaces()
            val conditions = currentConditions.value // Get the latest conditions

            val filteredPlaces = conditions.let { conditionList ->
                allPlaces.filter { place ->
                    conditionList.any { condition ->
                        place.condition.equals(condition, ignoreCase = true)
                    }
                }
            } ?: allPlaces

            _allPlacesWithConditionsMetState.value = filteredPlaces
            Log.d("MainViewModel", "Filtered places count: ${filteredPlaces.size}")
        }
    }

//    fun getCurrentConditions(weatherViewModel: WeatherViewModel, goldenHourViewModel: GoldenHourViewModel): List<String>? {
//        //val weatherData = weatherViewModel.weatherData.value
//
//        // Load position data (for weather and golden hour)
//        val latitude: Double = 48.208176 // Replace with actual latitude
//        val longitude: Double = 16.373819 // Replace with actual longitude
//
//        val sections: String = "all"
//        val apiToken = secrets.weatherAPI // Replace with your API token
//
//        loadWeather(latitude, longitude, sections, apiToken)
//
//        val weatherData = weatherResponseFromRepo
//        val goldenHourTimes = goldenHourViewModel.goldenHourTimes.value
//        val conditions = mutableListOf<String>()
//
//        if (weatherResponseFromRepo != null && goldenHourTimes != null) {
//            val currentTime = LocalDateTime.now()
//
//            if (currentTime.isAfter(goldenHourTimes.morningStart) && currentTime.isBefore(goldenHourTimes.morningEnd)) {
//                conditions.add("Sunrise")
//            }
//
//            if (currentTime.isAfter(goldenHourTimes.eveningStart) && currentTime.isBefore(goldenHourTimes.eveningEnd)) {
//                conditions.add("Sunset")
//            }
//
//            // Check other weather conditions (you can add more conditions here)
//            val weatherDescription = weatherData?.current?.summary
//
//            if (weatherDescription?.contains("cloudy", ignoreCase = true) == true) {
//                conditions.add("Cloudy")
//            }
//            if (weatherDescription?.contains("rain", ignoreCase = true) == true) {
//                conditions.add("Rain")
//            }
//            if (weatherDescription?.contains("snow", ignoreCase = true) == true) {
//                conditions.add("Snow")
//            }
//            if (weatherDescription?.contains("thunder", ignoreCase = true) == true) {
//                conditions.add("Thunder")
//            }
//            if (weatherDescription?.contains("fog", ignoreCase = true) == true) {
//                conditions.add("Fog")
//            }
//            if (weatherDescription?.contains("overcast", ignoreCase = true) == true) {
//                conditions.add("Overcast")
//            }
//            // Add more conditions as needed
//        }
//
//        // If no conditions apply, return "Unknown"
//        return if (conditions.isEmpty()) {
////            listOf("Unknown")
//            listOf("fog")
//        } else {
//            conditions
//        }
//    }

    private fun fetchWeatherAndDetermineConditions() {
        viewModelScope.launch(coroutineExceptionHandler) {
            val cachedWeather = weatherDB.weatherDao().getCachedWeatherResponse()
            val weatherData = if (cachedWeather == null || isCacheStale(cachedWeather)) {
                fetchFreshWeatherData()
            } else {
                cachedWeather
            }

            val goldenHourTimes = goldenHourViewModel.goldenHourTimes.value
            _currentConditions.value = determineConditionsBasedOnWeatherData(weatherData, goldenHourTimes)

            getPlacesMatchingConditions()
        }
    }

    private suspend fun fetchFreshWeatherData(): WeatherResponse? {
        return try {
            val freshData = weatherRepository.getWeather(48.208176, 16.373819, "all", secrets.weatherAPI)
            freshData.lastUpdated = System.currentTimeMillis()
            weatherDB.weatherDao().insert(freshData)
            freshData
        } catch (e: Exception) {
            Log.e("MainViewModel", "Error fetching weather data", e)
            null
        }
    }

    private fun determineConditionsBasedOnWeatherData(weatherData: WeatherResponse?, goldenHourTimes: GoldenHourViewModel.GoldenHourTimes?): List<String> {
        val conditions = mutableListOf<String>()
        val currentTime = LocalDateTime.now()

        // Add Golden Hour condition if applicable
        if (isItGoldenHour(goldenHourTimes)) {
            conditions.add("Golden Hour")
        }

        // Example of checking weather conditions
        weatherData?.let { data ->
            // Check golden hour conditions based on currentTime and goldenHourTimes from data
            // Add weather-based conditions like "Cloudy", "Rain", etc.

            // Example condition
            if (data.current.summary.contains("cloudy", ignoreCase = true)) {
                conditions.add("Cloudy")
            }
            // Add more conditions as needed
        }

        //debug
        conditions.add("fog")

        return if (conditions.isEmpty()) listOf("Unknown") else conditions
    }

    private fun isCacheStale(weatherResponse: WeatherResponse): Boolean {
        val thirtyMinutesInMillis = 30 * 60 * 1000 // 30 minutes in milliseconds
        val lastUpdatedTime = weatherResponse.lastUpdated ?: return true
        val currentTime = System.currentTimeMillis()

        return (currentTime - lastUpdatedTime) > thirtyMinutesInMillis
    }

    fun fetchSunriseSunsetTimes(latitude: Double, longitude: Double, date: String = "today") {
        viewModelScope.launch {
            val result = sunriseSunsetRepository.getSunriseSunsetTimes(latitude, longitude, date)
            _sunriseSunset.value = result
        }
    }

}



