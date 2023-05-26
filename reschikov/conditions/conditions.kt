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
import com.example.androidtaskcompose.ui.theme.ops
import com.example.androidtaskcompose.ui.theme.stackTemp
import com.example.scratch.createVariable.numbersMap
import com.example.scratch.printBlock.variableForView

@Composable
fun conditions() {
    var firstValue by rememberSaveable { mutableStateOf("") }
    var secondValue by rememberSaveable { mutableStateOf("") }
    var mainAnswer by remember { mutableStateOf(false) }
    var selectedComparsion by remember { mutableStateOf(">") }
    var variables by rememberSaveable { mutableStateOf("") }
    var buttonColor by remember {
        mutableStateOf(Color(android.graphics.Color.parseColor("#FF4C64")))
    }
    var colorFlag = true
    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = firstValue,
                onValueChange = { newVariable ->
                    if (newVariable.length <= 100) {
                        firstValue = newVariable
                    }
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
                value = secondValue,
                onValueChange = { newValue ->
                    secondValue = newValue
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
        Text(
            text = "$mainAnswer",
            color = Color.DarkGray
        )
//        Button(
//            onClick = {
//                firstValue = ops(firstValue).toString()
//                secondValue = ops(secondValue).toString()
//                buttonColor = Color.Green
//            },
//            colors = ButtonDefaults.buttonColors(
//                backgroundColor = buttonColor
//            ),
//            modifier = Modifier
//                .padding(top = 8.dp)
//                .fillMaxWidth()
//        ) {
//            Text("Remember", color = Color.White)
//        }
//        mainAnswer = compareNumbers(firstValue, secondValue, selectedComparsion)
//        Text(
//            text = "$mainAnswer",
//            color = Color.DarkGray
//        )
    }
}

fun compareNumbers(
    firstValue: String,
    secondValue: String,
    selectedComparison: String
): Boolean {
    val firstNumber = firstValue.toDoubleOrNull()
    val secondNumber = secondValue.toDoubleOrNull()

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