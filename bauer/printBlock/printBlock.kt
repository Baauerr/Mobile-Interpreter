package com.example.scratch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import numbersMap

var variableForView = ""

@Composable
fun printBlock(){
    var variables by remember { mutableStateOf("") }
    var buttonColor by remember {
        mutableStateOf(Color(android.graphics.Color.parseColor("#FF4C64")))
    }
    var colorFlag = true
    Column(modifier = Modifier.padding(16.dp)){
        Row(verticalAlignment = Alignment.CenterVertically){
            TextField(
                value = variables,
                onValueChange = {newVariable ->
                    if (newVariable.length <= 10){
                        variables = newVariable
                    }
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
        }
        Button(
            onClick = {
                    var variablesArray = variables.split(",")
                    println(variablesArray)
                    for (values in variablesArray) {
                        if (numbersMap.containsKey(values)) {
                            variableForView += numbersMap.getValue(values).toString() + " "
                        } else {
                            colorFlag = false
                            variableForView += ""
                        }
                    }
                    println(variables)
                if (!colorFlag){
                    buttonColor = Color.Red
                }
                else {
                    buttonColor = Color.Green
                }
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
    }
}
