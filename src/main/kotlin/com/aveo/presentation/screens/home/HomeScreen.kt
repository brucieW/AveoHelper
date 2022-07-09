package com.aveo.presentation.screens.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
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
import androidx.compose.ui.window.Dialog
import com.aveo.navcontroller.NavController
import com.aveo.presentation.di
import com.aveo.presentation.dialogs.ChangeAdminPasswordDialog
import com.aveo.presentation.dialogs.ChangeAdminPasswordViewModel
import kotlinx.coroutines.delay
import org.kodein.di.instance

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    val homeState by homeViewModel.homeState
    var currentImage by remember { mutableStateOf(1) }
    val users = homeViewModel.users.collectAsState(initial = emptyList()).value

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
            if (homeState.isLoggedIn) {
                Text("Welcome ${homeState.activeUser?.userName}")
            } else {
                if (homeViewModel.isDefaultAdminUser()) {
                    val viewModel: ChangeAdminPasswordViewModel by di.instance()
//                    ChangeAdminPasswordDialog(viewModel)
                    Card(
                        elevation = 10.dp
                    ) {
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
                        } else if (homeState.activeUser != null) {
                            Text(
                                text = "Logged in as ${homeState.activeUser}",
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    color = Color(255, 255, 255),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 30.sp
                                )
                            )

                            Text(
                                text = "Users = $users",
                                Modifier.fillMaxWidth(),
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
