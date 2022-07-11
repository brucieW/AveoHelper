package com.aveo.presentation.dialogs.change_admin_password_dilog

import androidx.compose.runtime.*
import com.aveo.domain.repository.UserRepository
import com.aveo.util.ViewModel

class ChangeAdminPasswordViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    var password = mutableStateOf("" )
    var passwordVisible = mutableStateOf(false)
}