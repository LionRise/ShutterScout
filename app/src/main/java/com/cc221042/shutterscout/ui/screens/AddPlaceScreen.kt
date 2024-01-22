package com.cc221042.shutterscout.ui.screens

import android.net.Uri
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc221042.shutterscout.Place
import com.cc221042.shutterscout.R
import com.cc221042.shutterscout.data.getLatLongFromImageUri
import com.cc221042.shutterscout.ui.Corner
import com.cc221042.shutterscout.ui.MainViewModel
import com.cc221042.shutterscout.ui.composables.setupPhotoPicker
import com.cc221042.shutterscout.ui.gradientBackground
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.TextUnit
import com.cc221042.shutterscout.ui.composables.ConditionsRadio
import com.cc221042.shutterscout.ui.composables.ConditionsRow
import com.cc221042.shutterscout.ui.composables.IconRadio

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaceScreen(mainViewModel: MainViewModel) {
    var title by rememberSaveable { mutableStateOf("")}
    var condition by rememberSaveable { mutableStateOf("") }
    var imageUri by rememberSaveable { mutableStateOf("") }
    var saveSuccess by remember { mutableStateOf(false) }

    // location extraction from image EXIF data
    var latitudeText by rememberSaveable { mutableStateOf("") } // For user input
    var longitudeText by rememberSaveable { mutableStateOf("") } // For user input

    val context = LocalContext.current

    // map icon
    var icon by rememberSaveable { mutableStateOf("") }
    icon = "map-marker-alt"

    // conditions
    var conditions by rememberSaveable { mutableStateOf("") }

    val photoPicker = setupPhotoPicker { uri: Uri ->
        imageUri = uri.toString()
        val latLong = getLatLongFromImageUri(context, uri)
        //latitude = latLong.first
        //longitude = latLong.second
    }

    val gradientColors = listOf(Color(0xFFDE911D), Color(0xFFF0B429))

    val diagonalGradientBrush = Brush.linearGradient(
        colors = gradientColors,
        start = Offset(0f, 0f), // Top left corner
        end = Offset(100f, 100f) // Adjust for the desired diagonal effect
    )

    Scaffold(
        containerColor = Color(0xFFF7F7F7),
    ) {innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),

//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .padding(top = 38.dp)
                    .padding(horizontal = 12.dp)
                    .gradientBackground(
                        colors = listOf(Color(0xFFDE911D), Color(0xFFF0B429)),
                        cornerRadius = 10.dp,
                        startCorner = Corner.TOP_LEFT,
                        endCorner = Corner.BOTTOM_RIGHT
                    )

            ) {
                Button(
                    onClick = { photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                    shape = RoundedCornerShape(10.dp),
                    // TODO add gradient
                    colors = ButtonDefaults.buttonColors(Color(0xFFF0B429)),
                    modifier = Modifier
//                        .padding(top = 38.dp)
//                        .padding(horizontal = 12.dp)
                        .fillMaxWidth()
                        .height(138.dp)


                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        Text(
                            text = "plus-circle",
                            style = TextStyle(
                                fontSize = 22.sp,
                                lineHeight = 28.sp,
                                fontFamily = FontFamily(Font(R.font.font_awesome)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                            )
                        )
                        Text(
                            text = "add picture",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                            ), modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(50.dp))

// Go back box
        Button(
            onClick = {
                val latitude = latitudeText.toDoubleOrNull()
                val longitude = longitudeText.toDoubleOrNull()
                mainViewModel.save(Place(title, condition, imageUri, latitude, longitude))
                saveSuccess = true // Update the state to reflect save success
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFCB6E17)),
            modifier = Modifier
                .padding(top = 12.dp)
                .width(130.dp)
                .height(40.dp)
                .height(138.dp),
//                    enabled = title.isNotBlank() && condition.isNotBlank()
        ) {
            Text(
                text = "Save",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFF7F7F7),
                )
            )
        }

        if (saveSuccess) {
            Text("Place saved successfully!", color = Color.Green)
        }

        Column(
            modifier = Modifier
                .padding(top = 244.dp, start = 12.dp)

        ) {
            Text(
                text = "Name",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontFamily = FontFamily(Font(R.font.lexend)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF565656),
                ), modifier = Modifier
//                    .padding(top = 244.dp, start = 12.dp)
            )


            TextField(
                value = title,

                onValueChange = { newText -> title = newText },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,


                    ),
                textStyle = TextStyle(

                    color = Color.Black,
                    // Adjust lineHeight if needed
                    lineHeight = TextUnit.Unspecified
                ),
                modifier = Modifier
                    .padding(top = 8.dp) // Decreased padding
            )
            //input for latitude
            TextField(
                value = latitudeText,
                onValueChange = { newText -> latitudeText = newText },
                label = { Text("Latitude") }
            )

            TextField(
                value = longitudeText,
                onValueChange = { newText -> longitudeText = newText },
                label = { Text("Longitude") }
            )

            Text(
                text = "Icon on map",

                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontFamily = FontFamily(Font(R.font.lexend)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF565656),
                ), modifier = Modifier
                    .padding(top = 20.dp, bottom = 12.dp)
            )

            // Define your icons
            val icons = listOf("map-marker-alt", "star", "heart", "mountain", "building", "university", "water", "tree")

            // Split icons into two rows
            val firstRowIcons = icons.take(4)
            val secondRowIcons = icons.drop(4)

            // First row of icons
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                firstRowIcons.forEach { iconName ->
                    IconRadio(
                        name = iconName,
                        isSelected = icon == iconName,
                        onClick = { icon = iconName }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp)) // Space between rows

            // Second row of icons
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                secondRowIcons.forEach { iconName ->
                    IconRadio(
                        name = iconName,
                        isSelected = icon == iconName,
                        onClick = { icon = iconName }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }

            Text(
                text = "Icon on map",

                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontFamily = FontFamily(Font(R.font.lexend)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF565656),
                ), modifier = Modifier
                    .padding(top = 20.dp, bottom = 12.dp)
            )

            val possible_conditions = listOf("sunrise", "sunset","midday", "clouds", "rain", "snow", "thunder", "fog")
            val possible_conditions_icons = listOf("sunrise", "sunset", "sun", "cloud", "cloud-rain", "snowflake", "thunderstorm", "fog")



//                TextField(
//                    value = condition,
//                    onValueChange = { newText -> condition = newText },
//                    label = { Text("Condition of your place") }
//                )
            // Split icons into two rows
            val firstRowConditionsIcons = possible_conditions_icons.take(4)
            val firstRowConditionsNames = possible_conditions.take(4)

            val secondRowConditionsIcons = possible_conditions_icons.drop(4)
            val secondRowConditionsNames = possible_conditions.drop(4)

            ConditionsRow(
                conditions = firstRowConditionsNames,
                icons = firstRowConditionsIcons,
                selectedCondition = condition,
                onConditionSelected = { newCondition -> condition = newCondition }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ConditionsRow(
                conditions = secondRowConditionsNames,
                icons = secondRowConditionsIcons,
                selectedCondition = condition,
                onConditionSelected = { newCondition -> condition = newCondition }
            )


            Spacer(modifier = Modifier.height(20.dp))

            // TODO add gradient

        }
    }
}
