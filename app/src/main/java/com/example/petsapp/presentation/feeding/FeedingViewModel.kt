package com.example.petsapp.presentation.feeding

import androidx.lifecycle.ViewModel
import com.example.petsapp.model.Feeding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FeedingViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _feedingList = MutableStateFlow<List<Feeding>>(emptyList())
    val feedingList: StateFlow<List<Feeding>> = _feedingList

    fun loadFeedingEntries() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users").document(userId)
            .collection("feeding")
            .orderBy("date")
            .addSnapshotListener { snapshot, _ ->
                _feedingList.value = snapshot?.documents?.mapNotNull { doc ->
                    Feeding(
                        id = doc.id,
                        petName = doc.getString("petName") ?: "",
                        date = doc.getString("date") ?: "",
                        time = doc.getString("time") ?: "",
                        food = doc.getString("food") ?: "",
                        notes = doc.getString("notes") ?: ""
                    )
                } ?: emptyList()
            }
    }

    fun addFeedingEntry(entry: Feeding, onResult: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid ?: return onResult(false)

        val data = mapOf(
            "petName" to entry.petName,
            "date" to entry.date,
            "time" to entry.time,
            "food" to entry.food,
            "notes" to entry.notes
        )

        db.collection("users").document(userId)
            .collection("feeding")
            .add(data)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun deleteFeedingEntry(entryId: String, onResult: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid ?: return onResult(false)

        db.collection("users").document(userId)
            .collection("feeding").document(entryId)
            .delete()
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
}
