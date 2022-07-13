package com.aveo.presentation.dialogs.login

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aveo.presentation.common.NormalField
import com.aveo.presentation.common.PasswordField
import com.aveo.presentation.di
import com.aveo.presentation.screens.home.HomeEvent
import com.aveo.presentation.screens.home.HomeViewModel
import org.kodein.di.instance

@Composable
fun LoginDialog(
    homeViewModel: HomeViewModel,
) {
    val loginViewModel: LoginViewModel by di.instance()
    val state by loginViewModel.state

    loginViewModel.init()

    NormalLogin(
        show = state.showNormalLogin && !state.loginShutdown,
        homeViewModel,
        loginViewModel)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NormalLogin(
    show: Boolean,
    homeViewModel: HomeViewModel,
    viewModel: LoginViewModel
) {
    if (show) {
        val state by viewModel.state
        val focusRequester = remember { FocusRequester() }

        AlertDialog(
            modifier = Modifier
                .size(250.dp, 270.dp)
                .border(width = 4.dp, color = Color.Gray),
            onDismissRequest = {},
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                    ,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            viewModel.onEvent(LoginEvent.LoginUser(state.userName, state.password))
                            homeViewModel.onEvent(HomeEvent.LoginUser(state.userName))
                        },
                        enabled = state.isValid
                    ) {
                        Text("Login")
                    }
                }
            },
            title = {
                Text("Please Login")
            },
            text = {
                Column(
                    modifier = Modifier.padding(top = 5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NormalField(
                        modifier = Modifier.focusRequester(focusRequester),
                        state.userName,
                        "User Name",
                        onChange = { viewModel.onEvent(LoginEvent.ChangeUserName(it)) }
                    )

                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }

                    PasswordField(
                        value = state.password,
                        onChange = { viewModel.onEvent(LoginEvent.ChangePassword(it)) },
                        onSetVisible = { viewModel.onEvent(LoginEvent.SetPasswordVisible) },
                        passwordVisible = state.passwordVisible
                    )

                    if (state.errorText.isNotBlank()) {
                        Text(
                            text = state.errorText,
                            color = Color.Red
                        )
                    }
                }
            }
        )
    }
}

