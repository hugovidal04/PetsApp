package com.example.petsapp.presentation.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petsapp.R
import com.example.petsapp.presentation.components.ButtonComponent
import com.example.petsapp.presentation.components.NormalText
import com.example.petsapp.ui.theme.FondoPrincipal
import com.example.petsapp.ui.theme.PrincipalAdmin
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AdminScreen(
    viewModel: AdminViewModel = viewModel(),
    onLogout: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val users by viewModel.users.collectAsState()

    val filteredUsers = users.filter { user ->
        user.name.contains(searchQuery, ignoreCase = true) ||
                user.email.contains(searchQuery, ignoreCase = true)
    }

    var showCreateDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = FondoPrincipal)
            .padding(28.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NormalText(
            text = stringResource(id = R.string.panel_admin),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        SearchBarComp(
            searchQuery = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        filteredUsers.forEach { user ->
            UserCard(user = user, onDelete = {
                viewModel.deleteUser(user.uid)
            }, onMakeAdmin = {
                viewModel.makeAdmin(user.uid)
            })
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ButtonComponent(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    onLogout()
                },
                text = stringResource(id = R.string.boton_cerrar_sesion_admin),
                backgroundColor = PrincipalAdmin
            )
            Spacer(modifier = Modifier.width(8.dp))
            ButtonComponent(
                onClick = {
                    showCreateDialog = true
                },
                text = stringResource(id = R.string.boton_crear_cuenta_admin),
                backgroundColor = PrincipalAdmin
            )
            if (showCreateDialog) {
                CreateUserDialog(
                    onDismiss = { showCreateDialog = false },
                    onCreateUser = { name, email, password, adminPassword, onFailure ->
                        viewModel.createUserAsAdmin(
                            name = name,
                            email = email,
                            password = password,
                            adminPassword = adminPassword,
                            onSuccess = { showCreateDialog = false },
                            onFailure = { error ->
                                onFailure(error)
                            }
                        )
                    }
                )
            }
        }
    }
}