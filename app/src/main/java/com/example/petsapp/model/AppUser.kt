package com.example.petsapp.model

data class AppUser(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val isAdmin: Boolean = false,
    val acceptedTerms: Boolean = false
)
