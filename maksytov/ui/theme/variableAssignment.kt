package com.example.androidtaskcompose.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

val numbersMap = mutableStateMapOf("textFieldValue" to "")

@Composable
fun TextFieldWithMapValue() {
    var keyTextFieldValue by remember { mutableStateOf("") }
    var valueTextFieldValue by remember { mutableStateOf("") }
    var savedKey by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = keyTextFieldValue,
                onValueChange = { newVariable ->
                    if (newVariable.length <= 10) {
                        keyTextFieldValue = newVariable
                    }
                },
                modifier = Modifier.padding(top = 4.dp)
                    .weight(1f),
                maxLines = 1,
                textStyle = TextStyle(color = androidx.compose.ui.graphics.Color.White),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFF333333)
                )
            )
            Text(text = " = ", color = androidx.compose.ui.graphics.Color.White)
            TextField(
                value = valueTextFieldValue,
                onValueChange = { newValue ->
                    if (newValue.length <= 10) {
                        valueTextFieldValue = newValue
                    }
                },
                modifier = Modifier.padding(top = 4.dp)
                    .weight(2f),
                maxLines = 1,
                textStyle = TextStyle(color = androidx.compose.ui.graphics.Color.White),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFF444444)
                )
            )
        }

        Button(
            onClick = {
                numbersMap[keyTextFieldValue] = valueTextFieldValue
                savedKey = keyTextFieldValue
            },
            colors= ButtonDefaults.buttonColors(backgroundColor = Color(android.graphics.Color.parseColor("#FF4C64"))),
            modifier = Modifier.padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            Text("Assignment", color = androidx.compose.ui.graphics.Color.White)
        }
        Text(
            text = "$savedKey = ${numbersMap[savedKey] ?: ""}",
            color = androidx.compose.ui.graphics.Color.White
        )
    }
}
@Composable
fun mainDisplay() {
    Column(
        modifier = Modifier
            .background(Color(android.graphics.Color.parseColor("#0E1621")))
            .fillMaxSize()
    ) {
    }
}
