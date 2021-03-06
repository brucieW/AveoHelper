package com.aveo.presentation.dialogs.manage_users

import androidx.compose.runtime.mutableStateOf
import com.aveo.db.User
import com.aveo.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ManageUsersViewModel(
    val repository: UserRepository
) {
    var selectedUser = mutableStateOf<User?>(null)
    var newUserName = mutableStateOf("")
    var newUserEnabled = mutableStateOf(false)

    var showAddUserEdit = mutableStateOf(false)

    val users = repository.getAllUsers()

    fun setSelectedUser(user: User?) {
        selectedUser.value = user
    }

    fun showAddUserEdit(show: Boolean) {
        showAddUserEdit.value = show
        newUserName.value = ""
        newUserEnabled.value = false
    }

    fun onNewUserNameChange(name: String) {
        newUserName.value = name
    }

    fun setNewUserEnabled(enabled: Boolean) {
        newUserEnabled.value = enabled
    }

    fun addNewUser(userName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertUser(userName, "password", false)
            showAddUserEdit(false)
        }
    }

    fun deleteSelectedUser() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteUser(selectedUser.value!!.userName)
        }
    }

    fun resetPassword() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertUser(selectedUser.value!!.userName, "password",
                selectedUser.value!!.loggedIn == true
            )
        }
    }
}