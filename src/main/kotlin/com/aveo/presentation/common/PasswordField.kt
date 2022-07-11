package com.aveo.presentation.common

//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//
//@Composable
//fun PasswordField(
//    text: String,
//    onValue: (String) -> Unit,
//    onVisible: () -> Unit,
//    isVisible: Boolean
//) {
////    val icon = if (isVisible)
////        painterResource("drawable/visible.png")
////    else
////        painterResource("drawable/not_visible.png")
//
////    vaL icon = painterResource("drawable/not_visible.png")
//
//    OutlinedTextField(
//        value = text,
//        onValueChange = onValue(text),
//        placeholder = { Text(text = "Password") },
//        label = { Text(text = "Password") },
//        trailingIcon = {
//            IconButton(
//                onClick = { onVisible() }
//            ) {
//                Icon(
//                    painter = painterResource("drawable/not_visible.png"),
//                    contentDescription = "Visibility Icon"
//                )
//            }
//        },
//        singleLine = true,
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
//        maxLines = 1
//    )
//}