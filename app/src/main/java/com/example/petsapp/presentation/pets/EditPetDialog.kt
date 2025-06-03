package com.example.petsapp.presentation.pets

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petsapp.model.Pet

@Composable
fun EditPetDialog(
    pet: Pet,
    onDismiss: () -> Unit,
    onSavePet: (Pet) -> Unit
) {
    var name by remember { mutableStateOf(pet.name) }
    var species by remember { mutableStateOf(pet.species) }
    var breed by remember { mutableStateOf(pet.breed) }
    var birthDate by remember { mutableStateOf(pet.birthDate) }
    var gender by remember { mutableStateOf(pet.gender) }
    var errorMessage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Mascota") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = {
                        Text(
                            "Nombre",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                )
                OutlinedTextField(
                    value = species,
                    onValueChange = { species = it },
                    label = {
                        Text(
                            "Especie",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                )
                OutlinedTextField(
                    value = breed,
                    onValueChange = { breed = it },
                    label = {
                        Text(
                            "Raza",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                )
                OutlinedTextField(
                    value = birthDate,
                    onValueChange = { birthDate = it },
                    label = {
                        Text(
                            "Fecha de nacimiento",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                )
                OutlinedTextField(
                    value = gender,
                    onValueChange = { gender = it },
                    label = {
                        Text(
                            "Sexo",
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
                if (name.isBlank() || species.isBlank()) {
                    errorMessage = "Completa al menos nombre y especie"
                } else {
                    val updatedPet = pet.copy(
                        name = name,
                        species = species,
                        breed = breed,
                        birthDate = birthDate,
                        gender = gender
                    )
                    onSavePet(updatedPet)
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