package com.example.petsapp.presentation.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel : ViewModel() {

    private val PREFS_NAME = "settings"
    private val KEY_NOTIF = "notifications_enabled"

    private val _notificationsEnabled = MutableStateFlow(false)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled

    fun loadPreferences(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        _notificationsEnabled.value = prefs.getBoolean(KEY_NOTIF, false)
    }

    fun toggleNotifications(context: Context, enabled: Boolean) {
        _notificationsEnabled.value = enabled
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_NOTIF, enabled).apply()
    }

    fun deleteUser(onResult: (Boolean) -> Unit) {
        FirebaseAuth.getInstance().currentUser?.delete()?.addOnCompleteListener {
            onResult(it.isSuccessful)
        }
    }
}