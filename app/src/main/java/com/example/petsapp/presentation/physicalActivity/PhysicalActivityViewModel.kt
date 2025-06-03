package com.example.petsapp.presentation.physicalActivity

import androidx.lifecycle.ViewModel
import com.example.petsapp.model.PhysicalActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PhysicalActivityViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _activities = MutableStateFlow<List<PhysicalActivity>>(emptyList())
    val activities: StateFlow<List<PhysicalActivity>> = _activities

    fun loadActivities() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users").document(userId)
            .collection("physical_activity")
            .addSnapshotListener { snapshot, _ ->
                _activities.value = snapshot?.documents?.mapNotNull { doc ->
                    PhysicalActivity(
                        id = doc.id,
                        petName = doc.getString("petName") ?: "",
                        date = doc.getString("date") ?: "",
                        duration = doc.getString("duration") ?: "",
                        activityType = doc.getString("activityType") ?: "",
                        notes = doc.getString("notes") ?: ""
                    )
                } ?: emptyList()
            }
    }

    fun addActivity(activity: PhysicalActivity, onResult: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid ?: return onResult(false)

        val data = mapOf(
            "petName" to activity.petName,
            "date" to activity.date,
            "duration" to activity.duration,
            "activityType" to activity.activityType,
            "notes" to activity.notes
        )

        db.collection("users").document(userId)
            .collection("physical_activity")
            .add(data)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun deleteActivity(id: String, onResult: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid ?: return onResult(false)

        db.collection("users").document(userId)
            .collection("physical_activity").document(id)
            .delete()
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }
}