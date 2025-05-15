package com.example.petsapp.presentation.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petsapp.R
import com.example.petsapp.presentation.components.ButtonComponent
import com.example.petsapp.presentation.components.NormalText

@Preview
@Composable
fun AdminScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NormalText(
            text = stringResource(id = R.string.panel_admin),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Column(
            modifier = Modifier.weight(1f),
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Cards de los usuarios
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            ButtonComponent(
                onClick = {

                },
                text = stringResource(id = R.string.boton_cerrar_sesion_admin)
            )
            Spacer(modifier = Modifier.width(8.dp))
            ButtonComponent(
                onClick = {

                },
                text = stringResource(id = R.string.boton_crear_cuenta_admin)
            )
        }
    }
}