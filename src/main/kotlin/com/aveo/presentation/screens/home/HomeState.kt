package com.aveo.presentation.screens.home

import com.aveo.db.User

data class HomeState(
    var isLoggedIn: Boolean = false,
    var activeUser: User? = null,
    var showChangeAdminPasswordDialog: Boolean = false,
    var showChangePasswordDialog: Boolean = false,
    var showMangePasswordsDialog: Boolean = false,
    var adminPassword: String = "",
    var isDefaultAdminUser: Boolean = false
)