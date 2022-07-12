package com.aveo.presentation.dialogs.login

import com.aveo.db.User

data class LoginState(
    val showChangeAdminPassword: Boolean = false,
    val showNormalLogin: Boolean = false,
    val loginShutdown: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isValid: Boolean = false,
    val activeUser: User? = null,
    val userName: String = "",
    val password: String = "",
    val errorText: String = "",
    val passwordVisible: Boolean = false
)