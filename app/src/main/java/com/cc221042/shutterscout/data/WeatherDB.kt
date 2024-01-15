package com.cc221042.shutterscout.data

@Database(entities = [WeatherResponse::class], version = 1, exportSchema = false)
abstract class WeatherDB : RoomDatabase() {
    abstract fun bookmarkDao(): WeatherDao
}