package com.cc221042.shutterscout.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface SunriseSunsetService {

    // Endpoint to get sunrise and sunset times
    @GET("json")
    suspend fun getSunriseSunsetTimes(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("date") date: String = "today",
        @Query("formatted") formatted: Int = 0
    ): SunriseSunsetResponse // Replace with your actual data model class

    companion object {
        // Base URL of the Sunrise-Sunset API
        private const val BASE_URL = "https://api.sunrise-sunset.org/"

        // Function to create an instance of the service
        fun create(): SunriseSunsetService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SunriseSunsetService::class.java)
        }
    }
}