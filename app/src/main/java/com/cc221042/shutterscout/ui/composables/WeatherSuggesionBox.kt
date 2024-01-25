package com.cc221042.shutterscout.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc221042.shutterscout.Place
import com.cc221042.shutterscout.R

@Composable
fun WeatherSuggestionBox(
    currentConditions: List<String>?,
    matchingPlaces: List<Place>
) {
    Text(
        text = "Suggestions",
        style = TextStyle(
            fontSize = 14.sp,
            lineHeight = 14.sp,
            fontFamily = FontFamily(Font(R.font.lexend)),
            fontWeight = FontWeight(400),
            color = Color(0xFF515151),
        ),
        modifier = Modifier
            .padding(top = 20.dp)
            .padding(start = 12.dp)
    )

//    Text(
//        text = "Current Conditions: ${currentConditions?.joinToString(", ") ?: "N/A"}",
//        style = TextStyle(
//            fontSize = 16.sp,
//            lineHeight = 16.sp,
//            fontWeight = FontWeight(400),
//            color = Color(0xFF222222),
//        ),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(start = 12.dp, top = 20.dp)
//    )

    LazyRow(
        modifier = Modifier
            .padding(top = 12.dp, start = 12.dp)
            .fillMaxWidth()
    ) {
        itemsIndexed(matchingPlaces) { index, place ->
            HomeSuggestionCard(place.title, place.imageUri) {
                // Handle click action if needed
            }
            if (index < matchingPlaces.size - 1) {
                Spacer(Modifier.width(12.dp)) // Add spacing between items
            }
        }
    }
}