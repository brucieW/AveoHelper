package com.aveo.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.*
import com.aveo.di.repositoriesModule
import com.aveo.di.viewModules
import com.aveo.navcontroller.*
import com.aveo.presentation.common.Screen
import com.aveo.presentation.common.AboutDialog
import com.aveo.presentation.screens.home.HomeScreen
import com.aveo.presentation.screens.home.HomeViewModel
import com.aveo.presentation.screens.kitchen.KitchenScreen
import com.aveo.presentation.screens.residents.ResidentsScreen
import com.aveo.presentation.screens.residents.ResidentsViewModel
import org.kodein.di.DI
import org.kodein.di.*

@Composable
fun App() {
    val screens = Screen.values().toList()
    val navController by rememberNavController(Screen.HomeScreen.name)
    val currentScreen by remember { navController.currentScreen }
    var showAbout by remember { mutableStateOf(false) }

    // TODO: On startup, make sure no-one is already logged in.
//    val userRepository: UserRepository by di.instance()
//
//    CoroutineScope(Dispatchers.Main).launch {
//        val user = userRepository.getLoggedInUser()
//
//        if (user!!.loggedIn == true) {
//            userRepository.insertUser(user.userName, user.password, false)
//        }
//    }

    MaterialTheme {
        Surface(
            modifier = Modifier.background(color = MaterialTheme.colors.background)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                NavigationRail(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .fillMaxHeight(),
                    backgroundColor = Color.LightGray
                ) {
                    screens.forEach { screen ->
                        NavigationRailItem(
                            selected = currentScreen == screen.name,
                            icon = {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = screen.label
                                )
                            },
                            label = {
                                Text(screen.label)
                            },
                            alwaysShowLabel = false,
                            onClick = {
                                if (screen.label == "About") {
                                    showAbout = true
                                } else {
                                    navController.navigate(screen.name)
                                }
                            }
                        )
                    }
                }

                if (showAbout) {
                    AboutDialog(onClose = { showAbout = false })
                }

                Box(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    CustomNavigationHost(navController = navController)
                }
            }
        }
    }
}

fun main() = application {
    val state = rememberWindowState(
        position = WindowPosition(Alignment.Center)
    )

    Window(
        title = "Aveo Taringa",
        resizable = true,
        state = state,
        icon = painterResource("drawable/logo.png"),
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}

val di = DI {
    import(repositoriesModule)
    import(viewModules)
}

@Composable
fun CustomNavigationHost(
    navController: NavController
) {
    NavigationHost(navController) {
        composable(Screen.HomeScreen.name) {
            val homeViewModel: HomeViewModel by di.instance()
            HomeScreen(homeViewModel)
        }

        composable(Screen.ResidentsScreen.name) {
            val residentsViewModel: ResidentsViewModel by di.instance()
            ResidentsScreen(residentsViewModel)
        }

        composable(Screen.KitchenScreen.name) {
            KitchenScreen()
        }

        composable(Screen.AboutDialog.name) {
        }


    }.build()
}