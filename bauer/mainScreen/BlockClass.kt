package com.example.scratch.mainScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

data class Blocks(
    var firstValue: String,
    var secondValue: String,
    val blockID: Int,
    val color: String,
    val expression: MutableState<String>,
    val blockType: String
)

