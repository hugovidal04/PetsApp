package com.example.petsapp.presentation.diary

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
import com.example.petsapp.model.DiaryEntry
import com.example.petsapp.ui.theme.Blanco
import com.example.petsapp.ui.theme.ColorTexto

@Composable
fun DiaryCard(
    entry: DiaryEntry,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Blanco, contentColor = ColorTexto)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Mascota: ${entry.petName}", fontWeight = FontWeight.Bold)
            Text("Fecha: ${entry.date}", fontWeight = FontWeight.Medium)
            Text("Título: ${entry.title}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(entry.content)

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
