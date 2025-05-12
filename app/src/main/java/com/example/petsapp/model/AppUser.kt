package com.example.petsapp.model

data class AppUser(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "user",
    val active: Boolean = true,
    val acceptedTerms: Boolean = false
)
