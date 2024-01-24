package com.cc221042.shutterscout.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc221042.shutterscout.R

@Composable
fun HomeBackgroundImageBox(){
    // background image box
    Box(modifier = Modifier
//        .shadow(
//            elevation = 10.dp,
//            spotColor = Color(0x40000000),
//            ambientColor = Color(0x40000000)
//        )

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
}