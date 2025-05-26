package com.example.petsapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.petsapp.ui.theme.Principal
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxSize(),
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(280.dp)
                    .background(Principal)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Menú",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    HorizontalDivider()

                    Text(
                        "Mascotas",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    NavigationDrawerItem(
                        label = { Text("Mis mascotas") },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Pets, contentDescription = "Mascotas") },
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate("mascotas")
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Calendario") },
                        selected = false,
                        icon = {
                            Icon(
                                Icons.Outlined.CalendarToday,
                                contentDescription = "Calendario"
                            )
                        },
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate("calendario")
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Diario") },
                        selected = false,
                        icon = {
                            Icon(
                                Icons.AutoMirrored.Outlined.LibraryBooks,
                                contentDescription = "Diario"
                            )
                        },
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate("diario")
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        "Otros",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    NavigationDrawerItem(
                        label = { Text("Mapa") },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Map, contentDescription = "Mapa") },
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate("mapa")
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Configuración") },
                        selected = false,
                        icon = {
                            Icon(
                                Icons.Outlined.Settings,
                                contentDescription = "Configuración"
                            )
                        },
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate("configuracion")
                        }
                    )

                    Spacer(Modifier.weight(1f))

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    NavigationDrawerItem(
                        label = { Text("Cerrar sesión") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate("initial") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Inicio") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open()
                                else drawerState.close()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Bienvenido a PetsApp!")
            }
        }
    }
}