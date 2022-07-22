package com.aveo.presentation.dialogs.login

import androidx.compose.runtime.mutableStateOf
import com.aveo.domain.repository.UserRepository
import com.aveo.presentation.screens.home.HomeEvent
import com.aveo.presentation.screens.home.HomeViewModel
import com.aveo.presentation.windowTitle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    val homeViewModel: HomeViewModel,
    val repository: UserRepository
) {
    private val _state = mutableStateOf(LoginState())
    val state = _state

    fun init() {
        if (!state.value.isLoggedIn) {
            CoroutineScope(Dispatchers.Main).launch {
                val user = repository.getLoggedInUser()

                if (user == null) {
                    val adminUser = repository.getUser("admin")
                    _state.value = state.value.copy(
                        showNormalLogin = true,
                        isFirstLogin = adminUser!!.password == "admin"
                    )
                } else if (user.userName == "admin" && user.password == "admin") {
                    _state.value = state.value.copy(
                        showChangeAdminPassword = true
                    )
                }
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
                CoroutineScope(Dispatchers.IO).launch {
                    val user = repository.getUser(state.value.userName)

                    if (user == null || user.password != state.value.password) {
                        _state.value = state.value.copy(
                            errorText = "Invalid user/password"
                        )
                    } else {
                        homeViewModel.onEvent(HomeEvent.LoginUser(user.userName, user.password))
                        clearState()
                    }
                }
            }

            is LoginEvent.LogoutUser -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val activeUser = state.value.activeUser

                    repository.insertUser(activeUser!!.userName, activeUser.password, false)
                    windowTitle.value = "Aveo Taringa"
                    clearState()
                }
            }
        }
    }

    private fun clearState() {
        _state.value = state.value.copy(
            activeUser = null,
            isLoggedIn = false,
            userName = "",
            password = "",
            showNormalLogin = false,
            showChangeAdminPassword = false
        )
    }

    private fun checkValid() {
        _state.value = state.value.copy(
            isValid = state.value.userName.isNotBlank() && state.value.password.isNotBlank()
        )
    }
}