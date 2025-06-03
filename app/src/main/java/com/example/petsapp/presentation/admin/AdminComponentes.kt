package com.example.petsapp.presentation.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petsapp.R
import com.example.petsapp.model.AppUser
import com.example.petsapp.ui.theme.Blanco
import com.example.petsapp.ui.theme.ColorTexto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComp(
    searchQuery: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onValueChange,
        modifier = modifier,
        label = {
            Text(
                text = stringResource(id = R.string.correo_nombre),
                fontSize = 14.sp,
                color = ColorTexto
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Blanco,
            focusedBorderColor = ColorTexto,
            unfocusedBorderColor = ColorTexto,
            focusedTextColor = ColorTexto,
            unfocusedTextColor = ColorTexto,
        )
    )
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
            .background(Blanco, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text("Nombre: ${user.name}", color = ColorTexto)
        Text("Correo: ${user.email}", color = ColorTexto)
        Text("Rol: ${user.isAdmin}", color = ColorTexto)

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            if (!user.isAdmin) {
                Button(onClick = onDelete) {
                    Text("Eliminar")
                }
            }
            if (!user.isAdmin) {
                Button(onClick = onMakeAdmin) {
                    Text("Hacer Admin")
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}