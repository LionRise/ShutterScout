package com.cc221042.shutterscout.ui

import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cc221042.shutterscout.ui.composables.AddPlaceButton
import com.cc221042.shutterscout.ui.screens.GoldenHourScreen
import com.cc221042.shutterscout.ui.screens.HomeScreen
import com.cc221042.shutterscout.ui.screens.PlacesScreen
import com.cc221042.shutterscout.ui.screens.WeatherDisplay


sealed class Screen(val route: String) {
    object First : Screen("first")
    object Second : Screen("second")
    object Third : Screen("third")
    object Fourth : Screen("fourth")
    object AddPlace : Screen("addPlace")
}

enum class BottomNavScreen(val route: String, val icon: ImageVector, val contentDescription: String) {
    First("first", Icons.Default.Home, "Home"),
    Second("second", Icons.Default.Place, "Map"),
    Third("third", Icons.Default.DateRange, "Weather"),
    Fourth("fourth", Icons.Default.DateRange, "Test"),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(mainViewModel: MainViewModel, weatherViewModel: WeatherViewModel, goldenViewModel: GoldenHourViewModel) {
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
    ) { innerPadding -> MainNavHost(navController, mainViewModel, weatherViewModel, goldenViewModel, innerPadding) }
}


@Composable
fun MainNavHost(navController: NavHostController, mainViewModel: MainViewModel, weatherViewModel: WeatherViewModel, goldenViewModel: GoldenHourViewModel, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        modifier = Modifier.padding(innerPadding),
        startDestination = Screen.First.route
    ) {
        composable(Screen.First.route) { HomeScreen(mainViewModel, weatherViewModel) }
        composable(Screen.Second.route) { PlacesScreen(mainViewModel) }
        composable(Screen.Third.route) { WeatherDisplay(weatherViewModel) }
        composable(Screen.Fourth.route) { GoldenHourScreen(goldenViewModel) }
        composable(Screen.AddPlace.route) { AddPlaceScreen(mainViewModel) }
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
fun BottomNavigationBar(navController: NavHostController, selectedScreen: Screen) {
    BottomNavigation(backgroundColor = MaterialTheme.colorScheme.primary) {
        BottomNavScreen.entries.forEach { screen ->
            NavigationBarItem(
                selected = (selectedScreen.route == screen.route),
                onClick = { navController.navigate(screen.route) },
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.contentDescription) }
            )
        }
    }
}