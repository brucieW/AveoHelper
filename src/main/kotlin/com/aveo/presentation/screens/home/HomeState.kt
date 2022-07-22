package com.aveo.presentation.screens.home

import com.aveo.db.User

data class HomeState(
    var activeUser: User? = null,
    var showChangeAdminPasswordDialog: Boolean = false,
    var showChangePasswordDialog: Boolean = false,
    var showManageUsersDialog: Boolean = false,
    var showLoginDialog: Boolean = false,
    var adminPassword: String = "",
    var isDefaultAdminUser: Boolean = false
)