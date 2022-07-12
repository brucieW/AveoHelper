package com.aveo.presentation.dialogs.login

sealed class LoginEvent {
    data class ChangeUserName(val userName: String) : LoginEvent()
    data class ChangePassword(val password: String) : LoginEvent()
    data class SavePassword(val userName: String, val password: String) : LoginEvent()
    object SetPasswordVisible : LoginEvent()
    data class ShowLoginDialog(val show: Boolean) : LoginEvent()
}