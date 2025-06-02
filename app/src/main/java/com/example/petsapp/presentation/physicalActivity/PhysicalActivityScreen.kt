package com.example.petsapp.presentation.physicalActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.petsapp.presentation.components.AppDrawer
import com.example.petsapp.ui.theme.ColorTexto
import com.example.petsapp.ui.theme.FondoPrincipal

@Composable
fun PhysicalActivityScreen(
    navController: NavHostController,
    viewModel: PhysicalActivityViewModel = viewModel()
) {
    val activities by viewModel.activities.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadActivities()
    }

    AppDrawer(navController = navController, currentRoute = "actividad_fisica") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FondoPrincipal)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Actividad Física", fontSize = 22.sp, color = ColorTexto, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(12.dp))

            if (activities.isEmpty()) {
                Text("No hay registros.")
            } else {
                activities.forEach { activity ->
                    PhysicalActivityCard(
                        entry = activity,
                        onDelete = {
                            viewModel.deleteActivity(activity.id) {}
                        }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            Button(onClick = { showDialog = true }) {
                Text("Registrar actividad")
            }
        }
    }

    if (showDialog) {
        PhysicalActivityDialog(
            onDismiss = { showDialog = false },
            onSave = { entry ->
                viewModel.addActivity(entry) { showDialog = false }
            }
        )
    }
}