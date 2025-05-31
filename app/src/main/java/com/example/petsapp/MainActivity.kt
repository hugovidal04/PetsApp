package com.example.petsapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.petsapp.ui.theme.PetsAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    private val PREFS_NAME = "settings"
    private val KEY_NOTIFICATIONS = "notifications_enabled"
    private val CHANNEL_ID = "evento_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel()

        setContent {
            navHostController = rememberNavController()
            PetsAppTheme {
                Surface {
                    NavigationApp(navHostController)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val prefs: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val notificationsEnabled = prefs.getBoolean(KEY_NOTIFICATIONS, false)

        if (notificationsEnabled) {
            fetchEventsAndNotify()
        }
    }

    @SuppressLint("WrongConstant")
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Canal de Eventos"
            val description = "Notificaciones para eventos del calendario"
            val importance = NotificationManagerCompat.IMPORTANCE_HIGH
            val channel = android.app.NotificationChannel(CHANNEL_ID, name, importance).apply {
                this.description = description
            }
            val manager =
                getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun fetchEventsAndNotify() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()

        // 1) Calculamos "hoy" con formato yyyy-MM-dd
        val today = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayStr = sdf.format(today.time)

        db.collection("users")
            .document(userId)
            .collection("events")
            .get()
            .addOnSuccessListener { snapshot ->
                snapshot.documents.forEach { doc ->
                    val dateString = doc.getString("date") ?: return@forEach

                    // 2) Filtramos: solo notificamos si fecha >= hoy
                    if (dateString < todayStr) return@forEach

                    val petName = doc.getString("petName") ?: "Desconocida"
                    val title = doc.getString("title") ?: "Sin título"
                    val type = doc.getString("type") ?: ""
                    val notifTitle = "$title ($type)"
                    val notifText = "Mascota: $petName  •  Fecha: $dateString"

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val permiso = ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.POST_NOTIFICATIONS
                        )
                        if (permiso == PackageManager.PERMISSION_GRANTED) {
                            NotificationManagerCompat.from(this).notify(
                                (0..9999).random(),
                                NotificationCompat.Builder(this, CHANNEL_ID)
                                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                                    .setContentTitle(notifTitle)
                                    .setContentText(notifText)
                                    .setStyle(NotificationCompat.BigTextStyle().bigText(notifText))
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .build()
                            )
                        }
                    } else {
                        NotificationManagerCompat.from(this).notify(
                            (0..9999).random(),
                            NotificationCompat.Builder(this, CHANNEL_ID)
                                .setSmallIcon(android.R.drawable.ic_dialog_info)
                                .setContentTitle(notifTitle)
                                .setContentText(notifText)
                                .setStyle(NotificationCompat.BigTextStyle().bigText(notifText))
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .build()
                        )
                    }
                }
            }
    }
}
