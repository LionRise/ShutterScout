package com.cc221042.shutterscout.ui

import com.cc221042.shutterscout.Place

/**
 * Represents the state of the main screen in the ShutterScout App.
 *
 * @property places The list of places to be displayed on the main screen.
 * @property selectedScreen The currently selected screen (e.g., First or Second) in the app navigation.
 * @property openDialog A flag indicating whether a dialog is open or closed for interactions like editing or adding places.
 */
data class MainViewState(
    val places: List<Place> = listOf(),
    val selectedScreen: Screen = Screen.First,
    val openDialog: Boolean = false,
)