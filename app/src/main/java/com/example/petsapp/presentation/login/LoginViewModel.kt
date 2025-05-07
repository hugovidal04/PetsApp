package com.example.petsapp.presentation.login

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Login exitoso")
                    onSuccess()
                } else {
                    println("Error en login: ${task.exception?.message}")
                }
            }
    }

}