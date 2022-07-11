package com.aveo.presentation.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aveo.presentation.theme.Blue50
import com.aveo.presentation.theme.Shapes
import com.aveo.presentation.theme.smallerText

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AboutDialog(
    onClose: () -> Unit
) {
    AlertDialog(
        modifier = Modifier
            .size(280.dp, 260.dp)
            .shadow(elevation = 20.dp),
        onDismissRequest = {},
        buttons = {
            Button(
                modifier = Modifier.padding(start = 100.dp, top = 0.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 10.dp, focusedElevation = 10.dp),
                onClick = { onClose() }
            ) {
                Text(
                    text = "OK",
                    textAlign = TextAlign.Center
                )
            }
        },
        title = {
            Text(
                "About Aveo Helper"
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("This application is used for keeping track of Resident's meal charges")
                Spacer(modifier = Modifier.height(15.dp))
                Text("Version 1.0")
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    "Programmed by Bruce Edgerton",
                    style = smallerText
                )
                Text(
                    "Copyright 2022, All Rights Reserved",
                    style = smallerText
                )
            }
        },
        shape = Shapes.large,
        backgroundColor = Blue50
    )
}
