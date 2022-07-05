package com.aveo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.aveo.navcontroller.NavController
import com.aveo.navcontroller.NavigationHost
import com.aveo.navcontroller.composable
import com.aveo.navcontroller.rememberNavController
import com.aveo.ui.screens.HomeScreen
import com.aveo.ui.screens.ProfileScreen
import screens.SettingScreen
import com.aveo.ui.screens.NotificationScreen

@Composable
fun App() {
    val screens = Screen.values().toList()
    val navController by rememberNavController(Screen.HomeScreen.name)
    val currentScreen by remember {
        navController.currentScreen
    }

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
                                navController.navigate(screen.name)
                            }
                        )
                    }
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
        onCloseRequest = ::exitApplication) {
        App()
    }
}

/**
 * Screens
 */
enum class Screen(
    val label: String,
    val icon: ImageVector
) {
    HomeScreen(
        label = "Home",
        icon = Icons.Filled.Home
    ),
    NotificationsScreen(
        label = "Residents",
        icon = Icons.Filled.Face
    ),
    SettingsScreen(
        label = "Settings",
        icon = Icons.Filled.Settings
    ),
    ProfileScreens(
        label = "User Profile",
        icon = Icons.Filled.AccountBox
    )
}


@Composable
fun CustomNavigationHost(
    navController: NavController
) {
    NavigationHost(navController) {
        composable(Screen.HomeScreen.name) {
            HomeScreen(navController)
        }

        composable(Screen.NotificationsScreen.name) {
            NotificationScreen(navController)
        }

        composable(Screen.SettingsScreen.name) {
            SettingScreen(navController)
        }

        composable(Screen.ProfileScreens.name) {
            ProfileScreen(navController)
        }

    }.build()
}