package com.example.watchbatterynotify.presentation

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.watchbatterynotify.R

object NotificationHelper {

    private const val CHANNEL_ID = "battery_alert_channel"
    private const val NOTIFICATION_ID = 101

    fun createNotificationChannel(context: Context) {
            val name = "Battery Alerts"
            val descriptionText = "Notifications for low battery levels"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

    }

    fun showBatteryLowNotification(context: Context, batteryPct: Int) {
        // Ensure the notification channel is created
        createNotificationChannel(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_battery_alert) // Replace with your own icon
            .setContentTitle("Low Battery Alert!")
            .setContentText("Your watch battery is at $batteryPct%. Time to charge!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true) // Dismiss notification when tapped

        // For Wear OS specific features (optional)
        val wearableExtender = NotificationCompat.WearableExtender()
        // .setHintHideIcon(true) // Hide app icon on Wear OS if desired
        // .addAction(NotificationCompat.Action(R.drawable.ic_action_charge, "Charge Now", null)) // Example action
        // .setDismissalId("battery_low_dismissal_id") // Dismiss across devices if paired

        builder.extend(wearableExtender)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}