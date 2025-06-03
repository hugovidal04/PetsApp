package com.example.petsapp.presentation.calendar

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.petsapp.model.PetEvent
import java.time.LocalDate

@Composable
fun CreateEventDialog(
    date: LocalDate,
    onDismiss: () -> Unit,
    onSave: (PetEvent) -> Unit
) {
    var petName by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo Evento") },
        text = {
            Column {
                Text("Fecha: $date")
                OutlinedTextField(
                    value = petName,
                    onValueChange = { petName = it },
                    label = {
                        Text(
                            "Nombre de la mascota",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = {
                        Text(
                            "Título",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = {
                        Text(
                            "Descripción",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                )
                OutlinedTextField(
                    value = type,
                    onValueChange = { type = it },
                    label = {
                        Text(
                            "Tipo (vacuna, cita, tratamiento, ...)",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                )
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (petName.isBlank() || title.isBlank() || description.isBlank() || type.isBlank()) {
                    errorMessage = "Completa todos los campos"
                } else {
                    onSave(
                        PetEvent(
                            date = date.toString(),
                            petName = petName,
                            title = title,
                            description = description,
                            type = type
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