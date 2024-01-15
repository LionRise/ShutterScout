package com.cc221042.shutterscout.data
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Define an interface for the Meteosource API endpoints
interface MeteosourceService {

    // Example endpoint to get weather data, adjust the endpoint and parameters as needed
    @GET("get_weather")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("token") apiToken: String
    ): WeatherResponse // Replace with your actual data model class

    companion object {
        // Base URL of the Meteosource API
        private const val BASE_URL = "https://www.meteosource.com/api"

        // Function to create an instance of the service
        fun create(): MeteosourceService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MeteosourceService::class.java)
        }
    }
}