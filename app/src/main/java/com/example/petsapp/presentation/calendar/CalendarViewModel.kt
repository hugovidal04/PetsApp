package com.example.petsapp.presentation.calendar

import androidx.lifecycle.ViewModel
import com.example.petsapp.model.PetEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CalendarViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _events = MutableStateFlow<List<PetEvent>>(emptyList())
    val events: StateFlow<List<PetEvent>> = _events

    fun loadEvents() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users").document(userId)
            .collection("events")
            .addSnapshotListener { snapshot, _ ->
                _events.value = snapshot?.documents?.mapNotNull { doc ->
                    PetEvent(
                        id = doc.id,
                        date = doc.getString("date") ?: "",
                        petName = doc.getString("petName") ?: "",
                        title = doc.getString("title") ?: "",
                        description = doc.getString("description") ?: "",
                        type = doc.getString("type") ?: ""
                    )
                } ?: emptyList()
            }
    }

    fun addEvent(event: PetEvent, onResult: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid ?: return onResult(false)

        val data = mapOf(
            "date" to event.date,
            "petName" to event.petName,
            "title" to event.title,
            "description" to event.description,
            "type" to event.type
        )

        db.collection("users").document(userId)
            .collection("events")
            .add(data)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
}