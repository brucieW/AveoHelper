package com.aveo.presentation.dialogs.change_password

sealed class ChangePasswordEvent {
    data class CurrentPasswordChanged(val password : String) : ChangePasswordEvent()
    data class NewPasswordChanged(val password : String) : ChangePasswordEvent()
    object SetCurrentPasswordVisible : ChangePasswordEvent()
    object SetNewPasswordVisible : ChangePasswordEvent()
    object ChangePassword : ChangePasswordEvent()
}
