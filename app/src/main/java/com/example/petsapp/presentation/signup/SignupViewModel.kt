package com.example.petsapp.presentation.signup

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignupViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    fun signup(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        //termsAccepted: Boolean,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        when {
            name.isBlank() -> {
                onFailure("El nombre no puede estar vacío.")
                return
            }

            email.isBlank() -> {
                onFailure("El correo no puede estar vacío.")
                return
            }

            password.length < 6 -> {
                onFailure("La contraseña debe tener al menos 6 caracteres.")
                return
            }

            password != confirmPassword -> {
                onFailure("Las contraseñas no coinciden.")
                return
            }
            /*!termsAccepted -> {
                onFailure("Debes aceptar los términos y condiciones.")
                return
            }*/
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println("Registro exitoso")
                    onSuccess()
                } else {
                    val errorMessage = task.exception?.message ?: "Error desconocido"
                    println("Error en registro: $errorMessage")
                    onFailure(errorMessage)
                }
            }
    }
}