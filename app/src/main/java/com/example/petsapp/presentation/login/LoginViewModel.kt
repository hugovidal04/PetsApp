package com.example.petsapp.presentation.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun login(
        email: String,
        password: String,
        onSuccessAdmin: () -> Unit,
        onSuccessUser: () -> Unit,
        onFailure: (String) -> Unit = {}
    ) {
        when {
            email.isBlank() -> {
                onFailure("El email no puede estar vacío")
                return
            }

            password.isBlank() -> {
                onFailure("La contraseña no puede estar vacía")
                return
            }
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val userId = auth.currentUser?.uid
                if (userId == null) {
                    onFailure("No se pudo obtener el ID del usuario.")
                    return@addOnSuccessListener
                }

                db.collection("users").document(userId).get()
                    .addOnSuccessListener { document ->
                        if (!document.exists()) {
                            onFailure("El documento del usuario no existe.")
                            return@addOnSuccessListener
                        }

                        val isAdmin = document.getBoolean("isAdmin") ?: false
                        if (isAdmin) {
                            onSuccessAdmin()
                        } else {
                            onSuccessUser()
                        }
                    }
                    .addOnFailureListener {
                        onFailure("Error al obtener datos del usuario:")
                    }
            }
            .addOnFailureListener {
                onFailure("Contraseña o correo incorrecto")
            }
    }
}