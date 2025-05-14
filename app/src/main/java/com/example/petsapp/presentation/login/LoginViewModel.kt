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
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        db.collection("users").document(userId).get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    val isAdmin = document.getBoolean("isAdmin") ?: false
                                    if (isAdmin) {
                                        onSuccessAdmin()
                                    } else {
                                        onSuccessUser()
                                    }
                                } else {
                                    onFailure("El documento del usuario no existe.")
                                }
                            }
                            .addOnFailureListener { e ->
                                onFailure("Error al obtener datos del usuario: ${e.message}")
                            }
                    } else {
                        onFailure("No se pudo obtener el ID del usuario.")
                    }
                } else {
                    onFailure("Error al iniciar sesión: ${task.exception?.message}")
                }
            }
    }
}