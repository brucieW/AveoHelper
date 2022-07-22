package com.aveo.presentation.screens.kitchen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aveo.presentation.common.AveoPage
import com.aveo.presentation.theme.Blue50

@Composable
fun KitchenScreen(
) {
    AveoPage {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
                .background(Blue50)
                .border(3.dp, Color.Gray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Kitchen Screen")
        }
    }
}