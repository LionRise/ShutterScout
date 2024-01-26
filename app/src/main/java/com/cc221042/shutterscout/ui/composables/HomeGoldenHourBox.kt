package com.cc221042.shutterscout.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc221042.shutterscout.R
// for the gradient
import com.cc221042.shutterscout.ui.Corner
import com.cc221042.shutterscout.ui.customShadowLayered
import com.cc221042.shutterscout.ui.gradientBackground

@Composable
fun HomeGoldenHourBox(countdownTime: String) {
    // Golden hour box
    Box(
        modifier = Modifier

            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(top = 80.dp)
            .height(82.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(10.dp),
                clip = true
            )
            .gradientBackground(
                colors = listOf(Color(0xFFDE911D), Color(0xFFF0B429)),

                cornerRadius = 10.dp,
                startCorner = Corner.TOP_LEFT,
                endCorner = Corner.BOTTOM_RIGHT
            )




    ) {
        Text(
            text = "Golden hour in ",
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 14.sp,
                fontFamily = FontFamily(Font(R.font.lexend)),
                fontWeight = FontWeight(400),
                color = Color(0xFFFFFBEA),

                ),
            modifier = Modifier
                .padding(start = 12.dp, top = 12.dp)
        )
        Text(
            text = countdownTime,
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFFF7F7F7),
            ),
            modifier = Modifier
                .padding(start = 12.dp, top = 32.dp)
        )
    }

}
