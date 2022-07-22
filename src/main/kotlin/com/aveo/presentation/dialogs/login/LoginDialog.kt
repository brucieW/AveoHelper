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
import com.aveo.presentation.common.AveoButton
import com.aveo.presentation.common.NormalField
import com.aveo.presentation.common.PasswordField
import com.aveo.presentation.di
import com.aveo.presentation.windowTitle
import org.kodein.di.instance

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginDialog() {
    val viewModel: LoginViewModel by di.instance()
    val state by viewModel.state
    val focusRequester = remember { FocusRequester() }

    viewModel.init()

    AlertDialog(
        modifier = Modifier
            .size(250.dp, 270.dp)
            .border(width = 4.dp, color = Color.Gray),
        onDismissRequest = {},
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                AveoButton(
                    onClick = {
                        viewModel.onEvent(LoginEvent.LoginUser)
                        windowTitle.value = "Aveo Taringa - ${state.userName}"
                    },
                    enabled = state.isValid,
                    text = "Login"
                )
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
