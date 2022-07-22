package com.aveo.presentation.screens.home

import androidx.compose.runtime.mutableStateOf
import com.aveo.db.User
import com.aveo.domain.repository.UserRepository
import com.aveo.presentation.windowTitle
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
                    showChangeAdminPasswordDialog = event.show,
                    showLoginDialog = false
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
                _homeState.value = homeState.value.copy(
                    showChangePasswordDialog = event.show,
                    showLoginDialog = false
                )
            }

            is HomeEvent.ShowManageUsersDialog -> {
                _homeState.value = homeState.value.copy(
                    showManageUsersDialog = event.show
                )
            }

            is HomeEvent.LogOut -> {
                windowTitle.value = "Aveo Taringa"

                CoroutineScope(Dispatchers.IO).launch {
                    val user = userRepository.getLoggedInUser()

                    if (user != null && user.loggedIn == true) {
                        userRepository.deleteLoggedInUser(user.userName)
                        setActiveUser(null)
                    }
                }
            }

            is HomeEvent.ShowLoginDialog -> {
                _homeState.value = homeState.value.copy(
                    showLoginDialog = event.show
                )
            }

            is HomeEvent.LogIn -> {
                _homeState.value = homeState.value.copy(
                    showLoginDialog = false
                )
            }

            is HomeEvent.LoginUser -> {
                CoroutineScope(Dispatchers.IO).launch {
                    userRepository.insertUser(event.userName, event.password, true)
                    val user = userRepository.getLoggedInUser()
                    setActiveUser(user)

                    if (event.userName == "admin" && event.password == "admin") {
                        onEvent(HomeEvent.ShowChangeAdminPasswordDialog(true))
                    } else if (event.password == "password") {
                        onEvent(HomeEvent.ShowChangePasswordDialog(true))
                    }
                }
            }
        }
    }
}