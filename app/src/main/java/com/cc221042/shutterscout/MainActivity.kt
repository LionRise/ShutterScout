package com.cc221042.shutterscout

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cc221042.shutterscout.data.PlaceDB
import com.cc221042.shutterscout.ui.MainView
import com.cc221042.shutterscout.ui.MainViewModel
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.cc221042.shutterscout.data.WeatherMeteosourceService
import com.cc221042.shutterscout.data.WeatherRepository
import com.cc221042.shutterscout.data.secrets
import com.cc221042.shutterscout.ui.WeatherViewModel
import com.cc221042.shutterscout.ui.WeatherViewModelFactory
import com.cc221042.shutterscout.ui.theme.ShutterScoutTheme
import java.io.File

class MainActivity : ComponentActivity() {

    // Define the migration from version 1 to 2
    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // SQL statement to add the 'condition' column to the 'places' table
            db.execSQL("ALTER TABLE places ADD COLUMN condition TEXT NOT NULL DEFAULT ''")
        }
    }

    // Lazy initialization of the Room database
    private val db by lazy {
        Room.databaseBuilder(this, PlaceDB::class.java, "PlaceDB.db")
            .addMigrations(MIGRATION_1_2) // Add the migration
            .build()
    }

    // Initialize the MainViewModel using the Room database
    private val mainViewModel by viewModels<MainViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(db.dao) as T
                }
            }
        }
    )

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
            ShutterScoutTheme {
                // Surface container for the entire UI
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView(mainViewModel, weatherViewModel)
                }
            }
        }
    }

    // Shutdown the camera executor when the activity is destroyed
    override fun onDestroy() {
        super.onDestroy()
    }
}
