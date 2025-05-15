package com.example.petsapp.presentation.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petsapp.R
import com.example.petsapp.presentation.components.BarraBusqueda
import com.example.petsapp.presentation.components.ButtonComponent
import com.example.petsapp.presentation.components.NormalText
import com.example.petsapp.ui.theme.FondoPrincipal

@Preview
@Composable
fun AdminScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = FondoPrincipal)
            .padding(28.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NormalText(
            text = stringResource(id = R.string.panel_admin),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        BarraBusqueda()
        /*Column(
            //modifier = Modifier.weight(1f),
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Cards de los usuarios
        }*/
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
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