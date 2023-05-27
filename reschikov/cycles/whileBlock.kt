package com.example.scratch.cycles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.scratch.conditions.*
import com.example.scratch.conditions.addConditionsMenu
import com.example.scratch.createVariable.textFieldWithMapValue
import com.example.scratch.mainScreen.Blocks
import com.example.scratch.mathOperations.opsExpression
import com.example.scratch.printBlock.printBlock

object GlobalDataWhile {
    val blocksForWhile = mutableStateListOf<Blocks>()
}

@Composable
fun whileBlock(block: Blocks) {
        var firstKey by rememberSaveable { mutableStateOf("") }
        var secondKey by rememberSaveable { mutableStateOf("") }
        var mainAnswer by rememberSaveable { mutableStateOf(false) }
        var selectedComparsion by rememberSaveable { mutableStateOf("") }
        var buttonColor by remember {
            mutableStateOf(Color(android.graphics.Color.parseColor("#FF4C64")))
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (block.firstValue != "") {
                    firstKey = block.firstValue
                }
                if (block.secondValue != "") {
                    secondKey = block.secondValue
                }
                TextField(
                    value = firstKey,
                    onValueChange = {
                        firstKey = it
                        block.firstValue = it

                    },
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .weight(2f),
                    textStyle = TextStyle(color = androidx.compose.ui.graphics.Color.White),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFF333333)
                    )
                )
                selectedComparsion = dropdownMenu()
                TextField(
                    value = secondKey,
                    onValueChange = {
                        secondKey = it
                        block.secondValue = it
                        block.expression.value = "w;$firstKey$selectedComparsion$secondKey"
                        println(block.expression.value)

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
            addWhileMenu()
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                for (items in  GlobalDataWhile.blocksForWhile) {
                    when (items.blockType) {
                        "createVariable" ->
                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                                    .background(
                                        color = (Color(android.graphics.Color.parseColor(items.color))),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            )
                            {
                                textFieldWithMapValue(items)
                                Button(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Red,
                                    ),
                                    onClick = {
                                        if (block.blockType == "createConditions") {
                                            GlobalDataWhile.blocksForWhile.remove(items)
                                        }
                                    },
                                ) {}
                            }
                        "mathOperation" ->
                            Row() {
                                Box(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth()
                                        .background(
                                            color = Color(android.graphics.Color.parseColor(items.color)),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                ) {
                                    Button(
                                        modifier = Modifier
//                                        .weight(1f)
                                            .fillMaxHeight(),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color.Red,
                                        ),
                                        onClick = {
                                            if (block.blockType == "createConditions") {
                                                GlobalDataWhile.blocksForWhile.remove(items)
                                            }
                                        },
                                    ) {}
                                    opsExpression(items)
                                }
                            }
                        "createPrint" ->
                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                                    .background(
                                        color = Color(android.graphics.Color.parseColor(items.color)),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                Button(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Red,
                                    ),
                                    onClick = {
                                        if (block.blockType == "createConditions") {
                                            GlobalDataWhile.blocksForWhile.remove(items)
                                        }
                                    },
                                ) {}
                                printBlock(items)
                            }
                        "createConditions" ->
                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                                    .background(
                                        color = (Color(android.graphics.Color.parseColor(items.color))),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            )
                            {
                                Button(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Red,
                                    ),
                                    onClick = {
                                        if (block.blockType == "createConditions") {
                                            GlobalDataWhile.blocksForWhile.remove(items)
                                        }
                                    },
                                ) {}
                                conditions(items)
                            }
                    }
                }
            }
        }
}

var whileBlockID = 0
@Composable
fun addWhileMenu() {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("+") }

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
                contentDescription = "+"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },

            ) {
            DropdownMenuItem(
                onClick = {
                    GlobalDataWhile.blocksForWhile.add(
                        Blocks(
                            firstValue = " ",
                            secondValue = " ",
                            blockID = whileBlockID++,
                            color = "#FF7F50",
                            expression = mutableStateOf(" "),
                            blockType = "createVariable"
                        )
                    )
                }
            ) {
                Text("AddVariable")
            }
            DropdownMenuItem(
                onClick = {
                    GlobalDataWhile.blocksForWhile.add(
                        Blocks(
                            firstValue = " ",
                            secondValue = " ",
                            blockID = whileBlockID++,
                            color = "#FF4C64",
                            expression = mutableStateOf(" "),
                            blockType = "mathOperation"
                        )
                    )
                }
            ) {
                Text("Math")
            }
            DropdownMenuItem(
                onClick = {
                    GlobalDataWhile.blocksForWhile.add(
                        Blocks(
                            firstValue = " ",
                            secondValue = " ",
                            blockID = whileBlockID++,
                            color = "#EC2EFF",
                            expression = mutableStateOf(" "),
                            blockType = "createPrint"
                        )
                    )
                }
            ) {
                Text("Print")
            }
            DropdownMenuItem(
                onClick = {
                    GlobalDataWhile.blocksForWhile.add(
                        Blocks(
                            firstValue = " ",
                            secondValue = " ",
                            blockID = whileBlockID++,
                            color = "#FF7F50",
                            expression = mutableStateOf(" "),
                            blockType = "createConditions"
                        )
                    )
                }
            ) {
                Text("If-Else")
            }
        }
    }
}

