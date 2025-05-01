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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petsapp.R
import com.example.petsapp.presentation.components.ListaImagenes
import com.example.petsapp.presentation.components.NormalText
import com.example.petsapp.ui.theme.Principal

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LoginScreen() {
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
                color = Color.White,
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
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    Column {
                        NormalText(
                            text = stringResource(id = R.string.contraseña),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Left
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}