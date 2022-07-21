package com.aveo.presentation

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import java.io.Serializable

data class Preferences(
    var placement: WindowPlacement = WindowPlacement.Floating,
    var xPosition: Dp = 0.dp,
    var yPosition: Dp = 0.dp,
    var size: DpSize = DpSize(800.dp, 600.dp)
) : Serializable
