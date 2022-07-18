package com.aveo.presentation.dialogs.change_admin_password

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aveo.presentation.common.AveoButton
import com.aveo.presentation.common.PasswordField
import com.aveo.presentation.di
import com.aveo.presentation.screens.home.HomeEvent
import com.aveo.presentation.screens.home.HomeViewModel
import org.kodein.di.instance

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChangeAdminPasswordDialog(
    homeViewModel: HomeViewModel
) {
    val viewModel: ChangeAdminPasswordViewModel by di.instance()

    val focusRequester = remember { FocusRequester() }
    val userName = homeViewModel.homeState.value.activeUser!!.userName

    val password by viewModel.password
    val passwordVisible by viewModel.passwordVisible
    val error by viewModel.error
    val isValid by viewModel.isValid

    AlertDialog(
        modifier = Modifier
            .size(250.dp, 250.dp)
            .border(width = 4.dp, color = Color.Gray),
        onDismissRequest = {},
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                AveoButton(
                    onClick = {
                        viewModel.onEvent(ChangeAdminPasswordEvent.LoginUser(userName))
                        homeViewModel.onEvent(HomeEvent.ShowChangeAdminPasswordDialog(false))
                    },
                    enabled = isValid,
                    text = "Change"
                )
            }
        },
        title = {
            Text("Change Admin Password")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PasswordField(
                    modifier = Modifier.focusRequester(focusRequester),
                    password,
                    onChange = { viewModel.onEvent(ChangeAdminPasswordEvent.PasswordChanged(it)) },
                    onSetVisible = { viewModel.onEvent(ChangeAdminPasswordEvent.SetVisible) },
                    passwordVisible = passwordVisible
                )

                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }

                if (error.isNotBlank()) {
                    Text(
                        text = error,
                        color = Color.Red
                    )
                }
            }
        }
    )
}