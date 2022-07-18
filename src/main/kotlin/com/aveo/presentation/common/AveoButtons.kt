package com.aveo.presentation.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.aveo.presentation.theme.*

@Composable
fun AveoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            disabledBackgroundColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(),
        shape = buttonShape,
        enabled = enabled,
        onClick = onClick,
        elevation = ButtonDefaults.elevation(10.dp, 0.dp, 0.dp),
    ) {
        Box(
            modifier = Modifier
                .background(buttonBrush)
                .background(if (enabled) buttonBrush else disabledBrush)
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Text(
                text = text,
                style = typography.button
            )
        }
    }
}

@Composable
fun AveoIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    tint: Color = Blue700,
    imageVector: ImageVector,
    contentDescription: String = ""
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint,
        )
    }


}
@Composable
fun MultipleButtonBar(
    buttonData: List<ButtonData>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        buttonData.forEach { data ->
            AveoButton(
                modifier = Modifier.padding(start = 20.dp),
                text = data.text,
                enabled = data.enabled,
                onClick = data.onClicked
            )
        }
    }
}

data class ButtonData(
    val text: String,
    val enabled: Boolean = true,
    val onClicked: () -> Unit
)

fun getTwoButtons(
    firstButtonText: String = "Save",
    secondButtonText: String = "Cancel",
    firstButtonEnabled: Boolean = false,
    secondButtonEnabled: Boolean = true,
    onFirstButtonClicked: () -> Unit,
    onSecondButtonClicked: () -> Unit
): List<ButtonData> {
    return listOf(
        ButtonData(
            firstButtonText,
            onClicked = onFirstButtonClicked,
            enabled = firstButtonEnabled
        ),

        ButtonData(
            secondButtonText,
            onClicked = onSecondButtonClicked,
            enabled = secondButtonEnabled )
    )
}

fun getYesNoButtons(
    yesButtonEnabled: Boolean = true,
    onYesClicked: () -> Unit,
    onNoClicked: () -> Unit
) : List<ButtonData>  {
    return getTwoButtons(
        firstButtonText = "Yes",
        secondButtonText = " No ",
        firstButtonEnabled = yesButtonEnabled,
        onFirstButtonClicked = onYesClicked,
        onSecondButtonClicked = onNoClicked
    )
}

@Preview
@Composable
fun ShowButton() {
    Column {
        AveoButton(
            text = "Normal",
            onClick = { }
        )

        AveoButton(
            modifier = Modifier.padding(top = 10.dp),
            text = "Disabled",
            enabled = false,
            onClick = {}
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            contentPadding = PaddingValues(),
            shape = buttonShape,
            onClick = { },
            elevation = ButtonDefaults.elevation(5.dp, 0.dp, 0.dp),
        ) {
            Box(
                modifier = Modifier
                    .background(buttonBrush)
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Test Button",
                    style = typography.button
                )
            }
        }
    }
}

@Preview
@Composable
fun ShowSaveCancel() {
    MultipleButtonBar(
        getTwoButtons(
            onFirstButtonClicked = {},
            onSecondButtonClicked = {}
        )
    )
}

@Preview
@Composable
fun ShowYesNo() {
    MultipleButtonBar(
        getYesNoButtons(
            onYesClicked = {},
            onNoClicked = {}
        )
    )
}
