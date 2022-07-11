package com.aveo.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.aveo.di.repositoriesModule
import com.aveo.di.viewModules
import com.aveo.navcontroller.*
import com.aveo.presentation.common.Screen
import com.aveo.presentation.dialogs.AboutDialog
import com.aveo.presentation.screens.home.HomeScreen
import com.aveo.presentation.screens.home.HomeViewModel
import com.aveo.presentation.screens.kitchen.KitchenScreen
import com.aveo.ui.screens.ResidentsScreen
import org.kodein.di.DI
import org.kodein.di.*

@Composable
fun App() {
    val screens = Screen.values().toList()
    val navController by rememberNavController(Screen.HomeScreen.name)
    val currentScreen by remember {
        navController.currentScreen
    }
    var showAbout by remember { mutableStateOf(false) }

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
        placement = WindowPlacement.Floating,
        position = WindowPosition(Alignment.Center),
        isMinimized = false,
        width = 800.dp,
        height = 600.dp
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
            val viewModel: HomeViewModel by di.instance()
            val scope = rememberCoroutineScope()
            HomeScreen(navController, viewModel)
            viewModel.init(scope)
        }

        composable(Screen.ResidentsScreen.name) {
            ResidentsScreen(navController)
        }

        composable(Screen.KitchenScreen.name) {
            KitchenScreen(navController)
        }

        composable(Screen.AdminScreen.name) {
//            Screen.AdminScreen(navController)
        }

        composable(Screen.AboutDialog.name) {
        }


    }.build()
}