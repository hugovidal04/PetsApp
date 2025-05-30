package com.example.petsapp.presentation.diary

import androidx.lifecycle.ViewModel
import com.example.petsapp.model.DiaryEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DiaryViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _diaryList = MutableStateFlow<List<DiaryEntry>>(emptyList())
    val diaryList: StateFlow<List<DiaryEntry>> = _diaryList

    fun loadDiaryEntries() {
        val userId = auth.currentUser?.uid ?: return

        db.collection("users").document(userId)
            .collection("diary")
            .orderBy("date")
            .addSnapshotListener { snapshot, _ ->
                _diaryList.value = snapshot?.documents?.mapNotNull { doc ->
                    DiaryEntry(
                        id = doc.id,
                        petName = doc.getString("petName") ?: "",
                        date = doc.getString("date") ?: "",
                        title = doc.getString("title") ?: "",
                        content = doc.getString("content") ?: ""
                    )
                } ?: emptyList()
            }
    }

    fun addDiaryEntry(entry: DiaryEntry, onResult: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid ?: return onResult(false)

        val data = mapOf(
            "petName" to entry.petName,
            "date" to entry.date,
            "title" to entry.title,
            "content" to entry.content
        )

        db.collection("users").document(userId)
            .collection("diary")
            .add(data)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun deleteDiaryEntry(entryId: String, onResult: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid ?: return onResult(false)

        db.collection("users").document(userId)
            .collection("diary")
            .document(entryId)
            .delete()
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

}
