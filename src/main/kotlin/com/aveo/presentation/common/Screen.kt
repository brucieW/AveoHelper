package com.aveo.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

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
    ResidentsScreen(
        label = "Residents",
        icon = Icons.Filled.Face
    ),
    KitchenScreen(
        label = "Kitchen",
        icon = Icons.Filled.Favorite
    ),
    AboutDialog(
        label = "About",
        icon = Icons.Filled.Info
    )
}

