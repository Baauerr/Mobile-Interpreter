package com.example.scratch.mathOperations

import com.example.scratch.conditions.GlobalDataElse
import com.example.scratch.conditions.GlobalDataIf
import com.example.scratch.createVariable.numbersMap
import com.example.scratch.mainScreen.Blocks
import com.example.scratch.mainScreen.flag
import com.example.scratch.printBlock.variableForView


fun process(statement: String, blocksForConditions: MutableList<Blocks>) {
    val type = statement[0].toString().trim()
    var keyTextFieldValue = ""
    var valueTextFieldValue = ""
    var variables = ""
    when (type) {
        "=" -> {
            val elements = statement.split(" ")
            keyTextFieldValue = elements[1]
            valueTextFieldValue = elements[2]

            numbersMap[keyTextFieldValue] = ops(valueTextFieldValue).toString()
            if (flag)
                return
        }
        "p"->{
            val elements = statement.split(" ")
            variables = elements[1]

            var variablesArray = variables.split(",")
            for (values in variablesArray) {
                variableForView += ops(values).toString() + " "
            }
        }
        "?" ->{
            val elements = statement.split(";")
            variables = elements[1]
            var check = ops(variables.replace("\\s".toRegex(), ""))
            if(check == 1.0){
                for (index in 0 until GlobalDataIf.blocksForConditions.size) {
                    process(GlobalDataIf.blocksForConditions[index].expression.value, GlobalDataIf.blocksForConditions)
                    if (flag) {
                        variableForView = "Error"
                        break
                    }
                }
            }
            else{
                for (index in 0 until GlobalDataElse.blocksForElseConditions.size) {
                    process(GlobalDataElse.blocksForElseConditions[index].expression.value, GlobalDataElse.blocksForElseConditions)
                    if (flag) {
                        variableForView = "Error"
                        break
                    }
                }
            }
        }
    }
}