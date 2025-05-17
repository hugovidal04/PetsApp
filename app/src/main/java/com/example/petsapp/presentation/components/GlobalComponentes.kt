package com.example.petsapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petsapp.model.AppUser
import com.example.petsapp.ui.theme.FondoPrincipal


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
fun ListaImagenes(
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
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Contraseña") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun ButtonComponent(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            disabledContentColor = Color.White
        )
    ) {
        Text(text = text)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BarraBusqueda() {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 900.dp),
        query = query,
        onQueryChange = { query = it },
        onSearch = { println("Búsqueda confirmada: $it") },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text("Buscar por nombre o correo") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
        colors = SearchBarDefaults.colors(
            containerColor = Color(0xFFf1e7e4),
            inputFieldColors = TextFieldDefaults.colors(
                focusedTextColor = Color.White
            )
        )
    ) {

    }
}

@Composable
fun UserCard(
    user: AppUser,
    onDelete: () -> Unit,
    onMakeAdmin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(FondoPrincipal, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text("Nombre: ${user.name}", style = MaterialTheme.typography.bodyLarge)
        Text("Correo: ${user.email}", style = MaterialTheme.typography.bodyMedium)
        Text("Rol: ${if (user.isAdmin) "Administrador" else "Usuario"}")

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onDelete) {
                Text("Eliminar")
            }
            if (!user.isAdmin) {
                Button(onClick = onMakeAdmin) {
                    Text("Hacer Admin")
                }
            }
        }
    }
}