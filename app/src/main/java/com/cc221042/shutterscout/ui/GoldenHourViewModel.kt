package com.cc221042.shutterscout.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dev.zotov.phototime.solarized.Solarized
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import java.time.Duration
import java.time.ZoneId
import java.time.LocalDateTime

class GoldenHourViewModel : ViewModel() {

    private val _goldenHourTimes = MutableStateFlow<GoldenHourTimes?>(null)
    val goldenHourTimes: StateFlow<GoldenHourTimes?> = _goldenHourTimes

    private val _timeToNextGoldenHour = MutableStateFlow<String>("")
    val timeToNextGoldenHour: StateFlow<String> = _timeToNextGoldenHour.asStateFlow()

    val latitude = 48.208176
    val longitude = 16.373819
    val zoneId = ZoneId.systemDefault()

    init {
        viewModelScope.launch {
            while (isActive) {
                loadGoldenHourTimes()
                val currentTime = LocalDateTime.now(zoneId)
                val nextGoldenHour = getNextGoldenHourTime(currentTime)

                // Calculate time remaining until the next golden hour
                val durationUntilNextGoldenHour = Duration.between(currentTime, nextGoldenHour)
                var hours = durationUntilNextGoldenHour.toHours()
                // TODO understand whyv - maybe it results the sunset now the beggining of golden hour
                hours -= 1
                val minutes = (durationUntilNextGoldenHour.toMinutes() % 60)
                val seconds = (durationUntilNextGoldenHour.seconds % 60)

                _timeToNextGoldenHour.value = String.format("%02d:%02d:%02d", hours, minutes, seconds)

                delay(1000) // Update every second
            }
        }
    }

    private fun loadGoldenHourTimes() {
        val date = LocalDateTime.now(zoneId) // Or pass a date if you want to calculate for a specific day
        val solarized = Solarized(latitude, longitude, date)

        // Use the solarized library to get the times for the golden hours
        val morningGoldenHour = solarized.goldenHour.morning
        val eveningGoldenHour = solarized.goldenHour.evening

        _goldenHourTimes.value = GoldenHourTimes(
            morningStart = morningGoldenHour?.start?.atZone(ZoneId.of("UTC"))?.withZoneSameInstant(zoneId)?.toLocalDateTime(),
            morningEnd = morningGoldenHour?.end?.atZone(ZoneId.of("UTC"))?.withZoneSameInstant(zoneId)?.toLocalDateTime(),
            eveningStart = eveningGoldenHour?.start?.atZone(ZoneId.of("UTC"))?.withZoneSameInstant(zoneId)?.toLocalDateTime(),
            eveningEnd = eveningGoldenHour?.end?.atZone(ZoneId.of("UTC"))?.withZoneSameInstant(zoneId)?.toLocalDateTime()
        )
    }

    private fun getNextGoldenHourTime(currentTime: LocalDateTime): LocalDateTime {
        val goldenHourTimes = _goldenHourTimes.value ?: return currentTime
        return listOfNotNull(
            goldenHourTimes.morningStart,
            goldenHourTimes.morningEnd,
            goldenHourTimes.eveningStart,
            goldenHourTimes.eveningEnd
        ).firstOrNull { it.isAfter(currentTime) } ?: currentTime.plusDays(1) // Fallback to current time plus one day
    }

    private fun formatDuration(duration: Duration): String {
        val hours = duration.toHours().toString().padStart(2, '0')
        val minutes = (duration.toMinutes() % 60).toString().padStart(2, '0')
        val seconds = (duration.seconds % 60).toString().padStart(2, '0')
        return "$hours:$minutes:$seconds"
    }

    data class GoldenHourTimes(
        val morningStart: LocalDateTime?,
        val morningEnd: LocalDateTime?,
        val eveningStart: LocalDateTime?,
        val eveningEnd: LocalDateTime?
    )
}