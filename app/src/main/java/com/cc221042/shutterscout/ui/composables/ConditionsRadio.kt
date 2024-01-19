package com.cc221042.shutterscout.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.draw.clip
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
fun ConditionsRadio(name: String, icon_name: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(60.dp)
            .height(60.dp)
            .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) Color(0xFF127FBF) else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = icon_name,
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                    fontFamily = FontFamily(Font(R.font.font_awesome)),
                    fontWeight = FontWeight(900),
                    color = if (isSelected) Color(0xFF127FBF) else Color(0xFF9E9E9E),
                    textAlign = TextAlign.End
                )
            )
            Text(
                text = name,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    color = if (isSelected) Color(0xFF127FBF) else Color(0xFF9E9E9E),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(top = 0.dp)
            )
        }
    }
}