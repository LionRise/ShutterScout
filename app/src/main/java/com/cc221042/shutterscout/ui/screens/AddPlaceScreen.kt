package com.cc221042.shutterscout.ui.screens

import android.net.Uri
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc221042.shutterscout.Place
import com.cc221042.shutterscout.ui.MainViewModel
import com.cc221042.shutterscout.ui.composables.setupPhotoPicker

@Composable
fun HomeScreen(mainViewModel: MainViewModel) {
    var title by rememberSaveable { mutableStateOf("") }
    var condition by rememberSaveable { mutableStateOf("") }
    var imageUri by rememberSaveable { mutableStateOf("") }
    var saveSuccess by remember { mutableStateOf(false) }

    val photoPicker = setupPhotoPicker { uri: Uri ->
        imageUri = uri.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "ShutterScout", fontSize = 50.sp, style = TextStyle(fontFamily = FontFamily.Cursive))

        Spacer(modifier = Modifier.height(50.dp))

        TextField(
            value = title,
            onValueChange = { newText -> title = newText },
            label = { Text("Title of your place") }
        )
        TextField(
            value = condition,
            onValueChange = { newText -> condition = newText },
            label = { Text("Condition of your place") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Text("Open Gallery", fontSize = 20.sp)
        }

        Button(
            onClick = {
                mainViewModel.save(Place(title, condition, imageUri))
                saveSuccess = true // Update the state to reflect save success
            },
            modifier = Modifier.padding(top = 15.dp),
            enabled = title.isNotBlank() && condition.isNotBlank()
        ) {
            Text("Save", fontSize = 20.sp)
        }

        if (saveSuccess) {
            Text("Place saved successfully!", color = Color.Green)
        }
    }
}