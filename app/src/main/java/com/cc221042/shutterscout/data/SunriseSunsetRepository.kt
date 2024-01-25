package com.cc221042.shutterscout.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SunriseSunsetRepository(private val service: SunriseSunsetService) {

    suspend fun getSunriseSunsetTimes(latitude: Double, longitude: Double, date: String = "today"): Result<SunriseSunsetResponse> = withContext(
        Dispatchers.IO) {
        try {
            val response = service.getSunriseSunsetTimes(latitude, longitude, date)
            if (response.status == "OK") {
                Result.success(response)
            } else {
                Result.failure(RuntimeException("API call failed with status: ${response.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}