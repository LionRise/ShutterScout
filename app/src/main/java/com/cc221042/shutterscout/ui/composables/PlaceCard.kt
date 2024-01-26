package com.cc221042.shutterscout.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cc221042.shutterscout.Place
import com.cc221042.shutterscout.ui.MainViewModel
import coil.compose.rememberAsyncImagePainter
import com.cc221042.shutterscout.R

@Composable
fun PlaceCard(place: Place, mainViewModel: MainViewModel) {

// Map each condition to its corresponding icon
    val conditionsToIcons = mapOf(
        "sunrise" to "sunrise", // Replace with actual drawable resource ids
        "sunset" to "sunset",
        "midday" to "sun",
        "clouds" to "cloud",
        "rain" to "cloud_rain",
        "snow" to "snowflake",
        "thunder" to "thunderstorm",
        "fog" to "fog"
    )

    // Find the icon for the current place's condition
    val conditionIconName = conditionsToIcons[place.condition] ?: "sun"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(200.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White), // Set the background color here
        shape = RoundedCornerShape(8.dp),
    ) {
        Row {
            val imagePainter = rememberAsyncImagePainter(model = place.imageUri)
            Image(
                painter = imagePainter,
                contentDescription = place.title,
                modifier = Modifier
                    .width(150.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp).fillMaxHeight().fillMaxWidth()) {
                Text(
                    text = place.title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = Color(0xFF565656))
                )
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Icon on map",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF565656),
                            fontFamily = FontFamily(Font(R.font.lexend))
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = place.icon,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF565656),
                            fontFamily = FontFamily(Font(R.font.font_awesomesolid))),
                        modifier = Modifier
                            .padding(start = 6.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Row {
                    Text(
                        text = place.condition,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF565656),
                            fontFamily = FontFamily(Font(R.font.lexend))
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = conditionIconName,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF565656),
                            fontFamily = FontFamily(Font(R.font.font_awesomesolid)))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 60.dp)
                ) {
                    IconButton(
                        onClick = { mainViewModel.editPlace(place) }
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFF565656)
                        )
                    }
                    IconButton(
                        onClick = { mainViewModel.deleteButton(place) }
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFF565656)
                        )
                    }
                }
            }
        }
    }
}
