package com.cc221042.shutterscout.ui.composables

import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cc221042.shutterscout.Place
import com.cc221042.shutterscout.ui.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPlaceModal(mainViewModel: MainViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()

    if (state.value.openDialog) {
        val place = mainViewModel.placeState.collectAsState().value
        var title by rememberSaveable { mutableStateOf(place.title) }
        var condition by rememberSaveable { mutableStateOf(place.condition) }
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

        AlertDialog(
            onDismissRequest = {
                mainViewModel.closeDialog()
            },
            text = {
                Column {
                    TextField(
                        modifier = Modifier.padding(top = 20.dp),
                        value = title,
                        onValueChange = { newText -> title = newText },
                        label = { Text(text = "Title" ) }
                    )
                    Button(
                        onClick = { //On button press, launch the photo picker
                            photoPicker.launch(
                                PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                            ) },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Open Gallery")
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    mainViewModel.updatePlace(Place(title, condition, imageUri, place.latitude, place.longitude, place.id))
                    Log.d("duck", "confirm button clicked")
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = {
                    mainViewModel.closeDialog()
                }) {
                    Text("Dismiss")
                }
            }
        )
    }
}
