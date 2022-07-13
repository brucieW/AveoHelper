package com.aveo.presentation.dialogs.change_admin_password_dialog

import androidx.compose.runtime.*
import com.aveo.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangeAdminPasswordViewModel(
    userRepository: UserRepository
) {
    private val repository = userRepository

    var password = mutableStateOf("")
    var passwordVisible = mutableStateOf(false)
    var error = mutableStateOf("")
    var isValid = mutableStateOf(false)

    fun onEvent(
        event: ChangePasswordEvent
    ) {
        when (event) {
            is ChangePasswordEvent.PasswordChanged -> {
                password.value = event.password
                isValid.value = !(password.value.isEmpty() || password.value == "admin")

                error.value = if (password.value == "admin") "Password must be different" else ""
            }

            is ChangePasswordEvent.SetVisible -> {
                passwordVisible.value = !passwordVisible.value
            }

            is ChangePasswordEvent.LoginUser -> {
                CoroutineScope(Dispatchers.Main).launch {
                    repository.insertUser(event.userName, password.value, 1)
                }
            }
        }
    }
}
