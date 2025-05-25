package com.example.petsapp.presentation.admin

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.petsapp.model.AppUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class AdminViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val _users = MutableStateFlow<List<AppUser>>(emptyList())
    val users: StateFlow<List<AppUser>> = _users

    fun loadUsers() {
        db.collection("users")
            .addSnapshotListener { snapshot, error ->
                error?.let {
                    Log.d(
                        "ErrorEsperado",
                        "Error temporal: ${it.message}"
                    ) //Capturamos el error temporal
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

            adminPassword.isBlank() -> {
                onFailure("La contraseña de admin no puede estar vacía")
                return
            }
        }

        val adminEmail = auth.currentUser?.email
        if (adminEmail == null) {
            onFailure("No se puede obtener el correo del administrador.")
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val userId = result.user?.uid ?: ""
                val userMap = mapOf(
                    "uid" to userId,
                    "name" to name,
                    "email" to email,
                    "isAdmin" to false,
                    "acceptedTerms" to true
                )

                db.collection("users").document(userId).set(userMap)
                    .addOnSuccessListener {
                        auth.signOut()
                        auth.signInWithEmailAndPassword(adminEmail, adminPassword)
                            .addOnSuccessListener { onSuccess() }
                            .addOnFailureListener { onFailure("Usuario creado, pero error al volver a iniciar sesión.") }
                    }
                    .addOnFailureListener {
                        onFailure("Error al guardar datos:")
                    }
            }
            .addOnFailureListener {
                onFailure("Error al crear usuario:")
            }
    }
}