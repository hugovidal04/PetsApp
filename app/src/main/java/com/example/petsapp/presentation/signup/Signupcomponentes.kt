package com.example.petsapp.presentation.signup

import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable

@Composable
fun Checkbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
    )
}