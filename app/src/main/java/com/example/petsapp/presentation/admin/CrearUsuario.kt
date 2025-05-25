package com.example.petsapp.presentation.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreateUserDialog(
    onDismiss: () -> Unit,
    onCreateUser: (name: String, email: String, password: String, adminPassword: String, onFailure: (String) -> Unit) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var adminPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var adminPasswordVisible by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Crear nuevo usuario") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = {
                        Text(
                            "Nombre",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text(
                            "Email",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = {
                        Text(
                            "Contraseña",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image =
                            if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(image, contentDescription = "Mostrar/Ocultar contraseña")
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                OutlinedTextField(
                    value = adminPassword,
                    onValueChange = { adminPassword = it },
                    label = {
                        Text(
                            "Tu contraseña (admin)",
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (adminPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image =
                            if (adminPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                        IconButton(onClick = { adminPasswordVisible = !adminPasswordVisible }) {
                            Icon(image, contentDescription = "Mostrar/Ocultar contraseña")
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onCreateUser(name, email, password, adminPassword) { error ->
                    errorMessage = error
                }
            }) {
                Text("Crear")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}