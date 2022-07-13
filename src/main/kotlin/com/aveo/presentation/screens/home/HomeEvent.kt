package com.aveo.presentation.screens.home

sealed class HomeEvent {
    data class ShowChangeAdminPasswordDialog(val show: Boolean) : HomeEvent()
    data class ChangeAdminPassword(val password: String) : HomeEvent()
    data class SaveAdminPassword(val password: String) : HomeEvent()
    data class LoginUser(val userName: String) : HomeEvent()
    object LogOut : HomeEvent()
}