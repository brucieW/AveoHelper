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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChangeAdminPasswordDialog(
    viewModel: ChangeAdminPasswordViewModel
) {
    var password by viewModel.password
    var passwordVisible by viewModel.passwordVisible
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
                    onClick = { }
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
                    onValueChange = { password = it },
                    placeholder = { Text(text = "Password") },
                    label = { Text(text = "Password") },
                    trailingIcon = {
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible }
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