package com.aveo.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aveo.presentation.common.AveoButton
import com.aveo.presentation.common.AveoPage
import com.aveo.presentation.dialogs.change_admin_password.ChangeAdminPasswordDialog
import com.aveo.presentation.dialogs.change_password.ChangePasswordDialog
import com.aveo.presentation.dialogs.login.LoginDialog
import com.aveo.presentation.dialogs.manage_users.ManageUsersDialog

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel
) {
    val homeState by homeViewModel.homeState

    AveoPage {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (homeState.activeUser == null) {
                LoginDialog()
            } else {
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

                if (homeState.showChangeAdminPasswordDialog) {
                    ChangeAdminPasswordDialog(homeViewModel)
                }

                if (homeState.showChangePasswordDialog) {
                    ChangePasswordDialog(homeViewModel)
                }

                if (homeState.showManageUsersDialog) {
                    ManageUsersDialog(homeViewModel)
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
