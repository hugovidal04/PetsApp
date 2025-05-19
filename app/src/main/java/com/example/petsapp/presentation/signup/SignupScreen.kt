package com.example.petsapp.presentation.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petsapp.R
import com.example.petsapp.presentation.components.ButtonComponent
import com.example.petsapp.presentation.components.ListaImagenes
import com.example.petsapp.presentation.components.NormalText
import com.example.petsapp.presentation.components.PasswordField
import com.example.petsapp.presentation.components.TextField
import com.example.petsapp.ui.theme.Blanco
import com.example.petsapp.ui.theme.FondoPrincipal
import com.example.petsapp.ui.theme.Principal

@Composable
fun SingupScreen(
    viewModel: SignupViewModel = viewModel(),
    navigateToHome: () -> Unit = {},
    navigateToLogin: () -> Unit = {}
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Principal)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            ListaImagenes(
                listOf(
                    (painterResource(id = R.drawable.perro)),
                    (painterResource(id = R.drawable.huella)),
                    (painterResource(id = R.drawable.gato)),
                ),
                size = 100.dp
            )
            Spacer(modifier = Modifier.height(50.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                //.weight(1f),
                color = FondoPrincipal,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    NormalText(
                        text = stringResource(id = R.string.crea_cuenta_registro),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Column {
                        NormalText(
                            text = stringResource(id = R.string.nombre),
                            fontSize = 16.sp
                        )
                        TextField(
                            value = name,
                            onValueChange = { name = it },
                            placeholder = { Text("Nombre") }
                        )
                    }
                    Column {
                        NormalText(
                            text = stringResource(id = R.string.email),
                            fontSize = 16.sp
                        )
                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = { Text("Email") }
                        )
                    }
                    Column {
                        NormalText(
                            text = stringResource(id = R.string.contraseña),
                            fontSize = 16.sp
                        )
                        PasswordField(
                            value = password,
                            onValueChange = { password = it },
                        )
                    }
                    Column {
                        NormalText(
                            text = stringResource(id = R.string.repetir_contraseña),
                            fontSize = 16.sp
                        )
                        PasswordField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                        )
                    }
                    Row {
                        Checkbox(
                            checked = termsAccepted,
                            onCheckedChange = { newValue -> termsAccepted = newValue }
                        )
                        NormalText(
                            text = stringResource(id = R.string.terminos),
                            fontSize = 16.sp
                        )
                    }
                    /*errorMessage?.let {
                        Text(text = it, color = Color.Red, fontSize = 14.sp)
                    }*/
                    ButtonComponent(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.signup(
                                name, email, password, confirmPassword, termsAccepted,
                                onSuccess = navigateToHome,
                                onFailure = { msg -> errorMessage = msg }
                            )
                        },
                        text = stringResource(id = R.string.boton_crear_cuenta_user),
                        backgroundColor = Blanco
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        NormalText(
                            text = stringResource(
                                id = R.string.con_cuenta
                            ),
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        NormalText(
                            text = stringResource(
                                id = R.string.boton_iniciar_sesion
                            ),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            onClick = navigateToLogin
                        )
                    }
                }
            }
        }
    }
}