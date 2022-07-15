package com.aveo.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aveo.presentation.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AboutDialog(
    onClose: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .background(Blue50),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "About Aveo",
                    style = dialogTitle
                )

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .border(1.dp, Color.Black)
                ) {
                    IconButton(
                        onClick = { onClose() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close"
                        )
                    }

                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .padding(10.dp),
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
        modifier = Modifier
            .size(260.dp, 220.dp)
            .border(3.dp, Color.Gray)
        ,
        backgroundColor = Blue50,
        buttons = {}
    )
}