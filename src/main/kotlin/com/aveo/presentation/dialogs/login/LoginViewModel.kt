package com.aveo.presentation.dialogs.login

import androidx.compose.runtime.mutableStateOf
import com.aveo.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    userRepository: UserRepository
) {
    private val _state = mutableStateOf(LoginState())
    val state = _state

    private val repository = userRepository

    fun init() {
        CoroutineScope(Dispatchers.Main).launch {
            val user = repository.getLoggedInUser()

            if (user == null) {
                _state.value = state.value.copy(
                    showNormalLogin = true
                )
            } else if (user.userName == "admin" && user.password == "admin") {
                _state.value = state.value.copy(
                    showChangeAdminPassword = true
                )
            }
        }
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.ShowLoginDialog -> {
                _state.value = state.value.copy(
                    showNormalLogin = event.show
                )
            }

            is LoginEvent.ChangeUserName -> {
                _state.value = state.value.copy(
                    userName = event.userName,
                    errorText = ""
                )

                checkValid()
            }

            is LoginEvent.ChangePassword -> {
                _state.value = state.value.copy(
                    password = event.password,
                    errorText = ""
                )

                checkValid()
            }

            is LoginEvent.SetPasswordVisible -> {
                _state.value = state.value.copy(
                    passwordVisible = !_state.value.passwordVisible
                )
            }

            is LoginEvent.LoginUser -> {
                CoroutineScope(Dispatchers.Main).launch {
                    val user = repository.getUser(event.userName)

                    if (user != null && user.userName == state.value.userName &&
                                                user.password == state.value.password) {
                        repository.setLoggedInUser(user.userName)
                        _state.value = state.value.copy(
                            userName = "",
                            password = "",
                            showNormalLogin = false
                        )
                    } else {
                        _state.value = state.value.copy(
                            errorText = "User name/password is invalid"
                        )
                    }
                }
            }
        }
    }

    private fun checkValid() {
        val isValid = state.value.userName.isNotBlank() &&
                        state.value.password.isNotBlank()
        _state.value = state.value.copy(
            isValid = isValid
        )
    }
}