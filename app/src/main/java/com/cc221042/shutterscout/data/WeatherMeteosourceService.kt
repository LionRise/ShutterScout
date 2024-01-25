package com.cc221042.shutterscout.data
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Define an interface for the Meteosource API endpoints
interface WeatherMeteosourceService {


    @GET("v1/free/point")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("sections") sections: String,
        @Query("key") apiToken: String
    ): WeatherResponse

    companion object {
        // Base URL of the Meteosource API
        private const val BASE_URL = "https://www.meteosource.com/api/"

        // Function to create an instance of the service
        fun create(): WeatherMeteosourceService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherMeteosourceService::class.java)
        }
    }
}