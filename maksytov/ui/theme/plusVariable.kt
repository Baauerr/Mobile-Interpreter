package com.example.androidtaskcompose.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldWithMapValu() {
    var textFieldValue by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                if (newValue.length <= 10) {
                    textFieldValue = newValue
                }
            },
            modifier = Modifier.padding(top = 8.dp),
            maxLines = 1
        )
        Button(
            onClick = {
                numbersMap["textFieldValue"] = textFieldValue
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Save to Map")
        }
        Text(text = numbersMap["textFieldValue"] ?: "")
    }
}