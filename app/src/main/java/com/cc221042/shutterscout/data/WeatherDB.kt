package com.cc221042.shutterscout.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


// TODO caching via this database

@Database(entities = [WeatherResponse::class], version = 1, exportSchema = false)
@TypeConverters(WeatherTypeConverters::class)
abstract class WeatherDB : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}