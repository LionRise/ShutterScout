package com.cc221042.shutterscout.ui

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigation
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cc221042.shutterscout.Place
import kotlinx.coroutines.delay

sealed class Screen(val route: String) {
    object First : Screen("first")
    object Second : Screen("second")
    object Third : Screen("third")
}

enum class BottomNavScreen(val route: String, val icon: ImageVector, val contentDescription: String) {
    First("first", Icons.Default.Home, "Home"),
    Second("second", Icons.Default.Place, "Map"),
    Third("third", Icons.Default.DateRange, "Weather"),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(mainViewModel: MainViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()
    val navController = rememberNavController()

    // Convert Screen to BottomNavScreen
    val selectedBottomNavScreen = when (state.value.selectedScreen) {
        Screen.First -> BottomNavScreen.First
        Screen.Second -> BottomNavScreen.Second
        Screen.Third -> BottomNavScreen.Third
        else -> throw IllegalStateException("Unknown screen: ${state.value.selectedScreen}")
    }


    Scaffold(
        bottomBar = { BottomNavigationBar(navController, selectedBottomNavScreen) }
    ) { innerPadding -> MainNavHost(navController, mainViewModel, innerPadding) }
}


@Composable
fun MainNavHost(navController: NavHostController, mainViewModel: MainViewModel, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        modifier = Modifier.padding(innerPadding),
        startDestination = Screen.First.route
    ) {
        composable(Screen.First.route) {
            NavigationLogic(mainViewModel, Screen.First) {
                PlacesScreen(mainViewModel)
            }
        }
        composable(Screen.Second.route) {
            NavigationLogic(mainViewModel, Screen.Second) {
                AddPlaceScreen(mainViewModel)
            }
        }
        composable(Screen.Third.route) {
            NavigationLogic(mainViewModel, Screen.Third) {
                AddPlaceScreen(mainViewModel)
            }
        }
    }
}

@Composable
fun NavigationLogic(mainViewModel: MainViewModel, screen: Screen, content: @Composable () -> Unit) {
    LaunchedEffect(Unit) {
        mainViewModel.selectScreen(screen)
        if (screen == Screen.First) mainViewModel.getPlaces()
    }
    content()
}

@Composable
fun BottomNavigationBar(navController: NavHostController, selectedScreen: BottomNavScreen) {
    BottomNavigation(backgroundColor = MaterialTheme.colorScheme.primary) {
        BottomNavScreen.values().forEach { screen ->
            NavigationBarItem(
                selected = (selectedScreen == screen),
                onClick = { navController.navigate(screen.route) },
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.contentDescription) }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaceScreen(mainViewModel: MainViewModel) {
    var title by rememberSaveable { mutableStateOf("") }
    var imageUri by rememberSaveable { mutableStateOf("") }
    var saveSuccess by remember { mutableStateOf(false) }

    val photoPicker = setupPhotoPicker { uri ->
        imageUri = uri.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Culinary Canvas", fontSize = 50.sp, style = TextStyle(fontFamily = FontFamily.Cursive))

        Spacer(modifier = Modifier.height(50.dp))

        TextField(
            value = title,
            onValueChange = { newText -> title = newText },
            label = { Text("Title of your place") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Text("Open Gallery", fontSize = 20.sp)
        }

        Button(
            onClick = {
                mainViewModel.save(Place(title, imageUri))
                saveSuccess = true // Update the state to reflect save success
            },
            modifier = Modifier.padding(top = 15.dp),
            enabled = title.isNotBlank() && imageUri.isNotBlank()
        ) {
            Text("Save", fontSize = 20.sp)
        }

        if (saveSuccess) {
            Text("Place saved successfully!", color = Color.Green)
        }
    }
}

@Composable
private fun setupPhotoPicker(onImagePicked: (Uri) -> Unit): ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?> {
    val context = LocalContext.current
    return rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            onImagePicked(uri)
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(uri, flag)
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesScreen(mainViewModel: MainViewModel){
    val state = mainViewModel.mainViewState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var lastSearchQuery by remember { mutableStateOf("") }

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

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            Text(
                text = "Places",
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                style = TextStyle(fontFamily = FontFamily.Cursive),
                modifier = Modifier.padding(16.dp)
            )
        }

        items(state.value.places, key = { it.id }) { place -> // Assuming Place has an id field
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { mainViewModel.editPlace(place) },
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = place.title,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                    AsyncImage(
                        modifier = Modifier
                            .size(250.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(place.imageUri)
                            .crossfade(enable = true)
                            .build(),
                        contentDescription = "Place Image",
                        contentScale = ContentScale.Crop,
                    )
                    // Add a Spacer to create some space between text and the delete button
                    Spacer(modifier = Modifier.height(8.dp))
                    // IconButton for delete action
                    IconButton(onClick = { mainViewModel.deleteButton(place)}) {
                        Icon(Icons.Default.Delete,"Delete")
                    }
                }
            }
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPlaceModal(mainViewModel: MainViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()

    if (state.value.openDialog) {
        val place = mainViewModel.placeState.collectAsState().value
        var title by rememberSaveable { mutableStateOf(place.title) }
        var imageUri by rememberSaveable { mutableStateOf(place.imageUri ?: "") }
        val context = LocalContext.current
        val photoPicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("duck PhotoPicker", "Selected URI: $uri")
                imageUri = uri.toString()
                context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                Log.d(" duck PhotoPicker", "No media selected")
            }
        }

        AlertDialog(
            onDismissRequest = {
                mainViewModel.closeDialog()
            },
            text = {
                Column {
                    TextField(
                        modifier = Modifier.padding(top = 20.dp),
                        value = title,
                        onValueChange = { newText -> title = newText },
                        label = { Text(text = "Title" ) }
                    )
                    Button(
                        onClick = { //On button press, launch the photo picker
                            photoPicker.launch(PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )) },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Open Gallery")
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    mainViewModel.updatePlace(Place(title, imageUri, place.id))
                    Log.d("duck", "confirm button clicked")
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = {
                    mainViewModel.closeDialog()
                }) {
                    Text("Dismiss")
                }
            }
        )
    }
}
