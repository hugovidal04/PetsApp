package com.example.petsapp.presentation.pets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.petsapp.model.Pet

@Composable
fun PetDialog(
    initialPet: Pet? = null,
    onDismiss: () -> Unit,
    onSavePet: (Pet) -> Unit
) {
    var name by remember { mutableStateOf(initialPet?.name ?: "") }
    var species by remember { mutableStateOf(initialPet?.species ?: "") }
    var breed by remember { mutableStateOf(initialPet?.breed ?: "") }
    var birthDate by remember { mutableStateOf(initialPet?.birthDate ?: "") }
    var gender by remember { mutableStateOf(initialPet?.gender ?: "") }
    var errorMessage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initialPet == null) "Nueva Mascota" else "Editar Mascota") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = species,
                    onValueChange = { species = it },
                    label = { Text("Especie") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = breed,
                    onValueChange = { breed = it },
                    label = { Text("Raza") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = birthDate,
                    onValueChange = { birthDate = it },
                    label = { Text("Fecha de Nacimiento") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = gender,
                    onValueChange = { gender = it },
                    label = { Text("Sexo") },
                    modifier = Modifier.fillMaxWidth()
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
                    val pet = initialPet?.copy(
                        name = name,
                        species = species,
                        breed = breed,
                        birthDate = birthDate,
                        gender = gender
                    ) ?: Pet(
                        name = name,
                        species = species,
                        breed = breed,
                        birthDate = birthDate,
                        gender = gender
                    )
                    onSavePet(pet)
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