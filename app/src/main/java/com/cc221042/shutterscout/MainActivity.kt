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
import com.cc221042.shutterscout.data.WeatherMeteosourceService
import com.cc221042.shutterscout.data.WeatherRepository
import com.cc221042.shutterscout.data.secrets
import com.cc221042.shutterscout.ui.GoldenHourViewModel
import com.cc221042.shutterscout.ui.MapViewModel
import com.cc221042.shutterscout.ui.MapViewModelFactory
import com.cc221042.shutterscout.ui.WeatherViewModel
import com.cc221042.shutterscout.ui.WeatherViewModelFactory
import com.cc221042.shutterscout.ui.theme.ShutterScoutTheme

class MainActivity : ComponentActivity() {

    // Define the migration from version 1 to 2
    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // SQL statement to add the 'condition' column to the 'places' table
            db.execSQL("ALTER TABLE places ADD COLUMN condition TEXT NOT NULL DEFAULT ''")
        }
    }
    // Define the migration from version 2 to 3
    val MIGRATION_2_3: Migration = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // SQL statement to add the 'langitude' and longitude column to the 'places' table
            db.execSQL("ALTER TABLE places ADD COLUMN longitude REAL DEFAULT NULL")
            db.execSQL("ALTER TABLE places ADD COLUMN latitude REAL DEFAULT NULL")
        }
    }

    // Lazy initialization of the Room database
    private val db by lazy {
        Room.databaseBuilder(this, PlaceDB::class.java, "PlaceDB.db")
            .addMigrations(MIGRATION_1_2) // Add the migration
            .addMigrations(MIGRATION_2_3) // Add the migration
            .build()
    }
    val weatherMeteosourceService = WeatherMeteosourceService.create()
    val weatherRepository = WeatherRepository(weatherMeteosourceService)

    // Initialize the MainViewModel using the Room database
    private val mainViewModel by viewModels<MainViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(db.dao, weatherRepository) as T
                }
            }
        }
    )

    private lateinit var mapViewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("DEBUG", "Test Log Message")
        super.onCreate(savedInstanceState)

        // Initialize WeatherMeteosourceService using the companion object's create method
        val weatherMeteosourceService = WeatherMeteosourceService.create()
        val weatherRepository = WeatherRepository(weatherMeteosourceService)
        val viewModelFactory = WeatherViewModelFactory(weatherRepository)
        val weatherViewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)

        val goldenHourViewModel = ViewModelProvider(this).get(GoldenHourViewModel::class.java)

        val dao = db.dao
        val factory = MapViewModelFactory(dao)
        mapViewModel = ViewModelProvider(this, factory).get(MapViewModel::class.java)

        // Load position data (for weather and golden hour)
        val latitude: Double = 70.208176 // Replace with actual latitude
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
                    MainView(mainViewModel, weatherViewModel, goldenHourViewModel, mapViewModel)
                }
            }
        }
    }

    // Shutdown the camera executor when the activity is destroyed
    override fun onDestroy() {
        super.onDestroy()
    }
}
