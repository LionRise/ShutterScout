package com.cc221042.shutterscout.ui.composables

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.navigation.NavHostController
import com.cc221042.shutterscout.R
// for the gradient
import com.cc221042.shutterscout.ui.Corner
import com.cc221042.shutterscout.ui.Screen
import com.cc221042.shutterscout.ui.gradientBackground


@Composable
fun AddPlaceButton(navController: NavHostController, onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        modifier = Modifier,
        containerColor = Color(0xFF1992D4),

        onClick = {
            onClick() // Existing logic
            navController.navigate(Screen.AddPlace.route) // Navigate to AddPlaceScreen
        },
        icon = { Text(
            text = "map-marker-plus",
            style = TextStyle(
                fontSize = 28.sp,
                fontFamily = FontFamily(Font(R.font.font_awesome)),
                fontWeight = FontWeight(400),
                color = Color(0xFFFFFFFF),
            )
        ) },
        text = {
            Text(
                text = "Add Place",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily(Font(R.font.lexend)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),

                    )
            )

        },
    )
}