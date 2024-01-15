package com.cc221042.shutterscout.data


class WeatherRepository(private val meteosourceService: MeteosourceService) {
    suspend fun getWeather(latitude: Double, longitude: Double, apiToken: String): WeatherResponse {
        return meteosourceService.getWeather(latitude, longitude, apiToken)

    }
}