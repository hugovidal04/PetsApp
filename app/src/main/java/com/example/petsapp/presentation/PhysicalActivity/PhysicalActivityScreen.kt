package com.example.petsapp.presentation.PhysicalActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.petsapp.model.PhysicalActivity
import com.example.petsapp.presentation.components.AppDrawer
import com.example.petsapp.ui.theme.Blanco
import com.example.petsapp.ui.theme.ColorTexto
import com.example.petsapp.ui.theme.FondoPrincipal
import java.time.LocalDate

@Composable
fun PhysicalActivityScreen(
    navController: NavHostController,
    viewModel: PhysicalActivityViewModel = viewModel()
) {
    val activities by viewModel.activities.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadActivities()
    }

    AppDrawer(navController = navController, currentRoute = "actividad_fisica") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FondoPrincipal)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Actividad Física", fontSize = 22.sp, color = ColorTexto)

            Spacer(Modifier.height(12.dp))

            if (activities.isEmpty()) {
                Text("No hay registros.")
            } else {
                activities.forEach { activity ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Blanco,
                            contentColor = ColorTexto
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Mascota: ${activity.petName}", fontWeight = FontWeight.Bold)
                            Text("Fecha: ${activity.date}")
                            Text("Duración: ${activity.duration}")
                            Text("Tipo: ${activity.activityType}")
                            if (activity.notes.isNotBlank()) {
                                Text("Notas: ${activity.notes}")
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedButton(
                                onClick = { viewModel.deleteActivity(activity.id) {} },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                            ) {
                                Icon(Icons.Outlined.Delete, contentDescription = "Eliminar")
                                Spacer(Modifier.width(4.dp))
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Button(onClick = { showDialog = true }) {
                Text("Registrar actividad")
            }
        }
    }

    if (showDialog) {
        PhysicalActivityDialog(
            onDismiss = { showDialog = false },
            onSave = { entry ->
                viewModel.addActivity(entry) { showDialog = false }
            }
        )
    }
}

@Composable
fun PhysicalActivityDialog(
    onDismiss: () -> Unit,
    onSave: (PhysicalActivity) -> Unit
) {
    var petName by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(LocalDate.now().toString()) }
    var duration by remember { mutableStateOf("") }
    var activityType by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Registrar actividad") },
        text = {
            Column {
                OutlinedTextField(
                    value = petName,
                    onValueChange = { petName = it },
                    label = { Text("Nombre de la mascota") })
                OutlinedTextField(
                    value = duration,
                    onValueChange = { duration = it },
                    label = { Text("Duración") })
                OutlinedTextField(
                    value = activityType,
                    onValueChange = { activityType = it },
                    label = { Text("Tipo de actividad") })
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notas (opcional)") })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (petName.isNotBlank() && duration.isNotBlank() && activityType.isNotBlank()) {
                    onSave(
                        PhysicalActivity(
                            petName = petName,
                            date = date,
                            duration = duration,
                            activityType = activityType,
                            notes = notes
                        )
                    )
                }
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}