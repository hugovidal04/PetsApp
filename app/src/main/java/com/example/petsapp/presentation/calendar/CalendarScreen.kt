package com.example.petsapp.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Calendario de Eventos",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = ColorTexto
            )

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
                            },
                        color = ColorTexto
                    )
                }
            )

            Spacer(Modifier.height(16.dp))

            if (filteredEvents.isEmpty()) {
                Text("No hay eventos para esta fecha.", color = ColorTexto)
            } else {
                filteredEvents.forEach { event ->
                    Text(
                        "- Nombre de mascotas: ${event.petName} Título: ${event.title} Descripción: ${event.description} Evento: (${event.type})",
                        fontSize = 16.sp,
                        color = ColorTexto
                    )
                }
            }
        }
    }

    if (showDialog && selectedDate != null) {
        CreateEventDialog(
            date = selectedDate!!,
            onDismiss = { showDialog = false },
            onSave = { event ->
                viewModel.addEvent(event) {
                    showDialog = false
                }
            }
        )
    }
}