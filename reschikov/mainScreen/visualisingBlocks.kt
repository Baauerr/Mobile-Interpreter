package com.example.scratch.mainScreen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.scratch.conditions.GlobalDataElse
import com.example.scratch.conditions.GlobalDataIf
import com.example.scratch.conditions.conditions
import com.example.scratch.mathOperations.opsExpression
//import com.example.scratch.conditions.conditions
import com.example.scratch.createVariable.textFieldWithMapValue
import com.example.scratch.cycles.whileBlock
import com.example.scratch.forDraggingElements.SlideState
import com.example.scratch.forDraggingElements.dragToReorder
import com.example.scratch.printBlock.printBlock

var itemHeight = 0
var slotItemDifference = 0f

@ExperimentalAnimationApi
@Composable
fun visualBlock(
    index: Int,
    block: Blocks,
    slideState: SlideState,
    viewBlocks: MutableList<Blocks>,
    updateSlideState: (blockList: Blocks, slideState: SlideState) -> Unit,
    updateItemPosition: (currentIndex: Int, destinationIndex: Int) -> Unit
) {
    val itemHeightDp = 100.dp
    with(LocalDensity.current) {
        itemHeight = itemHeightDp.toPx().toInt()
        slotItemDifference = 16.dp.toPx()
    }
    val verticalTranslation by animateIntAsState(
        targetValue = when (slideState) {
            SlideState.UP -> -itemHeight
            SlideState.DOWN -> itemHeight
            else -> 0
        },
    )
    val isDragged = remember { mutableStateOf(false) }
    val zIndex = if (isDragged.value) 1.0f else 0.0f

    val currentIndex = remember { mutableStateOf(0) }
    val destinationIndex = remember { mutableStateOf(0) }

    val isPlaced = remember { mutableStateOf(false) }
    LaunchedEffect(isPlaced.value) {
        if (isPlaced.value) {
            if (currentIndex.value != destinationIndex.value) {
                updateItemPosition(currentIndex.value, destinationIndex.value)
            }
            isPlaced.value = false
        }
    }
    Box(
        modifier = Modifier
            .padding(10.dp)
            .dragToReorder(
                block,
                viewBlocks,
                itemHeight,
                updateSlideState,
                isDraggedAfterLongPress = true,
                { isDragged.value = true },
                { cIndex, dIndex ->
                    isDragged.value = false
                    isPlaced.value = true
                    currentIndex.value = cIndex
                    destinationIndex.value = dIndex
                }
            )
            .offset { IntOffset(0, verticalTranslation) }
            .zIndex(zIndex)
            .fillMaxWidth()
            .background(
                color = (Color(android.graphics.Color.parseColor(block.color))),
                shape = RoundedCornerShape(8.dp)
            ),
    ) {
        Row() {
            Box(modifier = Modifier.weight(10f))
            {
                when (block.blockType) {
                    "createVariable" -> textFieldWithMapValue(block)
                    "mathOperation" -> opsExpression(block)
                    "createPrint" -> printBlock(block)
                    "createConditions" -> conditions(block)
                    "createWhile" -> whileBlock(block)
                }
            }
            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red,
                ),
                onClick = {
                    viewBlocks.remove(block)
                    if(block.blockType == "createConditions"){
                        GlobalDataIf.blocksForConditions.clear()
                        GlobalDataElse.blocksForElseConditions.clear()
                    }
                },
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "",
                    tint = Color.White,
                )
            }
        }
    }
}
