package com.aveo.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun App() {
    val screens = Screen.values().toList()
    val navController by rememberNavController(Screen.HomeScreen.name)
    val currentScreen by remember { navController.currentScreen }
    var showAbout by remember { mutableStateOf(false) }
    var showPopup by remember { mutableStateOf(false) }
    var popupText by remember { mutableStateOf("") }
    var popupPosition by remember { mutableStateOf(IntOffset(0, 0)) }

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
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .height(40.dp),
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
                                    .padding(end = 20.dp)
                                    .onPointerEvent(PointerEventType.Enter) {

                                        // The native event will look like this:
                                        //     java.awt.event.MouseEvent[MOUSE_MOVED,(318,30),absolute(886,301),clickCount=0] on canvas0
                                        // so split will reveal item 3 as '(318' and item  as '30)')
                                        val event = it.nativeEvent.toString()

//                                        Arbor.d("event: $event)")
//                                        Arbor.d("position = ${it.changes.size}, ${it.changes.last().position}")

                                        val items = event.split(",")
                                        val x = items[1].substring(1).toInt() - 20
                                        val yItems = items[2].split(")")
                                        val y = yItems[0].toInt() + 20
                                        popupPosition = IntOffset(x, y)

//                                        Arbor.d("Popup Position = ${popupPosition}")

                                        showPopup = true
                                        popupText = screen.label
                                    }
                                    .onPointerEvent(PointerEventType.Exit) {
                                        showPopup = false
                                        popupPosition = IntOffset(0,0)
                                        val position = it.changes.last().position
                                    },
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

                if (showPopup) {
                   Popup(
                        offset = popupPosition
                    ) {
                        Text(popupText, color = Color.White)
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