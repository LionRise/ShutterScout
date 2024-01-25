    package com.cc221042.shutterscout.ui.screens

    import android.annotation.SuppressLint
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.width
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.foundation.verticalScroll
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.paint
    import androidx.compose.ui.draw.shadow
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.layout.ContentScale
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.TextStyle
    import androidx.compose.ui.text.font.Font
    import androidx.compose.ui.text.font.FontFamily
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.lifecycle.viewmodel.compose.viewModel
    import com.cc221042.shutterscout.R
    import com.cc221042.shutterscout.ui.GoldenHourViewModel
    import com.cc221042.shutterscout.ui.MainViewModel
    import com.cc221042.shutterscout.ui.WeatherViewModel
    import com.cc221042.shutterscout.ui.composables.HomeBackgroundImageBox
    import com.cc221042.shutterscout.ui.composables.HomeGoldenHourBox
    import com.cc221042.shutterscout.ui.composables.HomePhaseBox
    import com.cc221042.shutterscout.ui.composables.WeatherCurrentBox
    import com.cc221042.shutterscout.ui.composables.WeatherSuggestionBox


    @SuppressLint("StateFlowValueCalledInComposition")
    @Composable
    @Preview
    fun HomeScreen(
        mainViewModel: MainViewModel = viewModel(),
        weatherViewModel: WeatherViewModel = viewModel(),
        goldenHourViewModel: GoldenHourViewModel = viewModel()
    ) {
    //    val weatherData by weatherViewModel.weatherData.collectAsState()
    //    val countdownValue by goldenHourViewModel.countdownValue.collectAsState()

        // Observe the weather data and place matching conditions from the ViewModel
        val weatherData by weatherViewModel.weatherData.collectAsState()
        val countdownValue by goldenHourViewModel.countdownValue.collectAsState()
        val matchingPlaces by mainViewModel.allPlacesWithConditionsMet.collectAsState()
        val currentConditions by mainViewModel.currentConditions.collectAsState()

        val sunriseSunset by mainViewModel.sunriseSunset.collectAsState()

        val lexend = FontFamily(Font(R.font.lexend))

        val scrollState = rememberScrollState()

            // background color box
            Box(
                modifier = Modifier
                    .fillMaxSize() // Fill the entire screen
                    .background(Color(0xFFFFFFFF))
                    .verticalScroll(state = scrollState)
            ) {
                val imagePainter = painterResource(id = R.drawable.home_background)

                val imageModifier = Modifier
                    .shadow(
                        elevation = 10.dp,
                        spotColor = Color(0x40000000),
                        ambientColor = Color(0x40000000)
                    )
                    .width(361.dp)
                    .height(120.dp)


                HomeBackgroundImageBox()

                Column(
                    modifier = Modifier
                        .background(Color.Transparent)
                ) {

                    HomeGoldenHourBox(countdownValue)
                    Spacer(modifier = Modifier.height(20.dp))
                    WeatherCurrentBox(weatherData = weatherData)
                    WeatherSuggestionBox(matchingPlaces = matchingPlaces)

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Coming up",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 14.sp,
                            fontFamily = FontFamily(Font(R.font.lexend)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF515151),

                        ),
                        modifier = Modifier.padding(start = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    val sunriseSunsetResult by mainViewModel.sunriseSunset.collectAsState()


                    var civilTwilightBegin = "N/A"
                    sunriseSunsetResult?.let { result ->
                        if (result.isSuccess) {
                            civilTwilightBegin = result.getOrNull()?.results?.civil_twilight_begin ?: "-"
                        }
                    }
                    var civilTwilightEnd = "N/A"
                    sunriseSunsetResult?.let { result ->
                        if (result.isSuccess) {
                            civilTwilightEnd = result.getOrNull()?.results?.civil_twilight_end ?: "-"
                        }
                    }


                    var nauticalTwilightBegin = "N/A"
                    var nauticalTwilightEnd = "N/A"

                    sunriseSunsetResult?.let { result ->
                        if (result.isSuccess) {
                            nauticalTwilightBegin = result.getOrNull()?.results?.nautical_twilight_begin ?: "-"
                        }
                    }


                    sunriseSunsetResult?.let { result ->
                        if (result.isSuccess) {
                            nauticalTwilightEnd = result.getOrNull()?.results?.nautical_twilight_end ?: "-"
                        }
                    }


                    var astronomicalTwilightBegin = "N/A"
                    var astronomicalTwilightEnd = "N/A"


                    sunriseSunsetResult?.let { result ->
                        if (result.isSuccess) {
                            astronomicalTwilightBegin = result.getOrNull()?.results?.astronomical_twilight_begin ?: "-"
                        }
                    }

                    HomePhaseBox("Civil twilight",civilTwilightBegin, civilTwilightEnd, Color(0xFFDE911D),Color(0xFFCB6E17))
                    Spacer(modifier = Modifier.height(12.dp))
                    HomePhaseBox("Nautical twilight",nauticalTwilightBegin, nauticalTwilightEnd, Color(0xFF1992D4),Color(0xFF127FBF))
                    Spacer(modifier = Modifier.height(12.dp))
                    HomePhaseBox("Astronomical twilight",astronomicalTwilightBegin, astronomicalTwilightEnd, Color(0xFF0A558C),Color(0xFF003E6B))
                    Spacer(modifier = Modifier.height(32.dp))


            }
        }
    }




