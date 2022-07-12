package com.aveo.presentation.screens.home

import androidx.compose.runtime.mutableStateOf
import com.aveo.domain.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository
) {
    private val _homeState = mutableStateOf(HomeState())
    val homeState = _homeState

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

            is HomeEvent.SaveAdminPassword -> {
                GlobalScope.launch {
                    userRepository.insertUser("admin", event.password)
                    onEvent(HomeEvent.ShowChangeAdminPasswordDialog(false))
                }
            }
        }
    }
}