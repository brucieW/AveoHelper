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

    var showAddUserEdit = mutableStateOf(false)

    val users = repository.getAllUsers()

    fun setSelectedUser(user: User?) {
        selectedUser.value = user
    }

    fun showAddUserEdit(show: Boolean) {
        showAddUserEdit.value = show
    }

    fun onNewUserNameChange(name: String) {
        newUserName.value = name
    }

    fun addNewUser(userName: String) {
        CoroutineScope(Dispatchers.Main).launch {
            repository.insertUser(userName, "password", false)
            showAddUserEdit(false)
        }
    }

    fun deleteSelectedUser() {
        CoroutineScope(Dispatchers.Main).launch {
            repository.deleteUser(selectedUser.value!!.userName)
        }
    }

    fun resetPassword() {
        CoroutineScope(Dispatchers.Main).launch {
            repository.insertUser(selectedUser.value!!.userName, "password",
                selectedUser.value!!.loggedIn == true
            )
        }
    }
}