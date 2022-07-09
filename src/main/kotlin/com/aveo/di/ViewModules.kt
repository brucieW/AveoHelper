package com.aveo.di

import com.aveo.presentation.dialogs.ChangeAdminPasswordViewModel
import com.aveo.presentation.screens.home.HomeViewModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val viewModules = DI.Module("View Modules") {
    bind<HomeViewModel> { singleton { HomeViewModel(instance()) } }
    bind<ChangeAdminPasswordViewModel> { singleton { ChangeAdminPasswordViewModel(instance()) } }
}