package com.cc221042.shutterscout.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc221042.shutterscout.R

@Composable
fun HomeScreen(
){
    val lexend = FontFamily(Font(R.font.lexend))

    Box (
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

        Column (
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
                    .height(126.dp)
                    .background(color = Color(0xFFF6F6F6), shape = RoundedCornerShape(size = 10.dp))
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
                        .padding(top=12.dp, start = 12.dp)
                )
                Text(text = "")
            }
        }
    }

}
