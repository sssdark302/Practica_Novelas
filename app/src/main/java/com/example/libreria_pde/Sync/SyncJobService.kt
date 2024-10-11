package com.example.libreria_pde.Sync

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.libreria_pde.R

class SyncService : JobService() {

    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.d("SyncJobService", "Sincronización iniciada")
        // Lógica de sincronización aquí
        return true
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.d("SyncJobService", "Sincronización detenida")
        return true
    }

    val CHANNEL_ID = "sync_notifications_channel"

    // Crear un canal de notificación para sincronización
    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val name = "Sync Notifications"
            val descriptionText = "Notificaciones de sincronización de datos"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Enviar notificación de sincronización
    private fun sendNotification(title: String, message: String) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_sync)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, builder.build())
        }
    }
}