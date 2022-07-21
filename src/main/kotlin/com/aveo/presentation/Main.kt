package com.aveo.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.System.exit

@OptIn(ExperimentalComposeUiApi::class)
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
                modifier = Modifier.fillMaxSize()
            ) {
                NavigationRail(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .height(40.dp)
                        .border(3.dp, Color.DarkGray)
                    ,
                    backgroundColor = Color.LightGray,
                    elevation = 10.dp
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        screens.forEach { screen ->
                            NavigationRailItem(
                                modifier = Modifier
                                    .padding(end = 20.dp),
                                selected = currentScreen == screen.name,
                                icon = {
                                    Icon(
                                       imageVector = screen.icon,
                                        contentDescription = screen.label
                                    )
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
    val file = "c:/AveoDatabase/preferences.json"
    var preferences = Preferences()

    try {
        ObjectInputStream(FileInputStream(file)).use { stream ->
            val item = stream.readObject()

            when (item) {
                is Preferences -> preferences = item
            }
        }
    } catch (_: IOException) {

    }

    val state = rememberWindowState(
        placement = preferences.placement,
        position = WindowPosition.Absolute(preferences.xPosition, preferences.yPosition),
        size = preferences.size
    )

    Window(
        title = "Aveo Taringa",
        resizable = true,
        state = state,
        icon = painterResource("drawable/logo.png"),
        onCloseRequest = {
            preferences.placement = state.placement
            preferences.xPosition = state.position.x
            preferences.yPosition = state.position.y
            preferences.size = state.size
            ObjectOutputStream(FileOutputStream(file)).use { it -> it.writeObject(preferences) }
            exit(0)
        }
    ) {
//        Arbor.sow(Seedling())
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