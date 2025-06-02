package com.example.petsapp.model

data class PetEvent(
    val id: String = "",
    val date: String = "",
    val petName: String = "",
    val title: String = "",
    val description: String = "",
    val type: String = "" //vacuna, cita, tratamiento
)
