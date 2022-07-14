package com.aveo.presentation.dialogs.change_admin_password

sealed class ChangeAdminPasswordEvent {
    data class PasswordChanged(val password : String) : ChangeAdminPasswordEvent()
    object SetVisible : ChangeAdminPasswordEvent()
    data class LoginUser(val userName: String) : ChangeAdminPasswordEvent()
}
