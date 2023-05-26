package com.example.androidtaskcompose.ui.theme

import Blocks
import android.app.PendingIntent.getActivity
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
import com.example.scratch.printBlock.variableForView
import flag
import java.util.*
import java.util.Stack

val stackTemp = Stack<Double>()

object GlobalStack {
    val values = Stack<Double>()
}

@Composable
fun opsExpression(viewBlocks: MutableList<Blocks>, blockID: Int) {
    var keyTextFieldValue by rememberSaveable { mutableStateOf("") }
    var valueTextFieldValue by rememberSaveable { mutableStateOf("") }
    var savedKey by remember { mutableStateOf("") }
    var result by remember { mutableStateOf(0.0) }
    var buttonColor by remember {
        mutableStateOf(Color(android.graphics.Color.parseColor("#FF4C64")))
    }
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
                textStyle = TextStyle(color = Color.White),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFF333333)
                )
            )
            Text(text = " = ", color = Color.White)
            TextField(
                value = valueTextFieldValue,
                onValueChange = { newValue ->
                    valueTextFieldValue = newValue
                    viewBlocks[blockID].expression.value = "= $keyTextFieldValue $valueTextFieldValue"
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

    // Проходим по выражению и формируем очередь выхода
    var i = 0

    while (i < expression.length) {
        val c = expression[i].toChar()
        when (c) {
            in '0'..'9', '.'-> {
                // Если текущий символ - цифра или точка, то добавляем ее и все следующие символы числа в очередь выхода
                var number = ""
                while (i < expression.length && (expression[i] in '0'..'9' || expression[i] == '.')) {
                    number += expression[i]
                    i++
                }
                outputQueue.add(number)
                i--
            }
            in 'a'..'z' -> {
                // Если текущий символ - цифра или точка, то добавляем ее и все следующие символы числа в очередь выхода
                var number = ""
                while (i < expression.length && ((expression[i] in 'a'..'z') or (expression[i] in 'A'..'Z'))) {
                    number += expression[i]
                    i++
                }
                if (numbersMap.containsKey(number)) {
                    var current = numbersMap[number]
                    outputQueue.add(current.toString())
                    i--
                }
                else {
                    flag = true
                    return(0.0)

                }
            }
            in 'A'..'Z' -> {
                // Если текущий символ - цифра или точка, то добавляем ее и все следующие символы числа в очередь выхода
                var number = ""
                while (i < expression.length && ((expression[i] in 'a'..'z') or (expression[i] in 'A'..'Z'))) {
                    number += expression[i]
                    i++
                }
                if (numbersMap.containsKey(number)) {
                    var current = numbersMap[number]
                    outputQueue.add(current.toString())
                    i--
                }
                else {
                    flag = true
                    return(0.0)
                }
            }
            '(' -> operators.push(c)
            ')' -> {
                while (operators.isNotEmpty() && operators.peek() != '(') {
                    outputQueue.add(operators.pop().toString())
                }
                if (operators.isNotEmpty() && operators.peek() == '(') {
                    operators.pop() // Удаляем открывающую скобку из стека
                } else {
                    throw IllegalArgumentException("Неправильное использование скобок")
                }
            }
            '+', '-', '*', '/', '%' -> {
                while (operators.isNotEmpty() && getPrecedence(c) <= getPrecedence(operators.peek())) {
                    outputQueue.add(operators.pop().toString())
                }
                operators.push(c)
            }
        }
        i++
    }

    // Добавляем оставшиеся операторы из стека в очередь выхода
    while (operators.isNotEmpty()) {
        if (operators.peek() == '(' || operators.peek() == ')') {
            throw IllegalArgumentException("Неправильное использование скобок")
        }
        outputQueue.add(operators.pop().toString())
    }

    // Вычисляем значение выражения из очереди выхода
    for (token in outputQueue) {
        when {
            token.matches("[-+]?\\w+([.]?\\w+)?".toRegex()) -> {
                var variable = token.toDoubleOrNull()
                GlobalStack.values.push(variable)
            }
            else -> {
                val b = GlobalStack.values.pop()
                val a = if (GlobalStack.values.isNotEmpty()) GlobalStack.values.pop() else 0.0
                val result = when (token) {
                    "+" -> a + b
                    "-" -> a - b
                    "*" -> a * b
                    "/" -> a / b
                    "%" -> a % b
                    else -> throw IllegalArgumentException("Неподдерживаемый оператор: $token")
                }
                GlobalStack.values.push(result)
            }
        }
    }

// Возвращаем результат вычисления
    return GlobalStack.values.pop()
}



fun getPrecedence(c: Char): Int {
    return when (c) {
        '+', '-' -> 1
        '*', '/' , '%' -> 2
        else -> 0
    }
}