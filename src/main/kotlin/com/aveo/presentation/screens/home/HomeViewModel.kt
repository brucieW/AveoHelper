package com.aveo.presentation.screens.home

import androidx.compose.runtime.mutableStateOf
import com.aveo.db.User
import com.aveo.domain.repository.UserRepository
import com.aveo.util.ViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _homeState = mutableStateOf(HomeState())
    val homeState = _homeState

    val users = userRepository.getAllUsers()

    fun isDefaultAdminUser(): Boolean {
        var user: User? = null

        runBlocking {
            launch {
                user = userRepository.getUser("admin")
            }
        }

        return (user != null && user!!.userName == "admin" && user!!.password == "admin")
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
                    adminPassord = event.password
                )
            }
        }
    }
}