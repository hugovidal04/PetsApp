package com.example.petsapp.presentation.diary

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.petsapp.presentation.components.AppDrawer
import com.example.petsapp.ui.theme.ColorTexto
import com.example.petsapp.ui.theme.FondoPrincipal


@Composable
fun DiaryScreen(
    navController: NavHostController,
    viewModel: DiaryViewModel = viewModel()
) {
    val diaryEntries by viewModel.diaryList.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadDiaryEntries()
    }

    AppDrawer(navController = navController, currentRoute = "diary") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FondoPrincipal)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Diario de la mascota",
                color = ColorTexto,
                fontSize = 22.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            if (diaryEntries.isEmpty()) {
                Text("No hay entradas aún.")
            } else {
                diaryEntries.forEach { entry ->
                    DiaryCard(
                        entry = entry,
                        onDelete = {
                            viewModel.deleteDiaryEntry(entry.id) { /* Puedes mostrar feedback aquí */ }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = { showDialog = true }) {
                Text("Añadir nota")
            }
        }
    }

    if (showDialog) {
        DiaryDialog(
            onDismiss = { showDialog = false },
            onSave = { entry ->
                viewModel.addDiaryEntry(entry) {
                    showDialog = false
                }
            }
        )
    }
}