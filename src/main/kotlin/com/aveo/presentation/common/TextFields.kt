package com.aveo.presentation.common

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun NormalField(
    modifier: Modifier = Modifier,
    value: String,
    placeHolder: String,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
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
    modifier: Modifier = Modifier,
    value: String,
    placeHolder: String = "Password",
    addVisibleIcon: Boolean = true,
    onChange: (String) -> Unit,
    onSetVisible: () -> Unit,
    passwordVisible: Boolean
) {
    val icon = if (passwordVisible) painterResource("drawable/visible.png") else
        painterResource("drawable/not_visible.png")

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onChange(it) },
        placeholder = { Text(text = placeHolder) },
        label = { Text(text = placeHolder) },
        trailingIcon = {
            if (addVisibleIcon) {
                IconButton(
                    onClick = { onSetVisible() }
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = "Visibility Icon"
                    )
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        maxLines = 1
    )
}
