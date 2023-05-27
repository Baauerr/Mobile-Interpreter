package com.example.scratch.printBlock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.scratch.dimensions.columnPadding
import com.example.scratch.dimensions.textFieldsPadding
import com.example.scratch.dimensions.textPadding
import com.example.scratch.mainScreen.Blocks
import com.example.scratch.mathOperations.process
import com.example.scratch.ui.theme.forHeaders

var variableForView = ""

@Composable
fun printBlock(block: Blocks) {
    var variables by rememberSaveable { mutableStateOf("") }
    Column(modifier = Modifier.padding(top = columnPadding / 2, start = columnPadding, end = columnPadding)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (block.firstValue != "") {
                variables = block.firstValue
            }
            TextField(
                value = variables,
                onValueChange = {
                        variables = it
                        block.firstValue = it
                        var temp = variables.replace("\\s".toRegex(), "")
                    println(temp)
                        block.expression.value = "p $temp"
                    println(block.expression.value)
//                        process(variables)
                },
                modifier = Modifier
                    .padding(top = textFieldsPadding)
                    .weight(1f),
                maxLines = 1,
                textStyle = TextStyle(color = Color.White),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFF333333)
                )
            )
        }
        Text (text = "Print",  modifier = Modifier.padding(top = textPadding, bottom = textPadding), fontFamily = forHeaders)
    }
}