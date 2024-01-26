package com.cc221042.shutterscout.ui.screens

import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cc221042.shutterscout.R
import com.cc221042.shutterscout.ui.MainViewModel
import com.cc221042.shutterscout.ui.composables.EditPlaceModal
import com.cc221042.shutterscout.ui.composables.HomeBackgroundImageBox
import com.cc221042.shutterscout.ui.composables.PlaceCard
import com.cc221042.shutterscout.ui.composables.SearchBar
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesScreen(mainViewModel: MainViewModel){
    val state = mainViewModel.mainViewState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var lastSearchQuery by remember { mutableStateOf("") }

    val lexend = FontFamily(Font(R.font.lexend))

    // Initial Data Load
    LaunchedEffect(Unit) {
        mainViewModel.getPlaces()
    }

    // Debouncing the search input
    LaunchedEffect(searchQuery) {
        if (searchQuery != lastSearchQuery) {
            delay(300) // Debounce delay
            mainViewModel.searchPlaces(searchQuery)
        }
        lastSearchQuery = searchQuery

        // Load all places if the search query is cleared
        if (searchQuery.isBlank()) {
            mainViewModel.getPlaces()
        }
    }

    val imagePainter = painterResource(id = R.drawable.places_background)

    val imageModifier = Modifier
        .shadow(
            elevation = 10.dp,
            spotColor = Color(0x40000000),
            ambientColor = Color(0x40000000)
        )
        .width(361.dp)
        .height(120.dp)

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(Color.White)
    )
    {
        item {
            HomeBackgroundImageBox(
                backgroundImageRes = R.drawable.places_background,
                title = "Places"
            )
        }

        item {
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = { searchQuery = it }
            )
        }

        items(state.value.places, key = { it.id }) { place ->
            PlaceCard(place = place, mainViewModel = mainViewModel)
        }
    }

    // Debouncing the search
    LaunchedEffect(searchQuery) {
        if (searchQuery != lastSearchQuery) {
            delay(300) // debounce by 300ms
            if (searchQuery == lastSearchQuery) {
                mainViewModel.searchPlaces(searchQuery)
            }
        }
        lastSearchQuery = searchQuery
    }

    Column {
        EditPlaceModal(mainViewModel)
    }
}
