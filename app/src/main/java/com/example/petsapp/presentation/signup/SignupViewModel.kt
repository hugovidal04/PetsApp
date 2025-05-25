package com.example.petsapp.presentation.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun signup(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        termsAccepted: Boolean,
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

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                onFailure("El correo no tiene un formato válido.")
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

            !termsAccepted -> {
                onFailure("Debes aceptar los términos y condiciones.")
                return
            }
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val userId = authResult.user?.uid
                if (userId == null) {
                    onFailure("Error al obtener ID del usuario.")
                    return@addOnSuccessListener
                }
                val userData = hashMapOf(
                    "uid" to userId,
                    "name" to name,
                    "email" to email,
                    "isAdmin" to false,
                    "acceptedTerms" to true
                )
                db.collection("users").document(userId).set(userData)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener {
                        onFailure("Error al guardar datos:")
                    }
            }
            .addOnFailureListener {
                onFailure("Error al registrar:")
            }
    }
}