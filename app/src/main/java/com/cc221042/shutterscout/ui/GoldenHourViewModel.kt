package com.cc221042.shutterscout.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dev.zotov.phototime.solarized.Solarized
import java.time.ZoneId
import java.time.LocalDateTime

class GoldenHourViewModel : ViewModel() {
    private val _goldenHourTimes = MutableStateFlow<GoldenHourTimes?>(null)
    val goldenHourTimes: StateFlow<GoldenHourTimes?> = _goldenHourTimes

    // You would need to replace these with actual location data retrieval logic
    val latitude = 48.208176
    val longitude = 16.373819
    val zoneId = ZoneId.systemDefault() // or the specific zone you're interested in

    init {
        loadGoldenHourTimes()
    }

    private fun loadGoldenHourTimes() {
        viewModelScope.launch {
            val date =
                LocalDateTime.now() // Or pass a date if you want to calculate for a specific day
            val solarized = Solarized(latitude, longitude, date)

            // Access the LocalDateTime from each SunPhase subclass
            val firstLightTime =
                solarized.firstLight?.date?.atZone(ZoneId.of("UTC"))?.withZoneSameInstant(zoneId)
                    ?.toLocalDateTime() ?: return@launch
            val sunriseTime =
                solarized.sunrise?.date?.atZone(ZoneId.of("UTC"))?.withZoneSameInstant(zoneId)
                    ?.toLocalDateTime() ?: return@launch
            val sunsetTime =
                solarized.sunset?.date?.atZone(ZoneId.of("UTC"))?.withZoneSameInstant(zoneId)
                    ?.toLocalDateTime() ?: return@launch
            val lastLightTime =
                solarized.lastLight?.date?.atZone(ZoneId.of("UTC"))?.withZoneSameInstant(zoneId)
                    ?.toLocalDateTime() ?: return@launch

            // Handle GoldenHour and BlueHour which have start and end times
            val morningGoldenHourStart =
                solarized.goldenHour.morning?.start?.atZone(ZoneId.of("UTC"))
                    ?.withZoneSameInstant(zoneId)?.toLocalDateTime() ?: return@launch
            val morningGoldenHourEnd = solarized.goldenHour.morning?.end?.atZone(ZoneId.of("UTC"))
                ?.withZoneSameInstant(zoneId)?.toLocalDateTime() ?: return@launch
            val eveningGoldenHourStart =
                solarized.goldenHour.evening?.start?.atZone(ZoneId.of("UTC"))
                    ?.withZoneSameInstant(zoneId)?.toLocalDateTime() ?: return@launch
            val eveningGoldenHourEnd = solarized.goldenHour.evening?.end?.atZone(ZoneId.of("UTC"))
                ?.withZoneSameInstant(zoneId)?.toLocalDateTime() ?: return@launch

            // Update the MutableStateFlow with the new data
            _goldenHourTimes.value = GoldenHourTimes(
                morningStart = morningGoldenHourStart,
                morningEnd = morningGoldenHourEnd,
                eveningStart = eveningGoldenHourStart,
                eveningEnd = eveningGoldenHourEnd,
                firstLightTime = firstLightTime,
                lastLightTime = lastLightTime,
                sunriseTime = sunriseTime,
                sunsetTime = sunsetTime
            )
        }
    }

    // A data class to hold the golden hour times
    data class GoldenHourTimes(
        val morningStart: LocalDateTime,
        val morningEnd: LocalDateTime,
        val eveningStart: LocalDateTime,
        val eveningEnd: LocalDateTime,
        val firstLightTime: LocalDateTime,
        val lastLightTime: LocalDateTime,
        val sunriseTime: LocalDateTime,
        val sunsetTime: LocalDateTime
    )
}
