package com.example.petsapp.presentation.feeding

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
import com.example.petsapp.model.Feeding
import com.example.petsapp.presentation.components.AppDrawer
import com.example.petsapp.ui.theme.Blanco
import com.example.petsapp.ui.theme.ColorTexto
import com.example.petsapp.ui.theme.FondoPrincipal
import java.time.LocalDate

@Composable
fun FeedingScreen(
    navController: NavHostController,
    viewModel: FeedingViewModel = viewModel()
) {
    val feedingList by viewModel.feedingList.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadFeedingEntries()
    }

    AppDrawer(navController = navController, currentRoute = "feeding") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FondoPrincipal)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                "Alimentación diaria",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = ColorTexto
            )

            Spacer(Modifier.height(12.dp))

            if (feedingList.isEmpty()) {
                Text("No hay registros.")
            } else {
                feedingList.forEach { entry ->
                    FeedingCard(
                        entry = entry,
                        onDelete = {
                            viewModel.deleteFeedingEntry(entry.id) { success ->
                            }
                        }
                    )
                }

            }

            Spacer(Modifier.height(12.dp))

            Button(onClick = { showDialog = true }) {
                Text("Registrar alimentación")
            }

        }
    }

    if (showDialog) {
        FeedingDialog(
            onDismiss = { showDialog = false },
            onSave = { entry ->
                viewModel.addFeedingEntry(entry) {
                    showDialog = false
                }
            }
        )
    }
}

@Composable
fun FeedingDialog(
    onDismiss: () -> Unit,
    onSave: (Feeding) -> Unit
) {
    var petName by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(LocalDate.now().toString()) }
    var time by remember { mutableStateOf("") }
    var food by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Registrar alimentación") },
        text = {
            Column {
                OutlinedTextField(
                    value = petName,
                    onValueChange = { petName = it },
                    label = { Text("Nombre de la mascota") })
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Hora (ej. 08:00)") })
                OutlinedTextField(
                    value = food,
                    onValueChange = { food = it },
                    label = { Text("Comida") })
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notas (opcional)") })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(
                    Feeding(
                        petName = petName,
                        date = date,
                        time = time,
                        food = food,
                        notes = notes
                    )
                )
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

@Composable
fun FeedingCard(
    entry: Feeding,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Blanco, contentColor = ColorTexto)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Mascota: ${entry.petName}", fontWeight = FontWeight.Bold)
            Text("Fecha: ${entry.date}")
            Text("Hora: ${entry.time}")
            Text("Alimento: ${entry.food}")
            if (entry.notes.isNotBlank()) {
                Text("Notas: ${entry.notes}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onDelete,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
            ) {
                Icon(Icons.Outlined.Delete, contentDescription = "Eliminar")
                Spacer(Modifier.width(4.dp))
                Text("Eliminar")
            }
        }
    }
}