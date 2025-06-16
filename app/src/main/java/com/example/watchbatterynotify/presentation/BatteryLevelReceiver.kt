package com.example.watchbatterynotify.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.util.Log

class BatteryLevelReceiver : BroadcastReceiver() {

    private val tag = "BatteryLevelReceiver"
    private val batteryThreshold = 80 // Example: Notify at 20% battery or below

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BATTERY_CHANGED) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val batteryPct = if (level != -1 && scale != -1) {
                (level * 100 / scale.toFloat()).toInt()
            } else {
                -1
            }

            Log.d(tag, "Battery level: $batteryPct%")

            if (batteryPct != -1 && batteryPct <= batteryThreshold) {
                // Trigger notification
                sendBatteryNotification(context, batteryPct)
            }
        }
    }

    private fun sendBatteryNotification(context: Context, batteryPct: Int) {
        // Implement your notification logic here
        // Call a function or start a service to show the notification
        NotificationHelper.showBatteryLowNotification(context, batteryPct)
    }
}
