package com.aveo.presentation.dialogs.change_admin_password_dilog

import androidx.compose.runtime.*

class ChangeAdminPasswordViewModel() {
    var password = mutableStateOf("" )
    var passwordVisible = mutableStateOf(false)
    var isValid = mutableStateOf(false)

    fun onEvent(
        event : ChangePasswordEvent
    ) {
        when (event) {
            is ChangePasswordEvent.PasswordChanged -> {
                password.value = event.password
                isValid.value = !(password.value.isEmpty() || password.value == "admin")
            }

            is ChangePasswordEvent.SetVisible -> {
                passwordVisible.value = !passwordVisible.value
            }
        }
    }
}