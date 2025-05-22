package com.example.petsapp.presentation.admin

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.petsapp.model.AppUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.firebase.auth.FirebaseAuth


class AdminViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val _users = MutableStateFlow<List<AppUser>>(emptyList())
    val users: StateFlow<List<AppUser>> = _users

    fun loadUsers() {
        db.collection("users")
            .addSnapshotListener { snapshot, error ->
                error?.let {
                    Log.d("ErrorEsperado", "Error temporal: ${it.message}")
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val userList = snapshot.documents.mapNotNull { doc ->
                        try {
                            AppUser(
                                uid = doc.id,
                                name = doc.getString("name") ?: "",
                                email = doc.getString("email") ?: "",
                                isAdmin = doc.getBoolean("isAdmin") ?: false,
                                acceptedTerms = doc.getBoolean("acceptedTerms") ?: false
                            )
                        } catch (e: Exception) {
                            Log.e("UserData", "Error mapping user ${doc.id}", e)
                            null
                        }
                    }
                    _users.value = userList
                    Log.d("AdminViewModel", "Usuarios cargados:")
                    userList.forEach {
                        Log.d("AdminViewModel", "UID: ${it.uid}, nombre: ${it.name}")
                    }
                }
            }
    }

    init {
        Log.d("AdminViewModel", "ViewModel iniciado")
        loadUsers()
    }

    fun deleteUser(userId: String) {
        Log.d("AdminViewModel", "Eliminando usuario con ID: $userId")
        db.collection("users").document(userId).delete().addOnSuccessListener {
            loadUsers()
        }
    }

    fun makeAdmin(userId: String) {
        db.collection("users").document(userId)
            .update("isAdmin", true)
            .addOnSuccessListener {
                loadUsers()
            }
    }

    fun createUserAsAdmin(
        name: String,
        email: String,
        password: String,
        adminPassword: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (name.isBlank() || email.isBlank() || password.length < 6) {
            onFailure("Nombre, correo o contraseña inválidos.")
            return
        }

        val currentUser = auth.currentUser

        auth.createUserWithEmailAndPassword(email, password) //Al hacer esto se cierra la sesión de admin
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val newUser = task.result.user
                    val newUserId = newUser?.uid

                    if (newUserId != null) {
                        val userMap = hashMapOf(
                            "uid" to newUserId,
                            "name" to name,
                            "email" to email,
                            "isAdmin" to false,
                            "acceptedTerms" to true
                        )

                        db.collection("users").document(newUserId).set(userMap)
                            .addOnSuccessListener {
                                currentUser?.email?.let { adminEmail -> //Con let y '?' evitamos poner ifs para tener en cuenta si algo es null
                                    auth.signOut()
                                    // Se vuelve a loguear el admin porque firebase no puede tener dos sesiones abiertas
                                    auth.signInWithEmailAndPassword(
                                        adminEmail,
                                        adminPassword
                                    )

                                        .addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                onSuccess()
                                            } else {
                                                onFailure("Usuario creado pero error al volver a iniciar sesión.")
                                            }
                                        }
                                }
                            }
                            .addOnFailureListener { e ->
                                onFailure("Error al guardar usuario: ${e.message}")
                            }
                    } else {
                        onFailure("Error al obtener el ID del nuevo usuario.")
                    }
                } else {
                    onFailure("Error al crear usuario: ${task.exception?.message}")
                }
            }
    }
}