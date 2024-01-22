package com.cc221042.shutterscout.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cc221042.shutterscout.Place
import com.cc221042.shutterscout.data.PlaceDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapViewModel(private val dao: PlaceDao) : ViewModel() {
    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> = _places

    init {
        loadPlaces()
    }

    private fun loadPlaces() {
        viewModelScope.launch {
            dao.getPlaces().collect { placesList ->
                _places.value = placesList
            }
        }
    }
}

class MapViewModelFactory(private val dao: PlaceDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
