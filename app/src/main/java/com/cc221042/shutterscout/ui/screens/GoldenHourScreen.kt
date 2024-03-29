package com.cc221042.shutterscout.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cc221042.shutterscout.ui.GoldenHourViewModel
import java.time.format.DateTimeFormatter


//@Composable
//fun GoldenHourScreen(viewModel: GoldenHourViewModel = viewModel()) {
//    val goldenHourViewModel = remember { viewModel }
//    // Here, we assume the viewModel is passed down or retrieved via viewModel() delegation
//    val context = LocalContext.current
//    val goldenHourTimes by viewModel.goldenHourTimes.collectAsState()
//    val countdown by viewModel.countdownValue.collectAsState()
//
//    // Use MaterialTheme.typography for better text styles if using Material Design
//    val typography = MaterialTheme.typography
//    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text("Sun Phase Times", style = typography.headlineMedium)
//
//        goldenHourTimes?.let { times ->
//            Text("First Light at: ${times.firstLightTime?.format(timeFormatter)}", style = typography.bodyMedium)
//            Text("Sunrise at: ${times.sunriseTime?.format(timeFormatter)}", style = typography.bodyMedium)
//            Text("Morning Golden Hour starts at: ${times.morningStart?.format(timeFormatter)}", style = typography.bodyMedium)
//            Text("Morning Golden Hour ends at: ${times.morningEnd?.format(timeFormatter)}", style = typography.bodyMedium)
//            Text("Evening Golden Hour starts at: ${times.eveningStart?.format(timeFormatter)}", style = typography.bodyMedium)
//            Text("Evening Golden Hour ends at: ${times.eveningEnd?.format(timeFormatter)}", style = typography.bodyMedium)
//            Text("Sunset at: ${times.sunsetTime?.format(timeFormatter)}", style = typography.bodyMedium)
//            Text("Last Light at: ${times.lastLightTime?.format(timeFormatter)}", style = typography.bodyMedium)
//            Text("Countdown to next Golden Hour: $countdown", style = typography.bodyMedium)
//        } ?: Text("Loading sun phase times...", style = typography.bodyMedium)
//    }
//}


@Preview(showBackground = true)
@Composable
fun GoldenHourScreenPreview() {
    // Note: Previews may not be able to display data that is loaded asynchronously
//    GoldenHourScreen()
    Text(text = "TEST")
}