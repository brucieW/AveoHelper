package com.aveo.presentation.dialogs.change_password

import com.aveo.db.User

data class ChangePasswordState(
    var activeUser: User? = null,
    var currentPassword: String = "",
    var currentPasswordVisible: Boolean = false,
    var newPassword: String = "",
    var newPasswordVisible: Boolean = false,
    var error: String = "",
    var isValid: Boolean = false
)
