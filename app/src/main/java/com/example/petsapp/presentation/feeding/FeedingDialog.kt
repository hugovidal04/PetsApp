package com.example.petsapp.presentation.feeding

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
import com.example.petsapp.model.Feeding
import java.time.LocalDate

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
    var errorMessage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Registrar alimentación") },
        text = {
            Column {
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
                    value = time,
                    onValueChange = { time = it },
                    label = {
                        Text(
                            "Hora",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                )
                OutlinedTextField(
                    value = food,
                    onValueChange = { food = it },
                    label = {
                        Text(
                            "Comida",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = {
                        Text(
                            "Notas (opcional)",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                )
                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = Color.Red)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (petName.isBlank() || time.isBlank() || food.isBlank()) {
                    errorMessage = "Completa todos los campos excepto las notas"
                } else {
                    onSave(
                        Feeding(
                            petName = petName,
                            date = date,
                            time = time,
                            food = food,
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