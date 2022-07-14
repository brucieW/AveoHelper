package com.aveo.presentation.dialogs.change_password

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
import com.aveo.presentation.common.PasswordField
import com.aveo.presentation.di
import com.aveo.presentation.screens.home.HomeEvent
import com.aveo.presentation.screens.home.HomeViewModel
import org.kodein.di.instance

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChangePasswordDialog(
    homeViewModel: HomeViewModel
) {
    val viewModel: ChangePasswordViewModel by di.instance()
    val state by viewModel.state

    val focusRequester = remember { FocusRequester() }

    AlertDialog(
        modifier = Modifier
            .size(260.dp, 260.dp)
            .border(width = 4.dp, color = Color.Gray)
            .padding(bottom = 10.dp),
        onDismissRequest = {},
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        viewModel.onEvent(ChangePasswordEvent.ChangePassword)
                        homeViewModel.onEvent(HomeEvent.ShowChangePasswordDialog(false))
                    },
                    enabled = state.isValid
                ) {
                    Text("Change")
                }

                Spacer(modifier = Modifier.width(20.dp))

                Button(
                    onClick = {
                        homeViewModel.onEvent(HomeEvent.ShowChangePasswordDialog(false))
                    },
                ) {
                    Text("Cancel")
                }
            }
        },
        title = {
            Text("Change Current Password")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PasswordField(
                    modifier = Modifier.focusRequester(focusRequester),
                    state.currentPassword,
                    placeHolder = "Current Password",
                    addVisibleIcon = false,
                    onChange = { viewModel.onEvent(ChangePasswordEvent.CurrentPasswordChanged(it)) },
                    onSetVisible = { viewModel.onEvent(ChangePasswordEvent.SetCurrentPasswordVisible) },
                    passwordVisible = state.currentPasswordVisible
                )

                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }

                PasswordField(
                    value = state.newPassword,
                    placeHolder = "New Password",
                    onChange = { viewModel.onEvent(ChangePasswordEvent.NewPasswordChanged(it)) },
                    onSetVisible = { viewModel.onEvent(ChangePasswordEvent.SetNewPasswordVisible) },
                    passwordVisible = state.newPasswordVisible
                )

                if (state.error.isNotBlank()) {
                    Text(
                        text = state.error,
                        color = Color.Red
                    )
                }
            }
        }
    )
}