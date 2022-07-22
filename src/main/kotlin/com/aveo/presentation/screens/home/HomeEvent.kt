package com.aveo.presentation.screens.home

sealed class HomeEvent {
    data class ShowChangeAdminPasswordDialog(val show: Boolean) : HomeEvent()
    data class ChangeAdminPassword(val password: String) : HomeEvent()
    data class SaveAdminPassword(val password: String) : HomeEvent()
    data class ShowChangePasswordDialog(val show: Boolean) : HomeEvent()
    data class ShowManageUsersDialog(val show: Boolean) : HomeEvent()
    data class ShowLoginDialog(val show: Boolean) : HomeEvent()
    data class LoginUser(val userName: String, val password: String) : HomeEvent()
    object LogIn : HomeEvent()
    object LogOut : HomeEvent()
}