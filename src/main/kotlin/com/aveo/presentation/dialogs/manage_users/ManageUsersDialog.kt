package com.aveo.presentation.dialogs.manage_users

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aveo.presentation.common.AveoIconButton
import com.aveo.presentation.di
import com.aveo.presentation.screens.home.HomeEvent
import com.aveo.presentation.screens.home.HomeViewModel
import com.aveo.presentation.theme.*
import org.kodein.di.instance

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ManageUsersDialog(
    homeViewModel: HomeViewModel
) {
    val viewModel: ManageUsersViewModel by di.instance()

    val selectedUser by viewModel.selectedUser
    val showAddUserEdit by viewModel.showAddUserEdit
    val newUserName by viewModel.newUserName
    val newUserEnabled by viewModel.newUserEnabled

    val users = viewModel.users.collectAsState(initial = emptyList()).value

    val focusRequester = remember { FocusRequester() }

    AlertDialog(
        modifier = Modifier
            .size(250.dp, 250.dp)
            .border(width = 4.dp, color = Color.Gray),
        backgroundColor = Blue50,
        onDismissRequest = {},
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth().padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AveoIconButton(
                    onClick = { viewModel.showAddUserEdit(true) },
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "Add New User",
                )

                if (showAddUserEdit) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.52f)
                            .border(1.dp, Color.Black)
                            .padding(5.dp)
                    ) {
                        BasicTextField(
                            value = newUserName,
                            onValueChange = {
                                viewModel.onNewUserNameChange(it)
                                viewModel.setNewUserEnabled(newUserName.isNotEmpty() && users.none { user ->
                                    user.userName.equals(
                                        newUserName
                                    )
                                })
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                            textStyle = normalText
                        )
                    }

                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }

                    AveoIconButton(
                        onClick = { viewModel.addNewUser(newUserName) },
                        enabled = newUserEnabled,
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Save New User",
                        tint = if (newUserEnabled) Blue700 else Color.Gray
                    )

                    AveoIconButton(
                        onClick = { viewModel.showAddUserEdit(false) },
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Cancel Save New User"
                    )
                }
            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Manage Users")
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Blue700),
                    contentAlignment = Alignment.Center
                ) {
                    AveoIconButton(
                        onClick = { homeViewModel.onEvent(HomeEvent.ShowManageUsersDialog(false)) },
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }
        },
        text = {
            Box(
                modifier = Modifier
                    .size(width = 200.dp, height = 120.dp)
                    .border(1.dp, Color.Black)
                    .background(Color.LightGray)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        users.filter { user -> user.userName != "admin" }
                    ) { user ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (user == selectedUser) Color.Gray else Color.LightGray)
                                .selectable(
                                    selected = user == selectedUser,
                                    onClick = { viewModel.setSelectedUser(user) }
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(fraction = 0.7f),
                                text = user.userName
                            )

                            AveoIconButton(
                                onClick = { viewModel.deleteSelectedUser() },
                                modifier = Modifier
                                    .size(20.dp)
                                    .fillMaxWidth(fraction = .1f),
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete"
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            AveoIconButton(
                                modifier = Modifier.size(20.dp),
                                onClick = {
                                    viewModel.setSelectedUser(user)
                                    viewModel.resetPassword()
                                },
                                enabled = user.password != "password",
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = "Refresh"
                            )
                        }
                    }
                }
            }
        }
    )
}
