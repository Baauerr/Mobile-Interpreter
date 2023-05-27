package com.example.scratch.conditions

import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.RowScopeInstance.weight
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.scratch.createVariable.textFieldWithMapValue
import com.example.scratch.forDraggingElements.dragToReorder
import com.example.scratch.mathOperations.ops
import com.example.scratch.mainScreen.Blocks
import com.example.scratch.mainScreen.itemHeight
import com.example.scratch.mathOperations.opsExpression
import com.example.scratch.printBlock.printBlock

object GlobalDataIf {
    val blocksForConditions = mutableStateListOf<Blocks>()
}

object GlobalDataElse {
    val blocksForElseConditions = mutableStateListOf<Blocks>()
}

@Composable
fun conditions(block: Blocks) {
    Column() {
        ifBlock(block = block)
        elseBlock(block = block)
    }
}

@Composable
fun ifBlock(block: Blocks) {
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
                textStyle = TextStyle(color = Color.White),
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
                    block.expression.value = "?;$firstKey$selectedComparsion$secondKey"

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
        addConditionsMenu()
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            for (items in GlobalDataIf.blocksForConditions) {
                when (items.blockType) {
                    "createVariable" ->
                        Row() {
                            Box(
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
                                        .fillMaxHeight(),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Red,
                                    ),
                                    onClick = {
                                        if (block.blockType == "createConditions") {
                                            GlobalDataIf.blocksForConditions.remove(items)
                                        }
                                    },
                                ) {}
                            }
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
                                opsExpression(items)
                                Button(
                                    modifier = Modifier
                                        .fillMaxHeight(),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Red,
                                    ),
                                    onClick = {
                                        if (block.blockType == "createConditions") {
                                            GlobalDataIf.blocksForConditions.remove(items)
                                        }
                                    },
                                ) {}
                            }
                        }
                    "createPrint" ->
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
                                printBlock(items)
                                Button(
                                    modifier = Modifier
                                        .fillMaxHeight(),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Red,
                                    ),
                                    onClick = {
                                        if (block.blockType == "createConditions") {
                                            GlobalDataIf.blocksForConditions.remove(items)
                                        }
                                    },
                                ) {}
                            }
                        }
                }
            }
        }
    }
}

@Composable
fun elseBlock(block: Blocks) {
    var firstElseKey by rememberSaveable { mutableStateOf("") }
    var secondElseKey by rememberSaveable { mutableStateOf("") }
    var mainAnswer by rememberSaveable { mutableStateOf(false) }
    var selectedComparsion by rememberSaveable { mutableStateOf("") }
    var buttonColor by remember {
        mutableStateOf(Color(android.graphics.Color.parseColor("#FF4C64")))
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (block.firstValue != "") {
                firstElseKey = block.firstValue
            }
            if (block.secondValue != "") {
                secondElseKey = block.secondValue
            }
            TextField(
                value = "Else",
                onValueChange = {

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
        addConditionsElseMenu()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f)
        ) {
            for (items in GlobalDataElse.blocksForElseConditions) {
                when (items.blockType) {
                    "createVariable" -> textFieldWithMapValue(items)
                    "mathOperation" -> opsExpression(items)
                    "createPrint" -> printBlock(items)
                    "createConditions" -> conditions(items)
                }
            }
        }
    }
}

@Composable
fun dropdownMenu(): String {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }

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
                contentDescription = "Стрелка вниз"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },

            ) {
            DropdownMenuItem(
                onClick = {
                    selectedItem = "<"
                    expanded = false
                }
            ) {
                Text("<")
            }
            DropdownMenuItem(
                onClick = {
                    selectedItem = ">"
                    expanded = false
                }
            ) {
                Text(">")
            }
            DropdownMenuItem(
                onClick = {
                    selectedItem = "="
                    expanded = false
                }
            ) {
                Text("=")
            }
            DropdownMenuItem(
                onClick = {
                    selectedItem = "!"
                    expanded = false
                }
            ) {
                Text("!")
            }
            DropdownMenuItem(
                onClick = {
                    selectedItem = ">"
                    expanded = false
                }
            ) {
                Text(">=")
            }
            DropdownMenuItem(
                onClick = {
                    selectedItem = "<="
                    expanded = false
                }
            ) {
                Text("<=")
            }
        }
    }
    return selectedItem
}

var conditionsIfBlockID = 0
var conditionsElseBlockID = 0

@Composable
fun addConditionsMenu() {
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
                    GlobalDataIf.blocksForConditions.add(
                        Blocks(
                            firstValue = " ",
                            secondValue = " ",
                            blockID = conditionsIfBlockID++,
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
                    GlobalDataIf.blocksForConditions.add(
                        Blocks(
                            firstValue = " ",
                            secondValue = " ",
                            blockID = conditionsIfBlockID++,
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
                    GlobalDataIf.blocksForConditions.add(
                        Blocks(
                            firstValue = " ",
                            secondValue = " ",
                            blockID = conditionsIfBlockID++,
                            color = "#EC2EFF",
                            expression = mutableStateOf(" "),
                            blockType = "createPrint"
                        )
                    )
                }
            ) {
                Text("Print")
            }

        }
    }
}
@Composable
fun addConditionsElseMenu() {
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
                    GlobalDataElse.blocksForElseConditions.add(
                        Blocks(
                            firstValue = " ",
                            secondValue = " ",
                            blockID = conditionsElseBlockID++,
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
                    GlobalDataElse.blocksForElseConditions.add(
                        Blocks(
                            firstValue = " ",
                            secondValue = " ",
                            blockID = conditionsElseBlockID++,
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
                    GlobalDataElse.blocksForElseConditions.add(
                        Blocks(
                            firstValue = " ",
                            secondValue = " ",
                            blockID = conditionsElseBlockID++,
                            color = "#EC2EFF",
                            expression = mutableStateOf(" "),
                            blockType = "createPrint"
                        )
                    )
                }
            ) {
                Text("Print")
            }

        }
    }
}