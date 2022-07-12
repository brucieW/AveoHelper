package com.aveo.di

import com.aveo.presentation.dialogs.login.LoginViewModel
import com.aveo.presentation.screens.home.HomeViewModel
import org.kodein.di.*

val viewModules = DI.Module("View Modules") {
    bind<HomeViewModel> { singleton { HomeViewModel(instance()) } }
    bind<LoginViewModel> { singleton { LoginViewModel(instance()) } }
}