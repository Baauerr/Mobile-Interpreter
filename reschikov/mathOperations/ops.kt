package com.example.scratch.mathOperations

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.scratch.createVariable.numbersMap
import com.example.scratch.mainScreen.Blocks
import com.example.scratch.mainScreen.flag
import java.util.*
import java.util.Stack

object GlobalStack {
    val values = Stack<Double>()
}

@Composable
fun opsExpression(viewBlocks: MutableList<Blocks>,block: Blocks) {
    var keyTextFieldValue = rememberSaveable { mutableStateOf("") }
    var valueTextFieldValue = rememberSaveable { mutableStateOf("") }
    var savedKey by remember { mutableStateOf("") }
    var result by remember { mutableStateOf(0.0) }
    var buttonColor by remember {
        mutableStateOf(Color(android.graphics.Color.parseColor("#FF4C64")))
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (block.firstValue != "") {
                keyTextFieldValue.value = block.firstValue
            }
            if (block.secondValue != "") {
                valueTextFieldValue.value = block.secondValue
            }
            TextField(
                value = keyTextFieldValue.value,
                onValueChange = {
                    keyTextFieldValue.value = it
                    block.firstValue = it
                },
                modifier = Modifier.padding(top = 4.dp)
                    .weight(1f),
                textStyle = TextStyle(color = Color.White),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFF333333)
                )
            )
            println(block.expression)
            Text(text = " = ", color = Color.White)
            TextField(
                value = valueTextFieldValue.value,
                onValueChange = {
                    valueTextFieldValue.value = it
                    block.secondValue = it
                    block.expression.value = "=${keyTextFieldValue.value}${valueTextFieldValue.value}"
                },
                modifier = Modifier.padding(top = 4.dp)
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
fun ops(expression: String): Double {
    val operators = Stack<Char>()
    val outputQueue = LinkedList<String>()

    val comparators = mapOf<String, (Double, Double) -> Boolean>(
        ">" to { a, b -> a > b },
        "<" to { a, b -> a < b },
        "=" to { a, b -> a == b },
        "!" to { a, b -> a != b },
        ">=" to { a, b -> a >= b},
        "<=" to { a, b -> a <= b }
    )

    var i = 0

    while (i < expression.length) {
        val c = expression[i].toChar()
        when (c) {
            in '0'..'9', '.' -> {
                var number = ""
                while (i < expression.length && (expression[i] in '0'..'9' || expression[i] == '.')) {
                    number += expression[i]
                    i++
                }
                outputQueue.add(number)
                i--
            }
            in 'a'..'z', in 'A'..'Z' -> {
                var number = ""
                while (i < expression.length && ((expression[i] in 'a'..'z') || (expression[i] in 'A'..'Z'))) {
                    number += expression[i]
                    i++
                }
                if (numbersMap.containsKey(number)) {
                    val current = numbersMap[number]
                    outputQueue.add(current.toString())
                    i--
                } else {
                    flag = true
                    return 0.0
                }
            }
            '(' -> operators.push(c)
            ')' -> {
                while (operators.isNotEmpty() && operators.peek() != '(') {
                    outputQueue.add(operators.pop().toString())
                }
                if (operators.isNotEmpty() && operators.peek() == '(') {
                    operators.pop()
                } else {
                    throw IllegalArgumentException("Неправильное использование скобок")
                }
            }
            '+', '-', '*', '/', '%', '>', '<', '=', '!' -> {
                while (operators.isNotEmpty() && getPrecedence(c) <= getPrecedence(operators.peek())) {
                    outputQueue.add(operators.pop().toString())
                }
                operators.push(c)
            }
        }
        i++
    }

    while (operators.isNotEmpty()) {
        if (operators.peek() == '(' || operators.peek() == ')') {
            throw IllegalArgumentException("Неправильное использование скобок")
        }
        outputQueue.add(operators.pop().toString())
    }

    for (token in outputQueue) {
        when {
            token.matches("[-+]?\\w+([.]?\\w+)?".toRegex()) -> {
                val variable = token.toDoubleOrNull()
                GlobalStack.values.push(variable)
            }
            else -> {
                val b = GlobalStack.values.pop()
                val a = if (GlobalStack.values.isNotEmpty()) GlobalStack.values.pop() else 0.0
                val result = when {
                    comparators.containsKey(token) -> {
                        val comparator = comparators[token] ?: error("Неподдерживаемый оператор: $token")
                        if (comparator(a, b)) 1.0 else 0.0
                    }
                    else -> {
                        when (token) {
                            "+" -> a + b
                            "-" -> a - b
                            "*" -> a * b
                            "/" -> a / b
                            "%" -> a % b
                            else -> throw IllegalArgumentException("Неподдерживаемый оператор: $token")
                        }
                    }
                }
                GlobalStack.values.push(result)
            }
        }
    }
    return GlobalStack.values.pop()
}



fun getPrecedence(c: Char): Int {
    return when (c) {
        '>','<','=','!' -> 1
        '+', '-' -> 2
        '*', '/' , '%' -> 3
        else -> 0
    }
}