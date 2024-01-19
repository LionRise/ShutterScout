package com.cc221042.shutterscout.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cc221042.shutterscout.Place

/**
 * RoomDatabase class representing the database for storing Places entities.
 *
 * @property dao PlacesDao interface for accessing database operations related to Place entities.
 */

//@Database(entities = [Place::class], version = 3, autoMigrations = [
//    AutoMigration (from = 1, to = 2),
//    AutoMigration(from = 2, to = 3)
//])


@Database(entities = [Place::class], version = 1)
abstract class PlaceDB : RoomDatabase() {
    abstract val dao: PlaceDao
}

//abstract class PlaceDB : RoomDatabase() {
//    abstract val dao: PlaceDao
//}