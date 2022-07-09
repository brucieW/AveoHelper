package com.aveo.presentation.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aveo.presentation.screens.home.HomeViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChangeAdminPasswordDialog(
    changeAdminPasswordViewModel: ChangeAdminPasswordViewModel
) {
    AlertDialog(
        modifier = Modifier.size(250.dp, 250.dp),
        onDismissRequest = {},
        buttons = {
            Button(
                onClick = {}
            ) {
                Text("OK")
            }
        },
        title = {
            Text("About Aveo Helper")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("This application is used for keeping track of Resident's meal charges")
                Text("Version 1.0")
                Text("Programmed by Bruce Edgerton")
                Text("Copyright &#169; 2022, All Rights Reserved</string>")
            }
        }

    )
}