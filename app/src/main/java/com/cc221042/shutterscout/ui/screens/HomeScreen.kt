package com.cc221042.shutterscout.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cc221042.shutterscout.R
import com.cc221042.shutterscout.ui.GoldenHourViewModel
import com.cc221042.shutterscout.ui.MainViewModel
import com.cc221042.shutterscout.ui.WeatherViewModel
import com.cc221042.shutterscout.ui.composables.AddPlaceButton
import com.cc221042.shutterscout.ui.composables.HomeGoldenHourBox
import com.cc221042.shutterscout.ui.composables.HomeSuggestionCard
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt


@Composable
@Preview
fun HomeScreen(
    mainViewModel: MainViewModel = viewModel(),
    weatherViewModel: WeatherViewModel = viewModel(),
    goldenHourViewModel: GoldenHourViewModel = viewModel()
) {
    val weatherData by weatherViewModel.weatherData.collectAsState()
    val countdownValue by goldenHourViewModel.countdownValue.collectAsState()


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
//        Image(
//            painter = imagePainter,
//            contentDescription = "Local image",
//            modifier = imageModifier
//        )

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

                // Current weather box
                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(top = 20.dp)

                        .height(134.dp)
                        .background(
                            color = Color(0xFFF6F6F6),
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                ) {
                    Text(
                        text = "Current weather",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 14.sp,
                            fontFamily = FontFamily(Font(R.font.lexend)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF515151),
                        ),
                        modifier = Modifier
                            .padding(start = 12.dp, top = 12.dp)
                    )

                    val weatherData by weatherViewModel.weatherData.collectAsState()
                    weatherData?.let { data ->

                        val weatherDescription = data.current.temperature

                        Text(
                            text = "$weatherDescription°C",
                            style = TextStyle(
                                fontSize = 24.sp,
                                lineHeight = 24.sp,
//                        fontFamily = FontFamily(Font(R.font.roboto)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF222222),
                            ),
                            modifier = Modifier
                                .padding(start = 12.dp, top = 38.dp)
                        )
                    } ?: Text(
                        text = "-",
                        style = TextStyle(
                            fontSize = 24.sp,
                            lineHeight = 24.sp,
//                        fontFamily = FontFamily(Font(R.font.roboto)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF222222),
                        ),
                        modifier = Modifier
                            .padding(start = 12.dp, top = 38.dp)
                    )
                    // Summary
                    weatherData?.let { data ->

                        val weatherDescription = data.current.summary

                        Text(
                            text = "$weatherDescription",
                            style = TextStyle(
                                fontSize = 20.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF515151),
                            ),
                            modifier = Modifier
                                .padding(start = 12.dp, top = 72.dp)
                        )
                    } ?: Text(
                        text = "-",
                        style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF515151),
                        ),
                        modifier = Modifier
                            .padding(start = 12.dp, top = 72.dp)
                    )
                    // Cloud cover
                    weatherData?.let { data ->

                        val weatherDescription = data.current.cloud_cover

                        Text(
                            text = "$weatherDescription% clouds",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 16.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF7E7E7E),
                            ),
                            modifier = Modifier
                                .padding(start = 12.dp, top = 103.dp)
                        )
                    } ?: Text(
                        text = "-",
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF7E7E7E),
                        ),
                        modifier = Modifier
                            .padding(start = 12.dp, top = 103.dp)

                    )

                }

            // Suggestions box
            Text(
                text = "Suggestions",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontFamily = FontFamily(Font(R.font.lexend)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF515151),
                ), modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(start = 12.dp)
            )
            Row(
                modifier = Modifier
                    .padding(top=12.dp)
            ) {
                Box(Modifier.padding(start = 12.dp)) {}
                HomeSuggestionCard("Mountains") {}
                Box(Modifier.padding(start = 12.dp)) {}
                HomeSuggestionCard("Lake") {""}
                Box(Modifier.padding(start = 12.dp)) {}
                HomeSuggestionCard("Lighthouse") {}
            }


            }
            }
        }




