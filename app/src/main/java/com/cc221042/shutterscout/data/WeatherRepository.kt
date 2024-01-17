package com.cc221042.shutterscout.data


class WeatherRepository(private val weatherMeteosourceService: WeatherMeteosourceService) {
    suspend fun getWeather(latitude: Double, longitude: Double, sections: String, apiToken: String): WeatherResponse {
        return weatherMeteosourceService.getWeather(latitude, longitude, sections, apiToken)

    }
}