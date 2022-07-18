package com.aveo.presentation.screens.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aveo.presentation.common.AveoButton
import com.aveo.presentation.dialogs.change_admin_password.ChangeAdminPasswordDialog
import com.aveo.presentation.dialogs.change_password.ChangePasswordDialog
import com.aveo.presentation.dialogs.login.LoginDialog
import com.aveo.presentation.dialogs.manage_users.ManageUsersDialog
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel
) {
    val homeState by homeViewModel.homeState
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
            .padding(top = 40.dp),
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
            if (homeState.activeUser != null) {
                if (homeState.activeUser!!.userName == "admin") {
                    AveoButton(
                        onClick = { homeViewModel.onEvent(HomeEvent.ShowManageUsersDialog(true)) },
                        text = "Manage Users"
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                AveoButton(
                    onClick = { homeViewModel.onEvent(HomeEvent.ShowChangePasswordDialog(true)) },
                    text = "Change Password"
                )

                Spacer(modifier = Modifier.height(20.dp))

                AveoButton(
                    onClick = { homeViewModel.onEvent(HomeEvent.LogOut) },
                    text = "Log Out"
                )

                if (homeState.showChangePasswordDialog) {
                    ChangePasswordDialog(homeViewModel)
                }

                if (homeState.showChangeAdminPasswordDialog) {
                    ChangeAdminPasswordDialog(homeViewModel)
                }

                if (homeState.showManageUsersDialog) {
                    ManageUsersDialog(homeViewModel)
                }
            } else {
                LoginDialog(homeViewModel)
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
