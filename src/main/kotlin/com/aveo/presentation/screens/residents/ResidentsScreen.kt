package com.aveo.presentation.screens.residents

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aveo.db.Resident
import com.aveo.presentation.screens.home.BackgroundImage
import com.aveo.presentation.theme.Blue50
import com.aveo.presentation.theme.Blue700
import com.aveo.presentation.theme.normalText
import com.aveo.presentation.theme.typography
import kotlinx.coroutines.delay

@Composable
fun ResidentsScreen(
    residentsViewModel: ResidentsViewModel
) {
    var currentImage by remember { mutableStateOf(1) }

    val radioOptions = listOf("Unit Order", "Name Order")

    val selectedValue = remember { mutableStateOf(radioOptions[0]) }
    val isSelectedItem: (String) -> Boolean = { selectedValue.value == it }
    val onChangeState: (String) -> Unit = { selectedValue.value = it }

    val residentsList1 = residentsViewModel.residents1.collectAsState(initial = emptyList()).value
    val residentsList2 = residentsViewModel.residents2.collectAsState(initial = emptyList()).value

    val cellWidth: (Int) -> Float = { index ->
        when (index) {
            0 -> 0.1f
            1 -> 0.5f
            2 -> 0.3f
            else -> 0.1f
        }
    }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(20000)
            currentImage = if (currentImage == 4) 1 else ++currentImage
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 70.dp),
    ) {
        Crossfade(
            targetState = currentImage.toString(),
            animationSpec = tween(4000)
        ) {
            BackgroundImage(it)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 30.dp, top = 20.dp, bottom = 20.dp)
                .background(Blue50)
                .border(3.dp, Color.Gray)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column() {
                    Text(
                        modifier = Modifier.padding(start = 10.dp, end = 20.dp),
                        text = "Resident Listing",
                        style = typography.h6
                    )
                }

                radioOptions.forEach { item ->
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .selectable(
                                    selected = isSelectedItem(item),
                                    onClick = { onChangeState(item) }
                                )


                        ) {
                            RadioButton(
                                selected = isSelectedItem(item),
                                onClick = { onChangeState(item) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Blue700,
                                    unselectedColor = Blue700,
                                    disabledColor = Color.Gray
                                )
                            )

                            Text(
                                text = item,
                                style = normalText
                            )

                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
               Table(1, cellWidth, residentsList1)
               Table(2, cellWidth, residentsList2)
            }
        }
    }
}

@Composable
fun Table(
    tableId: Int,
    cellWidth: (index: Int) -> Float,
    data: List<Resident>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(if (tableId == 1) 0.5f else 1f)
            .padding(start = if (tableId == 1) 20.dp else 0.dp)
    ) {
        items(data) { resident ->
            Row {
                for (i in 0..3) {
                    Surface(
                        border = BorderStroke(1.dp, Color.LightGray),
                        contentColor = Color.Transparent,
                        modifier = Modifier.fillMaxSize(cellWidth(i))
                    ) {
                        val value = when (i) {
                            0 -> getAnnotatedString(resident.unitNumber.toString())
                            1 -> getFormattedName(resident)
                            2 -> getAnnotatedString(getPhoneNumber(resident))
                            3 -> getAnnotatedString(if (resident.lastName.isEmpty()) "" else if (resident.firstName2.isNotBlank()) "2" else "1")
                            else -> getAnnotatedString("")
                        }

                        Text(
                            text = value,
                            fontSize = 10.sp,
                            textAlign = if (i == 1) TextAlign.Left else TextAlign.Center,
                            modifier = Modifier.padding(5.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }

            }
        }
    }
}

private fun getAnnotatedString(value: String) : AnnotatedString {
    val builder = AnnotatedString.Builder(" " + value)
    builder.addStyle(SpanStyle(fontWeight = FontWeight.ExtraBold), 0, 1)

    return builder.toAnnotatedString()
}
private fun getFormattedName(resident: Resident): AnnotatedString {
    val builder = AnnotatedString.Builder()

    if (resident.lastName.isNotEmpty()) {
        val name = resident.lastName
        builder.append(name)
        builder.addStyle(SpanStyle(fontWeight = FontWeight.ExtraBold), 0, name.length)
        builder.append(", ${resident.firstName1}")

        if (resident.firstName2.isNotEmpty()) {
            builder.append(" & ${resident.firstName2}")
        }

        if (resident.onResidentsCommittee) {
            val start = builder.length
            builder.append(" (RC)")
            builder.addStyle(SpanStyle(color = Color.Red), start, builder.length)
        }

        if (resident.isCommissionerForDeclarations) {
            val start = builder.length
            builder.append(" **")
            builder.addStyle(SpanStyle(color = Color.Red), start, builder.length)
        }
    } else {
        return getAnnotatedString("")
    }

    return builder.toAnnotatedString()
}

private fun getPhoneNumber(resident: Resident): String {
    var number = ""

    if (resident.mobileNumberId > 0L) {
        number = if (resident.mobileNumberId == 1L) resident.mobileNumber1
        else resident.mobileNumber2
    } else if (resident.mobileNumber1.isNotEmpty()) {
        number = resident.mobileNumber1
    } else if (resident.mobileNumber2.isNotEmpty()) {
        number = resident.mobileNumber2
    } else if (resident.phoneNumber.isNotEmpty()) {
        number = resident.phoneNumber
    }

    return number
}
