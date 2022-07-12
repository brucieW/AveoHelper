package com.aveo.presentation.dialogs.change_admin_password_dilog

sealed class ChangePasswordEvent {
    data class PasswordChanged(val password : String) : ChangePasswordEvent()
    object SetVisible : ChangePasswordEvent()
}
