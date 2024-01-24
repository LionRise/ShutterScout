package com.cc221042.shutterscout.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc221042.shutterscout.R
import com.cc221042.shutterscout.data.WeatherResponse

@Composable
fun WeatherCurrentBox(weatherData: WeatherResponse?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
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
            modifier = Modifier.padding(start = 12.dp, top = 12.dp)
        )

        weatherData?.let { data ->
            // Display temperature
            Text(
                text = "${data.current.temperature}°C",
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF222222),
                ),
                modifier = Modifier.padding(start = 12.dp, top = 38.dp)
            )

            // Display weather summary
            Text(
                text = data.current.summary,
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF515151),
                ),
                modifier = Modifier.padding(start = 12.dp, top = 72.dp)
            )

            // Display cloud cover
            Text(
                text = "${data.current.cloud_cover}% clouds",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF7E7E7E),
                ),
                modifier = Modifier.padding(start = 12.dp, top = 103.dp)
            )
        } ?: Text(
            text = "-",
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF222222),
            ),
            modifier = Modifier.padding(start = 12.dp, top = 38.dp)
        )
    }
}