package com.cc221042.shutterscout.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cc221042.shutterscout.ui.screens.HomeScreen
import com.cc221042.shutterscout.ui.screens.PlacesScreen
import com.cc221042.shutterscout.ui.screens.WeatherDisplay


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
fun MainView(mainViewModel: MainViewModel, viewModel: WeatherViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()
    val navController = rememberNavController()

    // Convert Screen to BottomNavScreen
    val selectedBottomNavScreen = when (state.value.selectedScreen) {
        Screen.First -> BottomNavScreen.First
        Screen.Second -> BottomNavScreen.Second
        Screen.Third -> BottomNavScreen.Third
    }


    Scaffold(
        bottomBar = { BottomNavigationBar(navController, selectedBottomNavScreen) }
    ) { innerPadding -> MainNavHost(navController, mainViewModel, innerPadding, viewModel) }
}

@Composable
fun MainNavHost(navController: NavHostController, mainViewModel: MainViewModel, innerPadding: PaddingValues, viewModel: WeatherViewModel) {
    NavHost(
        navController = navController,
        modifier = Modifier.padding(innerPadding),
        startDestination = Screen.First.route
    ) {
        composable(Screen.First.route) {
            NavigationLogic(mainViewModel, Screen.First) {
                HomeScreen(mainViewModel)
            }
        }
        composable(Screen.Second.route) {
            NavigationLogic(mainViewModel, Screen.Second) {
                PlacesScreen(mainViewModel)
            }
        }
        composable(Screen.Third.route) {
            NavigationLogic(mainViewModel, Screen.Third) {
                WeatherDisplay(viewModel)
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
        BottomNavScreen.entries.forEach { screen ->
            NavigationBarItem(
                selected = (selectedScreen == screen),
                onClick = { navController.navigate(screen.route) },
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.contentDescription) }
            )
        }
    }
}