package com.aveo.presentation.screens.home

import androidx.compose.runtime.mutableStateOf
import com.aveo.db.User
import com.aveo.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository
) {
    private val _homeState = mutableStateOf(HomeState())
    val homeState = _homeState

    init {
        CoroutineScope(Dispatchers.IO).launch {
            setActiveUser(userRepository.getLoggedInUser())
        }
    }

    fun setActiveUser(user: User?) {
        _homeState.value = homeState.value.copy(
            activeUser = user
        )
    }

    fun getActiveUser() : User? {
        return homeState.value.activeUser
    }

    fun getActivePassword() : String {
        return homeState.value.activeUser!!.password
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ShowChangeAdminPasswordDialog -> {
                _homeState.value = homeState.value.copy(
                    showChangeAdminPasswordDialog = event.show
                )
            }

            is HomeEvent.ChangeAdminPassword -> {
                _homeState.value = homeState.value.copy(
                    adminPassword = event.password
                )
            }

            is HomeEvent.SaveAdminPassword -> {
                CoroutineScope(Dispatchers.IO).launch {
                    userRepository.insertUser("admin", event.password, true)
                    onEvent(HomeEvent.ShowChangeAdminPasswordDialog(false))
                }
            }

            is HomeEvent.ShowChangePasswordDialog -> {
                if (!event.show) {
                    CoroutineScope(Dispatchers.IO).launch {
                        setActiveUser(userRepository.getLoggedInUser())
                    }
                }

                _homeState.value = homeState.value.copy(
                    showChangePasswordDialog = event.show
                )
            }

            is HomeEvent.ShowManageUsersDialog -> {
                _homeState.value = homeState.value.copy(
                    showManageUsersDialog = event.show
                )
            }

            is HomeEvent.LogOut -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val user = userRepository.getLoggedInUser()

                    if (user != null && user.loggedIn == true) {
                        userRepository.deleteLoggedInUser(user.userName)
                        setActiveUser(null)
                    }
                }
            }

            is HomeEvent.LoginUser -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val user = userRepository.getLoggedInUser()
                    setActiveUser(user)

                    if (user!!.userName == "admin" && user.password == "admin") {
                        onEvent(HomeEvent.ShowChangeAdminPasswordDialog(true))
                    } else if (user.password == "password") {
                        onEvent(HomeEvent.ShowChangePasswordDialog(true))
                    }
                }
            }
        }
    }
}