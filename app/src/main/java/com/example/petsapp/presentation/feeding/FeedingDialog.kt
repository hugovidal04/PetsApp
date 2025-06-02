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