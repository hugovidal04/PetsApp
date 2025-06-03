package com.example.petsapp.presentation.physicalActivity

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
import com.example.petsapp.model.PhysicalActivity
import java.time.LocalDate

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
    var errorMessage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Registrar actividad") },
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
                    value = duration,
                    onValueChange = { duration = it },
                    label = {
                        Text(
                            "Duración",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                )
                OutlinedTextField(
                    value = activityType,
                    onValueChange = { activityType = it },
                    label = {
                        Text(
                            "Tipo de actividad",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = {
                        Text(
                            "Notas opcional",
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
                if (petName.isBlank() || duration.isBlank() || activityType.isBlank()) {
                    errorMessage = "Completa todos los campos"
                } else {
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