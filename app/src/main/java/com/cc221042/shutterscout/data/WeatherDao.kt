package com.cc221042.shutterscout.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    // Method to insert or update a weather response
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherResponse: WeatherResponse)

    // Method to retrieve the cached weather response
    @Query("SELECT * FROM weather_responses LIMIT 1")
    suspend fun getCachedWeatherResponse(): WeatherResponse?

    // Optionally, if you need to clear old data
    @Query("DELETE FROM weather_responses")
    suspend fun clearCache()
}