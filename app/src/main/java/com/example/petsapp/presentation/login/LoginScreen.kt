package com.example.petsapp.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petsapp.R
import com.example.petsapp.presentation.components.EmailField
import com.example.petsapp.presentation.components.ListaImagenes
import com.example.petsapp.presentation.components.LoginButton
import com.example.petsapp.presentation.components.NormalText
import com.example.petsapp.presentation.components.PasswordField
import com.example.petsapp.ui.theme.FondoPrincipal
import com.example.petsapp.ui.theme.Principal

//@Preview
@Composable
fun LoginScreen(viewModel: LoginViewModel) {

    val email :String by viewModel.email.observeAsState(initial = "")
    val password :String by viewModel.password.observeAsState(initial = "")
    val loginEnable:Boolean by viewModel.loginEnable.observeAsState(initial = false)

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Principal)
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            ListaImagenes(
                listOf(painterResource(id = R.drawable.huella)),
                size = 200.dp
            )
            Spacer(modifier = Modifier.height(60.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = FondoPrincipal,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp) //Mirar de cambiar por un spacer al final de cada campo
                ) {
                    NormalText(
                        text = stringResource(id = R.string.bienvenida_login),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Column {
                        NormalText(
                            text = stringResource(id = R.string.email),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Left
                        )
                        EmailField(email, {viewModel.onLoginChanged(it, password)})
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    Column {
                        NormalText(
                            text = stringResource(id = R.string.contraseña),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Left
                        )
                        PasswordField(password) {viewModel.onLoginChanged(email, it)}
                        Spacer(modifier = Modifier.height(4.dp))
                        LoginButton(loginEnable) {}
                    }
                }
            }
        }
    }
}