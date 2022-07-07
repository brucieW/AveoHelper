package com.aveo.di

import com.aveo.presentation.screens.home.HomeScreenComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent {
    fun inject(homeScreenComponent: HomeScreenComponent)
}