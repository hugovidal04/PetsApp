package com.example.petsapp.presentation.pets

import androidx.lifecycle.ViewModel
import com.example.petsapp.model.Pet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PetsViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets: StateFlow<List<Pet>> = _pets

    fun loadPets() {
        val currentUser = auth.currentUser ?: return

        db.collection("users").document(currentUser.uid).collection("pets")
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                val petList = snapshot?.documents?.mapNotNull { doc ->
                    Pet(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        species = doc.getString("species") ?: "",
                        breed = doc.getString("breed") ?: "",
                        birthDate = doc.getString("birthDate") ?: "",
                        gender = doc.getString("gender") ?: "",
                    )

                } ?: emptyList()
                _pets.value = petList
            }
    }

    fun addOrUpdatePet(pet: Pet, onResult: (String) -> Unit) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            onResult("No se encontró el usuario. Inicia sesión de nuevo.")
            return
        }

        val petMap = mapOf(
            "name" to pet.name,
            "species" to pet.species,
            "breed" to pet.breed,
            "birthDate" to pet.birthDate,
            "gender" to pet.gender,
            "ownerId" to currentUser.uid,
        )

        val petsCollection = db.collection("users").document(currentUser.uid).collection("pets")

        if (pet.id.isEmpty()) {
            //Crear nuevo
            petsCollection.add(petMap)
                .addOnSuccessListener { onResult("") }
                .addOnFailureListener { e -> onResult("Error al añadir la mascota: ${e.message}") }
        } else {
            //Actualizar existente
            petsCollection.document(pet.id).update(petMap)
                .addOnSuccessListener { onResult("") }
                .addOnFailureListener { e -> onResult("Error al actualizar la mascota: ${e.message}") }
        }
    }

    fun deletePet(petId: String, onResult: (String) -> Unit) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            onResult("No se encontró el usuario. Inicia sesión de nuevo.")
            return
        }

        db.collection("users").document(currentUser.uid).collection("pets")
            .document(petId).delete()
            .addOnSuccessListener { onResult("") }
            .addOnFailureListener { e -> onResult("Error al eliminar la mascota: ${e.message}") }
    }
}