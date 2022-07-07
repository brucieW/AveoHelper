package com.aveo.presentation.screens.home

import com.aveo.di.AppComponent
import javax.inject.Inject

class HomeScreenComponent(
    appComponent: AppComponent,
) {
    @Inject
    lateinit var viewModel: HomeViewModel

    init {
        appComponent.inject(this)
    }
}
