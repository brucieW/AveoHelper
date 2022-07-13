package com.aveo.presentation.screens.residents

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aveo.navcontroller.NavController
import com.aveo.presentation.screens.home.BackgroundImage
import kotlinx.coroutines.delay

@Composable
fun ResidentsScreen(
    navController: NavController
) {
    var currentImage by remember { mutableStateOf(1) }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(20000)
            currentImage = if (currentImage == 4) 1 else ++currentImage
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 70.dp),
    ) {
        Crossfade(
            targetState = currentImage.toString(),
            animationSpec = tween(4000)
        ) {
            BackgroundImage(it)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(navController.currentScreen.value)
        }
    }
}