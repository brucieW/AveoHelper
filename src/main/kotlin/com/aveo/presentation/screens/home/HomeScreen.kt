package com.aveo.presentation.screens.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aveo.navcontroller.NavController
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    var currentImage by remember { mutableStateOf(1) }
    var users = homeViewModel.users.collectAsState(initial = emptyList()).value

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(10000)
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (homeViewModel.isLoggedIn) {
                Text("Logged in as ${homeViewModel.activeUser}")
            } else {
                if (users.isEmpty()) {
                    Text(
                        text = "Please Log In",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color(255, 255, 255),
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp
                        )

                    )
                } else {
                    Text(
                        text = "Logged in as $users[0]",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color(255, 255, 255),
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp
                        )

                    )
                }
            }
        }
    }
}

@Composable
fun BackgroundImage(
    imageId: String
) {
    Image(
        painter = painterResource("drawable/Taringa$imageId.jpg"),
        contentDescription = "",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.fillMaxSize()
    )
}
