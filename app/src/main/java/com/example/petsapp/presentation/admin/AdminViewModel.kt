package com.example.petsapp.presentation.admin

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.petsapp.model.AppUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AdminViewModel : ViewModel() {


    private val db = FirebaseFirestore.getInstance()
    private val _users = MutableStateFlow<List<AppUser>>(emptyList())
    val users: StateFlow<List<AppUser>> = _users

    fun loadUsers() {
        db.collection("users")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("AdminViewModel", "Error al escuchar cambios en usuarios", error)
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
                }
            }
    }

    init {
        Log.d("AdminViewModel", "ViewModel iniciado")
        loadUsers()
    }

    fun deleteUser(userId: String) {
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
}