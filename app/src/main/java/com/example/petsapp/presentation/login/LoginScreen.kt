package com.example.petsapp.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.petsapp.R
import com.example.petsapp.presentation.components.FondoBlanco
import com.example.petsapp.presentation.components.ListaImagenes
import com.example.petsapp.ui.theme.Principal

@Preview
@Composable
fun LoginScreen() {
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Principal),
                //.padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            ListaImagenes(
                listOf(
                    painterResource(R.drawable.huella),
                ), size = 200.dp
            )
            Spacer(modifier = Modifier.height(50.dp))
            FondoBlanco(height = 500.dp)
        }

    }
}