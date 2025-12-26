package com.islamicapp.siraathalmusthaqeem.screen.qurannotification

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.islamicapp.domain.qurannotification.QuranReminders
import java.time.LocalDate

class QuranReminderReceiver : BroadcastReceiver() {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getIntExtra("REMINDER_ID", -1)

        if (reminderId == -1) {
            Log.e("QuranReminder", "Invalid reminder ID")
            return
        }

        val reminder = QuranReminders.ALL_REMINDERS.find { it.id == reminderId }

        if (reminder == null) {
            Log.e("QuranReminder", "Reminder not found for ID: $reminderId")
            return
        }

        // Check if today is a valid day for this reminder
        val today = LocalDate.now().dayOfWeek
        if (!reminder.daysOfWeek.contains(today)) {
            Log.d("QuranReminder", "Skipping reminder for ${reminder.surahName} - not scheduled for $today")
            return
        }

        // Show notification
        val notificationManager = QuranNotificationManager(context)
        notificationManager.showNotification(reminder)

        Log.d("QuranReminder", "Notification shown for ${reminder.surahName}")
    }
}