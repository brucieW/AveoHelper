package com.aveo.presentation.screens.home

import androidx.compose.runtime.mutableStateOf
import com.aveo.db.User
import com.aveo.domain.repository.UserRepository
import com.aveo.util.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _homeState = mutableStateOf(HomeState())
    val homeState = _homeState

    val users = userRepository.getAllUsers()

    suspend fun getUser(userName: String) {
        coroutineScope {
            launch {
                _homeState.value = homeState.value.copy(
                    activeUser = userRepository.getUser(userName)
                )
            }
        }
    }

    fun isDefaultAdminUser() : Boolean {
        val user = homeState.value.activeUser

        return user != null && user.userName == "admin" && user.password == "admin"
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