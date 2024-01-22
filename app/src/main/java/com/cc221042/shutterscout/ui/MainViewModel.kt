package com.cc221042.shutterscout.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cc221042.shutterscout.Place
import com.cc221042.shutterscout.data.PlaceDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MainViewModel(private val dao: PlaceDao) : ViewModel() {

    private val _placeState = MutableStateFlow(Place("", "", "", 0.0, 0.0))
    val placeState: StateFlow<Place> = _placeState.asStateFlow()
    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()
    private var inSearchMode = false

    fun save(place: Place) {
        viewModelScope.launch {
            dao.insertPlace(place)
        }
    }

    fun searchPlaces(query: String) {
        inSearchMode = true
        Log.d("MainViewModel", "searchPlaces starts with query: $query")
        viewModelScope.launch {
            val allPlaces = dao.getPlaces().first()
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
            viewModelScope.launch {
                dao.getPlaces().collect() { allPlaces ->
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
        viewModelScope.launch {
            dao.updatePlace(place)
        }
        getPlaces()
        closeDialog()
    }

    fun closeDialog() {
        _mainViewState.update { it.copy(openDialog = false) }
    }

    fun deleteButton(place: Place) {
        viewModelScope.launch {
            dao.deletePlace(place)
        }
        getPlaces()
    }

    fun selectScreen(screen: Screen) {
        _mainViewState.update { it.copy(selectedScreen = screen) }
    }
}
