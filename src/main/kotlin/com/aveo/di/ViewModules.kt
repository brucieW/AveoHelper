package com.aveo.di

import com.aveo.presentation.dialogs.change_admin_password.ChangeAdminPasswordViewModel
import com.aveo.presentation.dialogs.change_password.ChangePasswordViewModel
import com.aveo.presentation.dialogs.login.LoginViewModel
import com.aveo.presentation.dialogs.manage_users.ManageUsersViewModel
import com.aveo.presentation.screens.home.HomeViewModel
import com.aveo.presentation.screens.residents.ResidentsViewModel
import org.kodein.di.*

val viewModules = DI.Module("View Modules") {
    bind<HomeViewModel> { singleton { HomeViewModel(instance()) } }
    bind<ResidentsViewModel> { singleton { ResidentsViewModel(instance()) } }
    bind<LoginViewModel> { singleton { LoginViewModel(instance(), instance()) } }
    bind<ChangeAdminPasswordViewModel> { singleton { ChangeAdminPasswordViewModel(instance()) } }
    bind<ChangePasswordViewModel> { singleton { ChangePasswordViewModel(instance(), instance()) } }
    bind<ManageUsersViewModel> { singleton { ManageUsersViewModel(instance()) } }
}