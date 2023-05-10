package com.example.androidtaskcompose.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import numbersMap
import java.util.*
import java.util.Stack

@Composable
fun opsExpression() {
    var keyTextFieldValue by remember { mutableStateOf("") }
    var valueTextFieldValue by remember { mutableStateOf("") }
    var savedKey by remember { mutableStateOf("") }
    var result by remember { mutableStateOf(0.0) }

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
                textStyle = TextStyle(color = androidx.compose.ui.graphics.Color.White),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFF333333)
                )
            )
            Text(text = " = ", color = androidx.compose.ui.graphics.Color.White)
            TextField(
                value = valueTextFieldValue,
                onValueChange = { newValue ->
                        valueTextFieldValue = newValue
                },
                modifier = Modifier.padding(top = 4.dp)
                    .weight(2f),
                maxLines = 1,
                textStyle = TextStyle(color = androidx.compose.ui.graphics.Color.White),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFF444444)
                )
            )
        }

        Button(
            onClick = {
                numbersMap[keyTextFieldValue] = valueTextFieldValue
                savedKey = keyTextFieldValue
                result = ops(valueTextFieldValue)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(android.graphics.Color.parseColor("#FF4C64"))),
            modifier = Modifier.padding(top = 8.dp).fillMaxWidth()
        ) {
            Text("Result", color = androidx.compose.ui.graphics.Color.White)
        }

        Text(
            text = "$savedKey = $result",
            color = androidx.compose.ui.graphics.Color.White
        )
    }
}
fun ops(expression: String): Double {
    val operators = Stack<Char>()
    val outputQueue = LinkedList<String>()

    // Проходим по выражению и формируем очередь выхода
    var i = 0
    while (i < expression.length) {
        val c = expression[i]
        when (c) {
            in '0'..'9', '.' -> {
                // Если текущий символ - цифра или точка, то добавляем ее и все следующие символы числа в очередь выхода
                var number = ""
                while (i < expression.length && (expression[i] in '0'..'9' || expression[i] == '.')) {
                    number += expression[i]
                    i++
                }
                outputQueue.add(number)
                i--
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
            '+', '-', '*', '/' -> {
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
    val values = Stack<Double>()
    for (token in outputQueue) {
        when {
            token.matches("-?\\d+(\\.\\d+)?".toRegex()) -> {
                values.push(token.toDouble())
            }
            else -> {
                val b = values.pop()
                val a = if (values.isNotEmpty()) values.pop() else 0.0
                val result = when (token) {
                    "+" -> a + b
                    "-" -> a - b
                    "*" -> a * b
                    "/" -> a / b
                    else -> throw IllegalArgumentException("Неподдерживаемый оператор: $token")
                }
                values.push(result)
            }
        }
    }

    // Возвращаем результат
    return if (values.isNotEmpty()) values.pop() else 0.0
}
fun getPrecedence(c: Char): Int {
    return when (c) {
        '+', '-' -> 1
        '*', '/' -> 2
        else -> 0
    }
}
