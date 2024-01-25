package com.cc221042.shutterscout.ui.composables

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cc221042.shutterscout.Place
import com.cc221042.shutterscout.R
import com.cc221042.shutterscout.ui.Corner
import com.cc221042.shutterscout.ui.MainViewModel
import com.cc221042.shutterscout.ui.gradientBackground

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPlaceModal(mainViewModel: MainViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()

    if (state.value.openDialog) {
        val place = mainViewModel.placeState.collectAsState().value
        var title by rememberSaveable { mutableStateOf(place.title) }
        var condition by rememberSaveable { mutableStateOf(place.condition) }
        var icon by rememberSaveable { mutableStateOf("") }
        var imageUri by rememberSaveable { mutableStateOf(place.imageUri ?: "") }
        val context = LocalContext.current
        val photoPicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Log.d("duck PhotoPicker", "Selected URI: $uri")
                imageUri = uri.toString()
                context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } else {
                Log.d(" duck PhotoPicker", "No media selected")
            }
        }
        val gradientColors = listOf(Color(0xFFDE911D), Color(0xFFF0B429))

        AlertDialog(
            onDismissRequest = {
                mainViewModel.closeDialog()
            },
            {
                Scaffold(
                    containerColor = Color(0xFFF7F7F7),
                ) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            onClick = {
                                mainViewModel.closeDialog()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent, // Set the button background to transparent
                                contentColor = Color(0xFF000000)
                            ),
                            modifier = Modifier
                                .padding(top = 25.dp)
                        ) {
                            Text(
                                text = "chevron-left",
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    lineHeight = 28.sp,
                                    fontFamily = FontFamily(Font(R.font.font_awesome)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF000000),
                                    textAlign = TextAlign.End,
                                )
                            )
                        }

                        Text(
                            text = "Edit Place",
                            style = TextStyle(
                                fontSize = 22.sp,
                                lineHeight = 28.sp,
                                fontFamily = FontFamily(Font(R.font.lexend)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF565656),
                                textAlign = TextAlign.Center,
                            ), modifier = Modifier
                                .padding(top = 20.dp)
                        )

                        IconButton(
                            onClick = {
                                mainViewModel.deleteButton(place)
                                mainViewModel.closeDialog()
                            },
                            modifier = Modifier
                                .padding(top = 20.dp)
                        )
                        {
                            Icon(Icons.Default.Delete,"Delete", tint = Color(0xFF565656))
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 50.dp)
                            .verticalScroll(rememberScrollState()),

                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

// IconButton for delete action
                        Box(
                            modifier = Modifier
                                .padding(top = 38.dp)
                                .padding(horizontal = 12.dp)
                                .fillMaxWidth()
                                .height(138.dp)

                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp)), // Apply a clip with rounded corners
                            model = ImageRequest.Builder(LocalContext.current)
                                    .data(place.imageUri)
                                    .crossfade(enable = true)
                                    .build(),
                                contentDescription = place.title,
                                contentScale = ContentScale.Crop,
                            )

                            Button(
                                onClick = { photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent, // Set the button background to transparent
                                    contentColor = Color.White // Set the content color to white (adjust if needed)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(138.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,

                                    ) {
                                    Text(
                                        text = "pen",
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
                                        text = "switch picture",
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

                    Column(
                        modifier = Modifier
                            .padding(start = 12.dp, top = 244.dp)

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
                            text = "Wanted Weather Condition",

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

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    mainViewModel.updatePlace(Place(title, condition, icon, imageUri, place.longitude, place.latitude, place.id))
                                    mainViewModel.closeDialog()
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(Color(0xFFCB6E17)),
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .width(130.dp)
                                    .height(40.dp)
                                    .height(138.dp),
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

                            Spacer(modifier = Modifier.width(16.dp))

                            Button(
                                onClick = {
                                    mainViewModel.closeDialog()
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(Color(0xFFCB6E17)),
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .width(130.dp)
                                    .height(40.dp)
                                    .height(138.dp),
                            ) {
                                Text(
                                    text = "Dismiss",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 16.sp,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFF7F7F7),
                                        )
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}
