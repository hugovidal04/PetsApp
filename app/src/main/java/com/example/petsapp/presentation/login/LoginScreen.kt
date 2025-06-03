package com.example.petsapp.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petsapp.R
import com.example.petsapp.presentation.components.ButtonComponent
import com.example.petsapp.presentation.components.ImageList
import com.example.petsapp.presentation.components.NormalText
import com.example.petsapp.presentation.components.PasswordField
import com.example.petsapp.presentation.components.TextField
import com.example.petsapp.ui.theme.Blanco
import com.example.petsapp.ui.theme.FondoPrincipal
import com.example.petsapp.ui.theme.Principal

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    navigateToHome: () -> Unit = {},
    navigateToSignup: () -> Unit = {},
    navigateToAdmin: () -> Unit = {}
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

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
            Spacer(modifier = Modifier.height(60.dp))
            ImageList(
                listOf(painterResource(id = R.drawable.huella)),
                size = 200.dp
            )
            Spacer(modifier = Modifier.height(60.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                color = FondoPrincipal,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    NormalText(
                        text = stringResource(id = R.string.bienvenida_login),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Column {
                        NormalText(
                            text = stringResource(id = R.string.email),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Left
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
                            fontSize = 16.sp,
                            textAlign = TextAlign.Left
                        )
                        PasswordField(value = password, onValueChange = { password = it })
                    }
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    ButtonComponent(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.login(
                                email = email,
                                password = password,
                                onSuccessAdmin = navigateToAdmin,
                                onSuccessUser = navigateToHome,
                                onFailure = { error ->
                                    errorMessage = error
                                }
                            )
                        },
                        text = stringResource(id = R.string.boton_iniciar_sesion),
                        backgroundColor = Blanco
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        NormalText(
                            text = stringResource(
                                id = R.string.sin_cuenta
                            ),
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        NormalText(
                            text = stringResource(
                                id = R.string.crea_cuenta_inicio
                            ),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            onClick = navigateToSignup
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f)) //Con esto el contenido va hacia arriba cuando no hay scroll
                }
            }
        }
    }
}