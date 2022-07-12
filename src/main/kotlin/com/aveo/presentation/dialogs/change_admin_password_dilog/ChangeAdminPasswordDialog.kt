package com.aveo.presentation.dialogs.change_admin_password_dilog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.aveo.presentation.screens.home.HomeEvent
import com.aveo.presentation.screens.home.HomeViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChangeAdminPasswordDialog(
    homeViewModel: HomeViewModel,
    viewModel: ChangeAdminPasswordViewModel
) {
    var password by viewModel.password
    var passwordVisible by viewModel.passwordVisible
    var isValid by viewModel.isValid

    val icon = if (passwordVisible) painterResource("drawable/visible.png") else
        painterResource("drawable/not_visible.png")

    AlertDialog(
        modifier = Modifier.size(250.dp, 250.dp),
        onDismissRequest = {},
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        homeViewModel.onEvent(HomeEvent.ShowChangeAdminPasswordDialog(false))
                    },
                    enabled = isValid
                ) {
                    Text("Change")
                }

                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = { }
                ) {
                    Text("Cancel")
                }
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
//                PasswordField(
//                    text = "${state.password}",
//                    onValue = { text -> state.password = text },
//                    onVisible = { state.passwordVisible = !state.passwordVisible},
//                    isVisible = state.passwordVisible
//                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { viewModel.onEvent(ChangePasswordEvent.PasswordChanged(it)) },
                    placeholder = { Text(text = "Password") },
                    label = { Text(text = "Password") },
                    trailingIcon = {
                        IconButton(
                            onClick = { viewModel.onEvent(ChangePasswordEvent.SetVisible) }
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
        }
    )
}