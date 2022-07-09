package com.aveo.presentation.screens.home

import com.aveo.db.User

data class HomeState(
    var isLoggedIn: Boolean = false,
    var activeUser: User? = null,
    var showChangeAdminPasswordDialog: Boolean = false,
    var adminPassord: String = ""
)