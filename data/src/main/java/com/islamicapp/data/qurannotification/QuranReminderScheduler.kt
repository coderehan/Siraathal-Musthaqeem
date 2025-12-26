package com.islamicapp.data.qurannotification

import androidx.annotation.RequiresApi
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.islamicapp.domain.qurannotification.QuranReminder
import com.islamicapp.domain.qurannotification.QuranReminders
import java.util.Calendar

class QuranReminderScheduler(
    private val context: Context,
    private val receiverClass: Class<*>
) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    /**
     * Schedule all Quran reminders at once
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleAllReminders() {
        QuranReminders.ALL_REMINDERS.forEach { reminder ->
            if (reminder.isEnabled) {
                scheduleReminder(reminder)
            }
        }
        Log.d("QuranReminderScheduler", "All reminders scheduled successfully")
    }

    /**
     * Schedule a single reminder
     */
    fun scheduleReminder(reminder: QuranReminder) {
        val intent = Intent(context, receiverClass).apply {
            putExtra("REMINDER_ID", reminder.id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, reminder.hour)
            set(Calendar.MINUTE, reminder.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // If the time has passed today, schedule for tomorrow
            if (timeInMillis < System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        // For Android 12+ (API 31+), we need to check if we can schedule exact alarms
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                scheduleExactAlarm(calendar, pendingIntent)
            } else {
                // Fallback to inexact alarm if permission not granted
                scheduleInexactAlarm(calendar, pendingIntent)
                Log.w(
                    "QuranReminderScheduler",
                    "Exact alarm permission not granted, using inexact alarm"
                )
            }
        } else {
            scheduleExactAlarm(calendar, pendingIntent)
        }

        Log.d(
            "QuranReminderScheduler",
            "Scheduled ${reminder.surahName} at ${reminder.hour}:${
                String.format(
                    "%02d",
                    reminder.minute
                )
            }"
        )
    }

    /**
     * Schedule exact repeating alarm (most accurate)
     */
    private fun scheduleExactAlarm(calendar: Calendar, pendingIntent: PendingIntent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }

    /**
     * Schedule inexact alarm (fallback)
     */
    private fun scheduleInexactAlarm(calendar: Calendar, pendingIntent: PendingIntent) {
        alarmManager.setWindow(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES / 2, // 7.5 min window
            pendingIntent
        )
    }

    /**
     * Cancel a specific reminder
     */
    fun cancelReminder(reminderId: Int) {
        val intent = Intent(context, receiverClass)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()

        Log.d("QuranReminderScheduler", "Cancelled reminder ID: $reminderId")
    }

    /**
     * Cancel all reminders
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun cancelAllReminders() {
        QuranReminders.ALL_REMINDERS.forEach { reminder ->
            cancelReminder(reminder.id)
        }
        Log.d("QuranReminderScheduler", "All reminders cancelled")
    }

    /**
     * Check if a specific reminder is scheduled
     */
    fun isReminderScheduled(reminderId: Int): Boolean {
        val intent = Intent(context, receiverClass)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        return pendingIntent != null
    }
}