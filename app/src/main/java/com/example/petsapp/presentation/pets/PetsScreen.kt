package com.example.petsapp.presentation.pets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.petsapp.model.Pet
import com.example.petsapp.presentation.components.AppDrawer

@Composable
fun PetsScreen(
    navController: NavHostController,
    viewModel: PetsViewModel = viewModel()
) {
    val pets by viewModel.pets.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var editingPet by remember { mutableStateOf<Pet?>(null) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadPets()
    }

    AppDrawer(navController = navController, currentRoute = "pets") {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Mis Mascotas", style = MaterialTheme.typography.titleLarge)

            Spacer(Modifier.height(8.dp))

            pets.forEach { pet ->
                PetCard(
                    pet = pet,
                    onEdit = {
                        editingPet = pet
                        showDialog = true
                    },
                    onDelete = {
                        viewModel.deletePet(pet.id) { error ->
                            if (error.isNotEmpty()) {
                                errorMessage = error
                            }
                        }
                    }
                )
                Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.height(16.dp))

            Button(onClick = {
                editingPet = null
                showDialog = true
            }) {
                Text("Añadir Mascota")
            }

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        }
    }

    if (showDialog) {
        PetDialog(
            initialPet = editingPet,
            onDismiss = { showDialog = false; errorMessage = "" },
            onSavePet = { pet ->
                viewModel.addOrUpdatePet(pet) { error ->
                    if (error.isNotEmpty()) {
                        errorMessage = error
                    } else {
                        showDialog = false
                        errorMessage = ""
                    }
                }
            }
        )
    }
}