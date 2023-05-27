package com.example.scratch.createVariable

import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.scratch.mainScreen.Blocks
import com.example.scratch.mathOperations.ops

val numbersMap = mutableStateMapOf("textFieldValue" to "")

@Composable
fun textFieldWithMapValue(block: Blocks) {
    var randomMassiveSecond = remember { mutableStateListOf<Blocks >() }
    var keyTextFieldValue by rememberSaveable { mutableStateOf("") }
    var valueTextFieldValue by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (block.firstValue != "") {
                keyTextFieldValue = block.firstValue
            }
            if (block.secondValue != "") {
                valueTextFieldValue = block.secondValue
            }
            TextField(
                value = keyTextFieldValue,
                onValueChange = {
                    keyTextFieldValue = it
                    block.firstValue = it
                },
                modifier = Modifier
                    .padding(top = 4.dp)
                    .weight(1f),
                maxLines = 1,
                textStyle = TextStyle(color = Color.White),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFF333333)
                )
            )
            Text(text = " = ", color = Color.White)
            TextField(
                value = valueTextFieldValue,
                onValueChange = {
                    valueTextFieldValue = it
                    block.secondValue = it
                    block.expression.value = "=$keyTextFieldValue$valueTextFieldValue"
                },
                modifier = Modifier
                    .padding(top = 4.dp)
                    .weight(2f),
                maxLines = 1,
                textStyle = TextStyle(color = Color.White),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFF444444)
                )
            )
        }
    }
}