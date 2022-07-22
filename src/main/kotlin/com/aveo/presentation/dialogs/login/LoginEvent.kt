package com.aveo.presentation.dialogs.login

sealed class LoginEvent {
    data class ChangeUserName(val userName: String) : LoginEvent()
    data class ChangePassword(val password: String) : LoginEvent()
    object LoginUser : LoginEvent()
    object LogoutUser : LoginEvent()
    object SetPasswordVisible : LoginEvent()
    data class ShowLoginDialog(val show: Boolean) : LoginEvent()
}