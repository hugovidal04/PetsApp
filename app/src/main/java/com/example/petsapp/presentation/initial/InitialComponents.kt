package com.example.petsapp.presentation.initial

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Arrow(onNavigateToLogin: () -> Unit) {
    Button(
        onClick = {
            onNavigateToLogin()
        },
        modifier = Modifier
            .height(80.dp)
            .width(80.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowForward,
            modifier = Modifier
                .fillMaxSize(),
            contentDescription = "",
            tint = Color.Black
        )
    }
}

