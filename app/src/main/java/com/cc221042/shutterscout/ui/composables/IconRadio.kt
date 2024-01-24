package com.cc221042.shutterscout.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc221042.shutterscout.R

@Composable
fun IconRadio(name: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(60.dp)
            .height(60.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(10.dp),
                clip = true
            )
            .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) Color(0xFF127FBF)else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = name,
            style = TextStyle(
                fontSize = 22.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.font_awesomesolid)),
                fontWeight = FontWeight(900),
                color = if (isSelected) Color(0xFF127FBF) else Color(0xFF9E9E9E),
                textAlign = TextAlign.Center,
            )
        )
    }
}
