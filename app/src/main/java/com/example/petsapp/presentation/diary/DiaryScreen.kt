package com.example.petsapp.presentation.diary

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
import com.example.petsapp.model.DiaryEntry
import com.example.petsapp.presentation.components.AppDrawer
import com.example.petsapp.ui.theme.Blanco
import com.example.petsapp.ui.theme.ColorTexto
import com.example.petsapp.ui.theme.FondoPrincipal
import java.time.LocalDate


@Composable
fun DiaryScreen(
    navController: NavHostController,
    viewModel: DiaryViewModel = viewModel()
) {
    val diaryEntries by viewModel.diaryList.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadDiaryEntries()
    }

    AppDrawer(navController = navController, currentRoute = "diary") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FondoPrincipal)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Diario de la mascota",
                color = ColorTexto,
                fontSize = 22.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            if (diaryEntries.isEmpty()) {
                Text("No hay entradas aún.")
            } else {
                diaryEntries.forEach { entry ->
                    DiaryCard(
                        entry = entry,
                        onDelete = {
                            viewModel.deleteDiaryEntry(entry.id) { /* Puedes mostrar feedback aquí */ }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = { showDialog = true }) {
                Text("Añadir nota")
            }
        }
    }

    if (showDialog) {
        DiaryDialog(
            onDismiss = { showDialog = false },
            onSave = { entry ->
                viewModel.addDiaryEntry(entry) {
                    showDialog = false
                }
            }
        )
    }
}


@Composable
fun DiaryDialog(
    onDismiss: () -> Unit,
    onSave: (DiaryEntry) -> Unit
) {
    var petName by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nueva Entrada de Diario") },
        text = {
            Column {
                OutlinedTextField(
                    value = petName,
                    onValueChange = { petName = it },
                    label = { Text("Mascota") })
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") })
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Contenido") })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val currentDate = LocalDate.now().toString()

                onSave(
                    DiaryEntry(
                        petName = petName,
                        date = currentDate,
                        title = title,
                        content = content
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
fun DiaryCard(
    entry: DiaryEntry,
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
            Text("Fecha: ${entry.date}", fontWeight = FontWeight.Medium)
            Text("Título: ${entry.title}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(entry.content)

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
