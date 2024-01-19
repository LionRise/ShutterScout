package com.cc221042.shutterscout.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConditionsRow(conditions: List<String>, icons: List<String>, selectedCondition: String, onConditionSelected: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        conditions.zip(icons).forEach { (condition, icon) ->
            ConditionsRadio(
                name = condition,
                icon_name = icon,
                isSelected = selectedCondition == condition,
                onClick = { onConditionSelected(condition) }
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}