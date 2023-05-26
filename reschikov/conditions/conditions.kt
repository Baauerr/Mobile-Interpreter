package com.example.scratch.conditions

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.scratch.mathOperations.ops
import com.example.scratch.mainScreen.Blocks

@Composable
fun conditions(block: Blocks) {
    var firstKey by rememberSaveable { mutableStateOf("") }
    var secondKey by rememberSaveable { mutableStateOf("") }
    var mainAnswer by rememberSaveable { mutableStateOf(false) }
    var selectedComparsion by rememberSaveable { mutableStateOf(">") }
    var buttonColor by remember {
        mutableStateOf(Color(android.graphics.Color.parseColor("#FF4C64")))
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (block.firstValue != "") {
                firstKey = block.firstValue
            }
            if (block.secondValue != "") {
                secondKey = block.secondValue
            }
            TextField(
                value = firstKey,
                onValueChange = {
                    firstKey = it
                    block.firstValue = it

                },
                modifier = Modifier
                    .padding(top = 4.dp)
                    .weight(2f),
                textStyle = TextStyle(color = Color.White),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFF333333)
                )
            )
            selectedComparsion = dropdownMenu()
            TextField(
                value = secondKey,
                onValueChange = {
                    firstKey = it
                    block.secondValue = it

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
        Button(
            onClick = {
                firstKey = ops(firstKey).toString()
                secondKey = ops(secondKey).toString()
                buttonColor = Color.Green
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonColor
            ),
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            Text("Remember", color = Color.White)
        }
        mainAnswer = compareNumbers(firstKey, secondKey, selectedComparsion)
        Text(
            text = "$mainAnswer",
            color = Color.DarkGray
        )
    }
}

fun compareNumbers(
    firstKey: String,
    secondKey: String,
    selectedComparison: String
): Boolean {
    val firstNumber = firstKey.toDoubleOrNull()
    val secondNumber = secondKey.toDoubleOrNull()

    return when (selectedComparison) {
        "<" -> firstNumber != null && secondNumber != null && firstNumber < secondNumber
        ">" -> firstNumber != null && secondNumber != null && firstNumber > secondNumber
        "==" -> firstNumber != null && secondNumber != null && firstNumber == secondNumber
        "!=" -> firstNumber != null && secondNumber != null && firstNumber != secondNumber
        ">=" -> firstNumber != null && secondNumber != null && firstNumber >= secondNumber
        "<=" -> firstNumber != null && secondNumber != null && firstNumber <= secondNumber
        else -> false
    }
}

@Composable
fun dropdownMenu(): String {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(">") }

    Column {
        Button(
            onClick = { expanded = true },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.DarkGray
            ),
        ) {
            Text(selectedItem)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Стрелка вниз"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },

            ) {
            DropdownMenuItem(
                onClick = {
                    selectedItem = "<"
                    expanded = false
                }
            ) {
                Text("<")
            }
            DropdownMenuItem(
                onClick = {
                    selectedItem = ">"
                    expanded = false
                }
            ) {
                Text(">")
            }
            DropdownMenuItem(
                onClick = {
                    selectedItem = "=="
                    expanded = false
                }
            ) {
                Text("==")
            }
            DropdownMenuItem(
                onClick = {
                    selectedItem = "!="
                    expanded = false
                }
            ) {
                Text("!=")
            }
            DropdownMenuItem(
                onClick = {
                    selectedItem = ">"
                    expanded = false
                }
            ) {
                Text(">=")
            }
            DropdownMenuItem(
                onClick = {
                    selectedItem = "<="
                    expanded = false
                }
            ) {
                Text("<=")
            }
        }
    }
    return selectedItem
}