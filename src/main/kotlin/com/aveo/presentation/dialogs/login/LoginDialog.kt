package com.aveo.presentation.dialogs.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.aveo.presentation.screens.home.HomeViewModel

@Composable
fun LoginDialog(
    homeViewModel: HomeViewModel,
    loginViewModel: LoginViewModel
) {
    val state by loginViewModel.state

    loginViewModel.init()
    NormalLogin(state.showNormalLogin && !state.loginShutdown, loginViewModel)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NormalLogin(
    show: Boolean,
    viewModel: LoginViewModel
) {
    if (show) {
        val state by viewModel.state

        AlertDialog(
            modifier = Modifier.size(250.dp, 270.dp),
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
                            viewModel.onEvent(LoginEvent.SavePassword(state.userName, state.password))
                        },
                        enabled = state.isValid
                    ) {
                        Text("Login")
                    }

                    Spacer(modifier = Modifier.width(20.dp))
                    Button(
                        onClick = { viewModel.onEvent(LoginEvent.ShowLoginDialog(false)) }
                    ) {
                        Text("Cancel")
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
                        state.userName,
                        "User Name",
                        onChange = { viewModel.onEvent(LoginEvent.ChangeUserName(it)) }
                    )

                    PasswordField(
                        state.password,
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

@Composable
fun NormalField(
    value: String,
    placeHolder: String,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onChange(it) },
        placeholder = { Text(text = placeHolder) },
        label = { Text(text = placeHolder) },
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun PasswordField(
    value: String,
    onChange: (String) -> Unit,
    onSetVisible: () -> Unit,
    passwordVisible: Boolean
) {
    val icon = if (passwordVisible) painterResource("drawable/visible.png") else
        painterResource("drawable/not_visible.png")

    OutlinedTextField(
        value = value,
        onValueChange = { onChange(it) },
        placeholder = { Text(text = "Password") },
        label = { Text(text = "Password") },
        trailingIcon = {
            IconButton(
                onClick = { onSetVisible() }
            ) {
                Icon(
                    painter = icon,
                    contentDescription = "Visibility Icon"
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        maxLines = 1
    )
}
