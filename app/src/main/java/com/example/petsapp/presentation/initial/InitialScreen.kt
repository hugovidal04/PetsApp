package com.example.petsapp.presentation.initial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petsapp.R
import com.example.petsapp.presentation.components.NormalText
import com.example.petsapp.ui.theme.Principal

@Preview
@Composable
fun InitialScreen(navigateToLogin:() -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Principal)
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            painter = painterResource(id = R.drawable.yorkshire_inicio),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.weight(1f))
        NormalText(
            text = stringResource(id = R.string.bienvenida), fontSize = 40.sp,
            color = Color.Black, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        NormalText(
            text = stringResource(id = R.string.llevar_registro), fontSize = 24.sp,
            color = Color.Black, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Arrow(onNavigateToLogin = navigateToLogin)
        Spacer(modifier = Modifier.weight(1f))
    }
}