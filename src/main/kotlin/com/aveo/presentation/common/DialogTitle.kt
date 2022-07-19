package com.aveo.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aveo.presentation.theme.Blue700
import com.aveo.presentation.theme.dialogTitle

@Composable
fun DialogTitle(
    title: String,
    onClose: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = dialogTitle
        )

        Box(
            modifier = Modifier
                .size(20.dp)
                .background(Blue700),
            contentAlignment = Alignment.Center
        ) {
            AveoIconButton(
                onClick = { onClose() },
                imageVector = Icons.Filled.Clear,
                contentDescription = "Close",
                tint = Color.White
            )
        }
    }
}