package com.example.petsapp.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.petsapp.model.PetEvent
import com.example.petsapp.presentation.components.AppDrawer
import com.example.petsapp.ui.theme.ColorTexto
import com.example.petsapp.ui.theme.FondoPrincipal
import io.github.boguszpawlowski.composecalendar.StaticCalendar
import io.github.boguszpawlowski.composecalendar.rememberCalendarState
import java.time.LocalDate


@Composable
fun CalendarScreen(
    navController: NavHostController,
    viewModel: CalendarViewModel = viewModel()
) {
    val events by viewModel.events.collectAsState()
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val calendarState = rememberCalendarState()

    LaunchedEffect(Unit) {
        viewModel.loadEvents()
    }

    val filteredEvents = events.filter { it.date == selectedDate?.toString() }

    AppDrawer(navController = navController, currentRoute = "calendar") {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FondoPrincipal)
                .padding(16.dp)
        ) {
            Text("Calendario de Eventos", fontWeight = FontWeight.Bold, fontSize = 22.sp)

            Spacer(Modifier.height(12.dp))

            StaticCalendar(
                calendarState = calendarState,
                dayContent = { dayState ->
                    val date = dayState.date
                    Text(
                        text = date.dayOfMonth.toString(),
                        modifier = Modifier
                            .padding(4.dp)
                            .background(if (date == selectedDate) Color.Gray else Color.Transparent)
                            .padding(8.dp)
                            .clickable {
                                selectedDate = date
                                showDialog = true
                            }
                    )
                }
            )

            Spacer(Modifier.height(16.dp))

            if (filteredEvents.isEmpty()) {
                Text("No hay eventos para esta fecha.")
            } else {
                filteredEvents.forEach { event ->
                    Text("- Nombre de mascotas: ${event.petName} Título: ${event.title} Descripción: ${event.description} Evento: (${event.type})", fontSize = 16.sp, color = ColorTexto)
                }
            }
        }
    }

    if (showDialog && selectedDate != null) {
        CreateEventDialog(
            date = selectedDate!!,
            onDismiss = { showDialog = false },
            onSave = { event ->
                viewModel.addEvent(event) { success ->
                    showDialog = false
                }
            }
        )
    }
}

@Composable
fun CreateEventDialog(
    date: LocalDate,
    onDismiss: () -> Unit,
    onSave: (PetEvent) -> Unit
) {
    var petName by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("vacuna") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo Evento") },
        text = {
            Column {
                Text("Fecha: $date")
                OutlinedTextField(
                    value = petName,
                    onValueChange = { petName = it },
                    label = { Text("Nombre de la mascota") })
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") })
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") })
                OutlinedTextField(
                    value = type,
                    onValueChange = { type = it },
                    label = { Text("Tipo (vacuna, cita, tratamiento)") })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(
                    PetEvent(
                        date = date.toString(),
                        petName = petName,
                        title = title,
                        description = description,
                        type = type
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