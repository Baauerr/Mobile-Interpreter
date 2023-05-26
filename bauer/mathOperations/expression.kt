package com.example.scratch.mathOperations

import com.example.scratch.createVariable.numbersMap
import com.example.scratch.mainScreen.flag
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
            if (flag)
                return
            //println(numbersMap[keyTextFieldValue])
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