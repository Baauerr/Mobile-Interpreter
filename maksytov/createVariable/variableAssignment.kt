
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.androidtaskcompose.ui.theme.GlobalStack
import java.util.Stack


val numbersMap = mutableStateMapOf("textFieldValue" to "")
@Composable
fun textFieldWithMapValue() {
    var keyTextFieldValue by remember { mutableStateOf("") }
    var valueTextFieldValue by remember { mutableStateOf("") }
    var savedKey by remember { mutableStateOf("") }
    var currentKey by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = keyTextFieldValue,
                onValueChange = { newVariable ->
                    if (newVariable.length <= 10) {
                        keyTextFieldValue = newVariable
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
            Text(text = " = ", color = Color.White)
            TextField(
                value = valueTextFieldValue,
                onValueChange = { newValue ->
                    if (newValue.length <= 10) {
                        valueTextFieldValue = newValue
                    }
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
                if (numbersMap.containsKey(keyTextFieldValue)) {
                    // Если ключ уже есть в словаре, меняем значение
                    val oldValue = numbersMap[keyTextFieldValue]
                    numbersMap[keyTextFieldValue] = valueTextFieldValue
                    currentKey = keyTextFieldValue
                    if (oldValue != valueTextFieldValue) {
                        GlobalStack.values.removeLast()
                        GlobalStack.values.push(numbersMap[currentKey]?.toDouble() ?: "".toDouble())
                        println(GlobalStack.values)
                    }
                } else {
                    // Иначе добавляем новую пару ключ-значение в словарь
                    numbersMap[keyTextFieldValue] = valueTextFieldValue
                    currentKey = keyTextFieldValue
                    GlobalStack.values.push(numbersMap[currentKey]?.toDouble() ?: "".toDouble())
                    println(GlobalStack.values)
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(android.graphics.Color.parseColor("#FF4C64"))
            ),
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            Text("Assignment", color = Color.White)
        }
    }
}