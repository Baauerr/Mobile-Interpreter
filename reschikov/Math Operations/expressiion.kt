package com.example.scratch.`Math Operations`

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import com.example.androidtaskcompose.ui.theme.GlobalStack
import com.example.androidtaskcompose.ui.theme.ops
import com.example.androidtaskcompose.ui.theme.stackTemp
import com.example.scratch.createVariable.numbersMap
import com.example.scratch.printBlock.variableForView


fun process(statement: String) {
    val elements = statement.split(" ")
    val type = elements[0].trim()
    var keyTextFieldValue = " "
    var valueTextFieldValue = " "
    var variables = " "
    when (type) {
        "=" -> {
            keyTextFieldValue = elements[1]
            valueTextFieldValue = elements[2]

            numbersMap[keyTextFieldValue] = ops(valueTextFieldValue).toString()
            println(numbersMap[keyTextFieldValue])
        }
        "p"->{
            variables = elements[1]

            var variablesArray = variables.split(",")
            for (values in variablesArray) {
                variableForView += ops(values).toString() + " "
            }
        }
        "?" ->{

        }
    }
}