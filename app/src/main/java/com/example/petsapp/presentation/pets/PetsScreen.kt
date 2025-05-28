package com.example.petsapp.presentation.pets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.petsapp.model.Pet
import com.example.petsapp.presentation.components.AppDrawer
import com.example.petsapp.presentation.components.ButtonComponent
import com.example.petsapp.ui.theme.ColorTexto
import com.example.petsapp.ui.theme.FondoPrincipal
import com.example.petsapp.ui.theme.PrincipalAdmin

@Composable
fun PetsScreen(
    navController: NavHostController,
    viewModel: PetsViewModel = viewModel()
) {
    val pets by viewModel.pets.collectAsState()
    var showCreateDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var editingPet by remember { mutableStateOf<Pet?>(null) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadPets()
    }

    AppDrawer(navController = navController, currentRoute = "pets") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FondoPrincipal)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Text("Mis Mascotas", color = ColorTexto, fontWeight = FontWeight.Bold, fontSize = 24.sp)

            Spacer(Modifier.height(8.dp))

            pets.forEach { pet ->
                PetCard(
                    pet = pet,
                    onEdit = {
                        editingPet = pet
                        showEditDialog = true
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                ButtonComponent(
                    onClick = { showCreateDialog = true },
                    text = "Añadir mascota",
                    backgroundColor = PrincipalAdmin
                )


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

    if (showCreateDialog) {
        CreatePetDialog(
            onDismiss = { showCreateDialog = false; errorMessage = "" },
            onSavePet = { pet ->
                viewModel.addOrUpdatePet(pet) { error ->
                    if (error.isNotEmpty()) {
                        errorMessage = error
                    } else {
                        showCreateDialog = false
                        errorMessage = ""
                    }
                }
            }
        )
    }

    if (showEditDialog) {
        editingPet?.let { pet ->
            EditPetDialog(
                pet = pet,
                onDismiss = { showEditDialog = false; errorMessage = "" },
                onSavePet = { updatedPet ->
                    viewModel.addOrUpdatePet(updatedPet) { error ->
                        if (error.isNotEmpty()) {
                            errorMessage = error
                        } else {
                            showEditDialog = false
                            errorMessage = ""
                        }
                    }
                }
            )
        }
    }
}