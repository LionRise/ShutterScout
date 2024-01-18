package com.cc221042.shutterscout.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc221042.shutterscout.R
import com.cc221042.shutterscout.ui.MainViewModel
import com.cc221042.shutterscout.ui.WeatherViewModel

@Composable
fun HomeScreen(mainViewModel: MainViewModel, weatherViewModel: WeatherViewModel) {
    val weatherData by weatherViewModel.weatherData.collectAsState()


        val lexend = FontFamily(Font(R.font.lexend))

        // Weather info box
        Box(
            modifier = Modifier
                .fillMaxSize() // Fill the entire screen
                .background(Color(0xFF9E9E9E))
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

            Column(
                modifier = Modifier
                    .background(Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 10.dp,
                            spotColor = Color(0x40000000),
                            ambientColor = Color(0x40000000)
                        )
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(top = 178.dp)
                        .height(128.dp)
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
                                .padding(start = 12.dp, top = 68.dp)
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
                            .padding(start = 12.dp, top = 68.dp)
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
                                .padding(start = 12.dp, top = 100.dp)
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
                            .padding(start = 12.dp, top = 100.dp)
                    )

                }
            }
        }


}

