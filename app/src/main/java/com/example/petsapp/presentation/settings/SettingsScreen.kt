package com.example.petsapp.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.petsapp.presentation.components.AppDrawer
import com.example.petsapp.ui.theme.ColorTexto
import com.example.petsapp.ui.theme.FondoPrincipal

@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel = viewModel()
) {
    val context = LocalContext.current
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    var showConfirmDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadPreferences(context)
    }

    AppDrawer(navController = navController, currentRoute = "settings") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FondoPrincipal)
                .padding(16.dp)
        ) {

            Text("Configuración", color = ColorTexto)
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Recibir notificaciones", color = ColorTexto)
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { enabled ->
                        viewModel.toggleNotifications(context, enabled)
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { showConfirmDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Eliminar cuenta", color = MaterialTheme.colorScheme.onError)
            }
        }

        if (showConfirmDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmDialog = false },
                title = { Text("Confirmar eliminación") },
                text = { Text("¿Estás seguro de que deseas eliminar tu cuenta?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.deleteUser { success ->
                            showConfirmDialog = false
                            if (success) {
                                navController.popBackStack(
                                    route = navController.graph.startDestinationRoute!!,
                                    inclusive = true
                                )
                                navController.navigate("login")
                            }
                        }
                    }) {
                        Text("Sí, eliminar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showConfirmDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}