package com.example.petsapp.presentation.signup

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
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    val userMap = hashMapOf(
                        "name" to name,
                        "email" to email,
                        "isAdmin" to false,
                        "acceptedTerms" to true
                    )
                    userId?.let {
                        db.collection("users").document(it).set(userMap)
                            .addOnSuccessListener {
                                onSuccess()
                            }
                            .addOnFailureListener { e ->
                                onFailure("Error al guardar datos: ${e.message}")
                            }
                    } ?: onFailure("Error al obtener ID del usuario")
                } else {
                    onFailure("Error al registrar: ${task.exception?.message}")
                }
            }
    }
}