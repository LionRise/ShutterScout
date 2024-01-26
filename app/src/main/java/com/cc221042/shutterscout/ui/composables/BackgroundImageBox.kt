package com.cc221042.shutterscout.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
fun HomeBackgroundImageBox(
    @DrawableRes backgroundImageRes: Int = R.drawable.home_background, // Default image if not provided
    title: String = "ShutterScout"
) {
    val backgroundImage: Painter = painterResource(id = backgroundImageRes)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Image(
            painter = backgroundImage,
            contentDescription = null, // Decorative image does not require a content description
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = title,
            style = TextStyle(
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.migra_extrabold)),
                fontWeight = FontWeight.ExtraBold,
                color =  Color(0xFFF7F7F7),
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .padding(horizontal = 16.dp)
        )
    }
}
