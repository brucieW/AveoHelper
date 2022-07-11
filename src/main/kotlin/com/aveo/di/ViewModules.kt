package com.aveo.di

import com.aveo.presentation.dialogs.change_admin_password_dilog.ChangeAdminPasswordViewModel
import com.aveo.presentation.screens.home.HomeViewModel
import org.kodein.di.*

val viewModules = DI.Module("View Modules") {
    bind<HomeViewModel> { provider { HomeViewModel(instance()) } }
    bind<ChangeAdminPasswordViewModel> { provider { ChangeAdminPasswordViewModel(instance()) } }
}