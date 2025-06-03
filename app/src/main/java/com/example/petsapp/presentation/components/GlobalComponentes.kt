package com.example.petsapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.petsapp.ui.theme.ColorTexto
import kotlinx.coroutines.launch


@Composable
fun NormalText(
    text: String,
    fontSize: TextUnit = 48.sp,
    color: Color = Color.Black,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Center,
    modifier: Modifier = Modifier, //Dependiendo de si queremos que se centre el texto o no usaremos ".fillMaxWidth()"
    onClick: () -> Unit = {}
) {
    Text(
        modifier = modifier.clickable(onClick = onClick),
        text = text,
        style = TextStyle(
            fontSize = fontSize,
            color = color,
            fontWeight = fontWeight
        ),
        textAlign = textAlign,
    )
}

@Composable
fun ImageList(
    imagenes: List<Painter>,
    size: Dp = 200.dp
) {
    Row {
        imagenes.forEach { imagen ->
            Image(
                painter = imagen,
                contentDescription = null,
                modifier = Modifier.size(size)
            )
        }
    }
}

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable () -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = placeholder,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun PasswordField(value: String, onValueChange: (String) -> Unit) {

    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Contraseña") },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconToggleButton(
                checked = passwordVisible,
                onCheckedChange = { passwordVisible = it }
            ) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun ButtonComponent(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            disabledContentColor = Color.White
        )
    ) {
        Text(text = text)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    navController: NavHostController,
    currentRoute: String,
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxSize(),
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(280.dp),
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
                    )
                    HorizontalDivider()

                    Text(
                        "Mascotas",
                        modifier = Modifier.padding(16.dp),
                    )
                    NavigationDrawerItem(
                        label = { Text("Mis mascotas") },
                        selected = currentRoute == "pets",
                        icon = { Icon(Icons.Outlined.Pets, null) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            if (currentRoute != "pets") navController.navigate("pets")
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Calendario") },
                        selected = currentRoute == "calendar",
                        icon = { Icon(Icons.Outlined.CalendarToday, null) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            if (currentRoute != "calendar") navController.navigate("calendar")
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Alimentación") },
                        selected = currentRoute == "feeding",
                        icon = { Icon(Icons.AutoMirrored.Outlined.LibraryBooks, null) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            if (currentRoute != "feeding") navController.navigate("feeding")
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Actividad física") },
                        selected = currentRoute == "physical_activity",
                        icon = { Icon(Icons.AutoMirrored.Outlined.LibraryBooks, null) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            if (currentRoute != "physical_activity") navController.navigate("physical_activity")
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Diario") },
                        selected = currentRoute == "diary",
                        icon = { Icon(Icons.AutoMirrored.Outlined.LibraryBooks, null) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            if (currentRoute != "diary") navController.navigate("diary")
                        }
                    )

                    HorizontalDivider(Modifier.padding(vertical = 8.dp))

                    Text(
                        "Otros",
                        modifier = Modifier.padding(16.dp),
                    )
                    NavigationDrawerItem(
                        label = { Text("Mapa") },
                        selected = currentRoute == "map",
                        icon = { Icon(Icons.Outlined.Map, null) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            if (currentRoute != "map") navController.navigate("map")
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Configuración") },
                        selected = currentRoute == "settings",
                        icon = { Icon(Icons.Outlined.Settings, null) },
                        onClick = {
                            scope.launch { drawerState.close() }
                            if (currentRoute != "settings") navController.navigate("settings")
                        }
                    )

                    Spacer(Modifier.weight(1f))

                    HorizontalDivider(Modifier.padding(vertical = 8.dp))

                    NavigationDrawerItem(
                        label = { Text("Cerrar sesión") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate("initial") {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = ColorTexto,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),
                    title = { Text("PetsApp") },
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
            Box(modifier = Modifier.padding(innerPadding)) {
                content()
            }
        }
    }
}