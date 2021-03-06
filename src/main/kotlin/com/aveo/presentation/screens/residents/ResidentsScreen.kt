package com.aveo.presentation.screens.residents

import androidx.compose.foundation.*
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
import com.aveo.presentation.common.AveoButton
import com.aveo.presentation.common.AveoPage
import com.aveo.presentation.theme.*

@Composable
fun ResidentsScreen(
    viewModel: ResidentsViewModel
) {
    val selectedOrder by viewModel.selectedOrderValue

    val residents1 = viewModel.residents1.collectAsState(initial = emptyList()).value
    val residents2 = viewModel.residents2.collectAsState(initial = emptyList()).value
    val residentsByName = viewModel.residents3.collectAsState(initial = emptyList()).value

    val loadDataEnabled by viewModel.loadDataEnabled

    val cellWidth: (Int) -> Float = { index ->
        when (index) {
            0 -> 0.12f
            1 -> 0.6f
            2 -> 0.75f
            else -> 1f
        }
    }

    AveoPage {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
                .background(Blue50)
                .border(3.dp, Color.Gray)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column() {
                    Text(
                        modifier = Modifier.padding(start = 25.dp, end = 20.dp),
                        text = "Residents Listing",
                        style = typography.h6
                    )
                }

                viewModel.radioOptions.forEach { item ->
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .selectable(
                                    selected = selectedOrder == item,
                                    onClick = { viewModel.onOrderChange(item) }
                                )


                        ) {
                            RadioButton(
                                selected = selectedOrder == item,
                                onClick = { viewModel.onOrderChange(item) },
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

                AveoButton(
                    modifier = Modifier.padding(start = 30.dp),
                    onClick = { viewModel.loadResidentsFile() },
                    text = "Load Residents File",
                    enabled = loadDataEnabled
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                ) {
                    if (selectedOrder == viewModel.radioOptions[0]) {
                        Table(cellWidth, residents1)
                    } else {
                        val data = if (residentsByName.size > 69) residentsByName.subList(0, 68) else residentsByName
                        Table(cellWidth, data)
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(end = 20.dp, bottom = 20.dp)
                ) {
                    if (selectedOrder == viewModel.radioOptions[0]) {
                        Table(cellWidth, residents2)
                    } else {
                        val data = if (residentsByName.size > 69) residentsByName.subList(
                            69,
                            residentsByName.size - 1
                        ) else emptyList()
                        Table(cellWidth, data)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Table(
    cellWidth: (index: Int) -> Float,
    data: List<Resident>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(data) { resident ->
            Row(
//                modifier = Modifier.mouseClickable(
//                    enabled = true,
//                    onClickLabel = "TEst",
//                    onClick = {
//                        if (this.buttons.isSecondaryPressed)
//                    }
//                )
            ) {
                for (i in 0..3) {
                    Surface(
                        border = BorderStroke(1.dp, Color.LightGray),
                        contentColor = Color.Transparent,
                        modifier = Modifier.fillMaxSize(cellWidth(i))

                    ) {
                        val value = when (i) {
                            0 -> getAnnotatedString(resident.unitNumber)
                            1 -> getFormattedName(resident)
                            2 -> getAnnotatedString(if (resident.phoneNumber.isNotBlank()) resident.phoneNumber else resident.mobileNumber)
                            3 -> getAnnotatedString(if (resident.lastName.isEmpty()) "" else if (resident.firstName2.isNotBlank()) "2" else "1")
                            else -> getAnnotatedString("")
                        }

                        val unitNumber = resident.unitNumber
                        val iUnitNumber = if (unitNumber == "100A") -100 else unitNumber.toInt()

                        Text(
                            text = value,
                            fontSize = 10.sp,
                            textAlign = if (i == 1) TextAlign.Left else TextAlign.Center,
                            modifier = Modifier.padding(5.dp)
                                .background(if (iUnitNumber == -100 || iUnitNumber in 89..124) Blue100 else Color.White, squareShape),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }

            }
        }
    }
}

private fun getAnnotatedString(value: String): AnnotatedString {
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
