package com.cc221042.shutterscout.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HomeSuggestionCard(name: String, onClick: () -> Unit){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally // Align children of Column to the center horizontally
    ) {
        Box(
            modifier = Modifier
                .width(110.dp)
                .height(159.dp)
                .background(
                    Color.LightGray,
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            // Box content goes here
        }
        Text(
            text = name,
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 12.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF515151),
            ),
            modifier = Modifier.padding(top = 8.dp) // Add padding at the top of the text for spacing
        )
    }
}