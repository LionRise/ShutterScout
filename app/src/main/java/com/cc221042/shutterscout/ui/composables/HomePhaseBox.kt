package com.cc221042.shutterscout.ui.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc221042.shutterscout.data.SunriseSunsetResponse


@Composable
fun HomePhaseBox(
    name: String,
    startTime: String,
    endTime: String,
    startColor: Color,
    endColor: Color
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp)
        .shadow(
            elevation = 3.dp,
            shape = RoundedCornerShape(10.dp),
            clip = true
        )

        .background(Color.White)
//        .height(44.dp)

    ) {
        Row(modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(vertical = 16.dp)

        ) {
            Canvas(modifier = Modifier.size(20.dp).align(Alignment.CenterVertically)) {
                val gradient = Brush.linearGradient(
                    colors = listOf(startColor, endColor),
                    start = Offset.Zero,
                    end = Offset(size.width, size.height)
                )
                drawCircle(brush = gradient, radius = size.minDimension / 2)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF515151),

                    ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = startTime,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF6C6C6C),

                    ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "-",
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF828282),
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = endTime,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF6C6C6C),
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )

        }

    }

}