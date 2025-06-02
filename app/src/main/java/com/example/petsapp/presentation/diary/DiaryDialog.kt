package com.example.petsapp.presentation.diary

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.petsapp.model.DiaryEntry
import java.time.LocalDate

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