package com.cc221042.shutterscout.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import com.cc221042.shutterscout.ui.composables.AddPlaceButton
import com.cc221042.shutterscout.ui.screens.AddPlaceScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cc221042.shutterscout.R
import com.cc221042.shutterscout.ui.composables.AddPlaceButton
import com.cc221042.shutterscout.ui.screens.HomeScreen
import com.cc221042.shutterscout.ui.screens.MapScreen
import com.cc221042.shutterscout.ui.screens.PlacesScreen
import com.cc221042.shutterscout.ui.screens.WeatherDisplay


sealed class Screen(val route: String) {
    object First : Screen("first")
    object Second : Screen("second")
    object Third : Screen("third")
    object Fourth : Screen("fourth")
    object AddPlace : Screen("addPlace")
}

enum class BottomNavScreen(val route: String, val icon: String, val contentDescription: String) {
    First("first", "home", "Home"),
    Second("second", "map", "Map"),
    Third("third","cloud" , "Weather"),
    Fourth("fourth", "sun" , "Test"),
//    First("first", Icons.Default.Home, "Home"),
//    Second("second", Icons.Default.Place, "Map"),
//    Third("third", Icons.Default.DateRange, "Weather"),
//    Fourth("fourth", Icons.Default.DateRange, "Test"),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(mainViewModel: MainViewModel, weatherViewModel: WeatherViewModel, goldenViewModel: GoldenHourViewModel, mapViewModel: MapViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()
    val navController = rememberNavController()

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.route) {
                Screen.First.route -> mainViewModel.selectScreen(Screen.First)
                Screen.Second.route -> mainViewModel.selectScreen(Screen.Second)
                Screen.Third.route -> mainViewModel.selectScreen(Screen.Third)
                Screen.Fourth.route -> mainViewModel.selectScreen(Screen.Fourth)
                Screen.AddPlace.route -> mainViewModel.selectScreen(Screen.AddPlace)
            }
        }
    }

    Scaffold(
        modifier = Modifier.padding(0.dp),
        bottomBar = {
            if (state.value.selectedScreen != Screen.AddPlace) {
                BottomNavigationBar(navController, state.value.selectedScreen)
            }
        },
        floatingActionButton = {
            if (state.value.selectedScreen != Screen.AddPlace) {
                AddPlaceButton(navController) {
                    navController.navigate(Screen.AddPlace.route)
                }
            }
        }
    ) { innerPadding -> MainNavHost(navController, mainViewModel, weatherViewModel, goldenViewModel, mapViewModel, innerPadding) }
}


@Composable
fun MainNavHost(navController: NavHostController, mainViewModel: MainViewModel, weatherViewModel: WeatherViewModel, goldenViewModel: GoldenHourViewModel, mapViewModel: MapViewModel, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        modifier = Modifier.padding(innerPadding),
        startDestination = Screen.First.route
    ) {
        composable(Screen.First.route) { HomeScreen(mainViewModel, weatherViewModel) }
        composable(Screen.Second.route) { MapScreen(places = mapViewModel.places) }
        composable(Screen.Third.route) { WeatherDisplay(weatherViewModel) }
        composable(Screen.Fourth.route) { PlacesScreen(mainViewModel) }
        composable(Screen.AddPlace.route) { AddPlaceScreen(navController, mainViewModel) }
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
fun CustomNavigationBarItem(
    screen: BottomNavScreen,
    isSelected: Boolean,
    alwaysShowLabel: Boolean = true,
    isStartItem: Boolean = false, // Indicates if this is the first item
    isEndItem: Boolean = false, // Indicates if this is the last item
    navIcon: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(
                start = if (isStartItem) 32.dp else 8.dp,
                end = if (isEndItem) 32.dp else 8.dp,
                top = 18.dp,
//                bottom = 12.dp
            )
    ) {
        Text(
            text = navIcon,
            style = TextStyle(
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.font_awesome)),
                fontWeight = FontWeight.Normal,
                color = if (isSelected) Color(0xFF40C3F7) else Color(0xFFB1B1B1), // Color changes based on selection
                textAlign = TextAlign.Center,
            )
        )
        if (isSelected || alwaysShowLabel) {
            Text(
                text = screen.contentDescription,
                color = if (isSelected) Color(0xFF40C3F7) else Color(0xFFB1B1B1), // Color changes based on selection
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, selectedScreen: Screen) {
    BottomNavigation(
        backgroundColor = Color.White,
        modifier = Modifier.height(80.dp) // Adjust height if necessary
    ) {
        BottomNavScreen.values().forEachIndexed { index, screen ->
            val isSelected = selectedScreen.route == screen.route

            CustomNavigationBarItem(
                screen = screen,
                isSelected = isSelected,
                isStartItem = index == 0,
                isEndItem = index == BottomNavScreen.values().lastIndex,
                navIcon = screen.icon,
                onClick = { navController.navigate(screen.route) }
            )
        }
    }
}


