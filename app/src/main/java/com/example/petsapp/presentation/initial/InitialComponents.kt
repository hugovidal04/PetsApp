package com.example.petsapp.presentation.initial

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NormalText(
    text: String,
    fontSize: TextUnit = 48.sp,
    color: Color = Color.Black,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        text = text,
        style = TextStyle(
            fontSize = fontSize,
            color = color,
            fontWeight = fontWeight
        ),
        textAlign = TextAlign.Center
    )
}

fun Arrow() {
    //Todo crear componente flecha
}