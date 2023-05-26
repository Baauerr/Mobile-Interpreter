package com.example.scratch.mainScreen

import androidx.compose.runtime.remember
import AppBar
import Block
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.example.scratch.createVariable.numbersMap
import com.example.scratch.forDraggingElements.SlideState
import com.example.scratch.mathOperations.process
import com.example.scratch.printBlock.variableForView
import drawerBody
import drawerHeader

var iDOfBlock = 0
var flag = false

@ExperimentalAnimationApi
@Composable
fun MainScreen() {
    val viewBlocks = remember { mutableStateListOf<Blocks>() }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val isExpanded = remember { mutableStateOf(false) }

    val slideStates = remember {
        mutableStateMapOf<Blocks, SlideState>().apply {
            viewBlocks.associateWith {
                SlideState.NONE
            }.also {
                putAll(it)
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation(
                modifier = Modifier
                    .height(50.dp),
                backgroundColor = (Color(android.graphics.Color.parseColor("#0E1621"))),
            ) {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Menu, contentDescription = "OpenMenu") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.PlayArrow, contentDescription = "Play") },
                    selected = true,
                    onClick = {
                        numbersMap.clear()
                        variableForView = ""
                        for (index in 0 until viewBlocks.size) {
                            flag = false
                            println(index)
                            process(viewBlocks[index].expression.value)
                            if (flag) {
                                variableForView = "Error"
                                break
                            }
                        }
                    }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.DateRange, contentDescription = "Console") },
                    selected = false,
                    onClick = { isExpanded.value = !isExpanded.value }
                )
            }
        },
        drawerContent = {
            drawerHeader()
            drawerBody(
                items = listOf(
                    Block(
                        id = "createVariable",
                        title = "New variable",
                        contentDescription = "CreatingVariable",
                        icon = Icons.Default.AddCircle,
                        boxColor = "#FF7F50"
                    ),
                    Block(
                        id = "mathOperation",
                        title = "Math operation",
                        contentDescription = "mathBlocks",
                        icon = Icons.Default.AddCircle,
                        boxColor = "#FF4C64"
                    ),
                    Block(
                        id = "createPrint",
                        title = "Print",
                        contentDescription = "creatingPrint",
                        icon = Icons.Default.AddCircle,
                        boxColor = "#EC2EFF"
                    ),
                    Block(
                        id = "createConditions",
                        title = "Condition",
                        contentDescription = "creatingCondition",
                        icon = Icons.Default.AddCircle,
                        boxColor = "#60ff60"
                    ),
                ),
                onItemClick = {
                    when (it.id) {
                        "createVariable" ->
                            viewBlocks.add(
                                Blocks(
                                    firstValue= " ",
                                    secondValue = " ",
                                    blockID = iDOfBlock++,
                                    color = "#FF7F50",
                                    expression = mutableStateOf(" "),
                                    blockType = "createVariable"
                                )
                            )
                        "mathOperation" -> viewBlocks.add(
                            Blocks(
                                firstValue= " ",
                                secondValue = " ",
                                blockID = iDOfBlock++,
                                color = "#FF4C64",
                                expression = mutableStateOf(" "),
                                blockType = "mathOperation"
                            )
                        )
                        "createPrint" -> viewBlocks.add(
                            Blocks(
                                firstValue= " ",
                                secondValue = " ",
                                blockID = iDOfBlock++,
                                color = "#EC2EFF",
                                expression = mutableStateOf(" "),
                                blockType = "createPrint"
                            )
                        )
                        "createConditions" ->
                            viewBlocks.add(
                                Blocks(
                                    firstValue = " ",
                                    secondValue = " ",
                                    blockID = iDOfBlock++,
                                    color = "#60ff60",
                                    expression = mutableStateOf(" "),
                                    blockType = "createConditions"
                                )
                            )
                    }
                }
            )
        },
        contentColor = (Color(android.graphics.Color.parseColor("#FFFFFF"))),
        drawerBackgroundColor = (Color.Black.copy(alpha = 0.4f)),
        backgroundColor = (Color(android.graphics.Color.parseColor("#17212B"))),
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            BlocksOnMainScreen(
                viewBlocks = viewBlocks,
                slideStates = slideStates,
                updateSlideState = { blockList, slideState -> slideStates[blockList] = slideState },
                updateItemPosition = { currentIndex, destinationIndex ->
                    val blockList = viewBlocks[currentIndex]
                    viewBlocks.removeAt(currentIndex)
                    viewBlocks.add(destinationIndex, blockList)
                    slideStates.apply {
                        viewBlocks.associateWith { SlideState.NONE }.also {
                            putAll(it)
                        }
                    }
                }
            )
        }
        if (isExpanded.value) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.6f))
                    .padding(bottom = 55.dp),
                Arrangement.Bottom,
            )
            {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp)
                        .shadow(2.dp)
                        .background(
                            Color(android.graphics.Color.parseColor("#0E1621")),
                            shape = RoundedCornerShape(8.dp)
                        ),

                    Alignment.Center,

                    ) {
                    LazyRow()
                    {
                        item {

                            Text(text = variableForView, fontSize = 30.sp)
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun BlocksOnMainScreen(
    viewBlocks: MutableList<Blocks>,
    slideStates: Map<Blocks, SlideState>,
    updateSlideState: (blockList: Blocks, slideState: SlideState) -> Unit,
    updateItemPosition: (currentIndex: Int, destinationIndex: Int) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(
            items = viewBlocks,
        ) { index, _ ->
            val block = viewBlocks.getOrNull(index)
            if (block != null) {
                key(block) {
                    val slideState = slideStates[block] ?: SlideState.NONE
                    visualBlock(
                        index = index,
                        block = block,
                        slideState = slideState,
                        viewBlocks = viewBlocks,
                        updateSlideState = updateSlideState,
                        updateItemPosition = updateItemPosition
                    )
                }
            }
        }
    }
}


