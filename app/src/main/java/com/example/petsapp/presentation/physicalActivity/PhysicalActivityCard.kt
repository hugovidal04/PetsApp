package com.example.petsapp.presentation.physicalActivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.petsapp.model.PhysicalActivity
import com.example.petsapp.ui.theme.Blanco
import com.example.petsapp.ui.theme.ColorTexto

@Composable
fun PhysicalActivityCard(
    entry: PhysicalActivity,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Blanco,
            contentColor = ColorTexto
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Mascota: ${entry.petName}", fontWeight = FontWeight.Bold)
            Text("Fecha: ${entry.date}")
            Text("Duración: ${entry.duration}")
            Text("Tipo: ${entry.activityType}")
            Text("Notas: ${entry.notes}")

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onDelete,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
            ) {
                Icon(Icons.Outlined.Delete, contentDescription = "Eliminar")
                Spacer(Modifier.width(4.dp))
                Text("Eliminar")
            }
        }
    }
}