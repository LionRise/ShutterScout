package com.cc221042.shutterscout.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.cc221042.shutterscout.ui.composables.HomeGoldenHourBox
import com.cc221042.shutterscout.ui.composables.WeatherCurrentBox
import com.cc221042.shutterscout.ui.composables.WeatherSuggestionBox


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

        val lexend = FontFamily(Font(R.font.lexend))

        // background color box
        Box(
            modifier = Modifier
                .fillMaxSize() // Fill the entire screen
                .background(Color(0xFFFFFFFF))
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


            // background image box
            Box(modifier = Modifier
                .shadow(
                    elevation = 10.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )

                .fillMaxWidth()
                .height(120.dp)
                .paint(
                    painter = painterResource(R.drawable.home_background),
                    contentScale = ContentScale.FillWidth
                )
            ) {
                Text(
                    text = "ShutterScout",
                    style = TextStyle(
                        fontSize = 32.sp,
                        lineHeight = 28.sp,
                        fontFamily = FontFamily(Font(R.font.migra_extrabold)),
                        fontWeight = FontWeight(800),
                        color = Color(0xFFF7F7F7),

                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .padding(start = 12.dp, top = 32.dp)
                )
            }

            Column(
                modifier = Modifier
                    .background(Color.Transparent)
            ) {

                HomeGoldenHourBox(countdownValue)
                Spacer(modifier = Modifier.height(20.dp))
                WeatherCurrentBox(weatherData = weatherData)
                WeatherSuggestionBox(currentConditions = currentConditions , matchingPlaces = matchingPlaces)

            



        }
    }
}




