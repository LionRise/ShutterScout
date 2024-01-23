package com.cc221042.shutterscout.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cc221042.shutterscout.Place
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for performing CRUD operations on Place.kt entities in the database.
 */
@Dao
interface PlaceDao {
    /**
     * Inserts a new place into the database.
     *
     * @param place The place to be inserted.
     */
    @Insert
    suspend fun insertPlace(place: Place)

    /**
     * Updates an existing place in the database.
     *
     * @param place The place to be updated.
     */
    @Update
    suspend fun updatePlace(place: Place)

    /**
     * Deletes a place from the database.
     *
     * @param place The place to be deleted.
     */
    @Delete
    suspend fun deletePlace(place: Place)

    /**
     * Retrieves a list of all places from the database using Flow.
     *
     * @return A Flow emitting a list of places.
     */
    @Query("SELECT * FROM places")
    suspend fun getPlaces(): List<Place>

    /**
     * Retrieves a single place from the database using Flow.
     *
     * @param id The ID of the place to be retrieved.
     * @return A Flow emitting a single place.
     */
    @Query("SELECT * FROM places WHERE id = :id")
    fun getPlace(id: Int): Flow<Place>

    /**
     * Retrieves a list of places from the database that match the given query using Flow.
     *
     * @param query The query to be used for searching places.
     * @return A Flow emitting a list of places.
     */
    @Query("SELECT * FROM places WHERE title LIKE '%' || :query || '%'")
    fun searchPlaces(query: String): Flow<List<Place>>


    @Query("UPDATE places SET imageUri = :imageUri WHERE id = :id")
    suspend fun updatePlaceImageUri(imageUri: String, id: Int)

}
