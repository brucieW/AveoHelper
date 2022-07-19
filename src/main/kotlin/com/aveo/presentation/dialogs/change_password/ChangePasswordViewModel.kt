package com.aveo.presentation.dialogs.change_password

import androidx.compose.runtime.*
import com.aveo.domain.repository.UserRepository
import com.aveo.presentation.screens.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangePasswordViewModel(
    viewModel: HomeViewModel,
    userRepository: UserRepository
) {
    private val repository = userRepository
    private val homeViewModel = viewModel

    private val _state = mutableStateOf(ChangePasswordState())
    val state = _state

    fun onEvent(
        event: ChangePasswordEvent
    ) {

        when (event) {
            is ChangePasswordEvent.CurrentPasswordChanged -> {
                val error = if (event.password != homeViewModel.getActivePassword()) "Current must match existing" else ""
                _state.value = state.value.copy(
                    currentPassword = event.password,
                    isValid = event.password.isNotEmpty() && error.isEmpty(),
                    error = error)
            }

            is ChangePasswordEvent.NewPasswordChanged -> {
                val error = if (event.password == homeViewModel.getActivePassword() || event.password == "admin") "Invalid new password" else ""
                _state.value = state.value.copy(
                    newPassword = event.password,
                    isValid = event.password.isNotEmpty() && error.isEmpty(),
                    error = error)
            }

            is ChangePasswordEvent.SetCurrentPasswordVisible -> {
                _state.value = state.value.copy(
                    currentPasswordVisible = !state.value.currentPasswordVisible
                )
            }

            is ChangePasswordEvent.SetNewPasswordVisible -> {
                _state.value = state.value.copy(
                    newPasswordVisible = !state.value.newPasswordVisible
                )
            }

            is ChangePasswordEvent.ChangePassword -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val user = homeViewModel.getActiveUser()
                    repository.insertUser(user!!.userName, state.value.newPassword, user.loggedIn == true)
                    state.value = state.value.copy(
                        currentPassword = "",
                        newPassword = ""
                    )
                }
            }
        }
    }
}
