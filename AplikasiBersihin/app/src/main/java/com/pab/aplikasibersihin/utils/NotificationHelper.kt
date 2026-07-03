package com.pab.aplikasibersihin.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.pab.aplikasibersihin.R

class NotificationHelper(private val context: Context) {
    fun showActiveOrderNotification(orderId: Long, statusText: String, stepIndex: Int, maxSteps: Int) {
        // Disabled
    }

    fun showCompletedOrderNotification(orderId: Long) {
        // Disabled
    }

    fun cancelOrderNotification(orderId: Long) {
        // Disabled
    }

    fun cancelAllNotifications() {
        // Disabled
    }
}
