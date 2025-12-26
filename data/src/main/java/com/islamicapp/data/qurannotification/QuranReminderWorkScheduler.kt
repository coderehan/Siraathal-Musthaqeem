package com.islamicapp.data.qurannotification

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.*
import androidx.work.WorkManager
import com.islamicapp.domain.qurannotification.QuranReminder
import com.islamicapp.domain.qurannotification.QuranReminders
import java.util.Calendar
import java.util.concurrent.TimeUnit

/**
 * Scheduler using WorkManager (alternative to AlarmManager)
 * More battery efficient but less precise timing
 */
class QuranReminderWorkScheduler(private val context: Context) {

    private val workManager = WorkManager.getInstance(context)

    /**
     * Schedule all reminders using WorkManager
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleAllReminders() {
        QuranReminders.ALL_REMINDERS.forEach { reminder ->
            if (reminder.isEnabled) {
                scheduleReminder(reminder)
            }
        }
        Log.d("QuranReminderWorkScheduler", "All reminders scheduled via WorkManager")
    }

    /**
     * Schedule a single reminder
     */
    fun scheduleReminder(reminder: QuranReminder) {
        val initialDelay = calculateInitialDelay(reminder.hour, reminder.minute)

        val inputData = workDataOf(
            QuranReminderWorker.KEY_REMINDER_ID to reminder.id
        )

        val dailyWorkRequest = PeriodicWorkRequestBuilder<QuranReminderWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.DAYS
        )
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .addTag("quran_reminder")
            .addTag("reminder_${reminder.id}")
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(false)
                    .build()
            )
            .build()

        workManager.enqueueUniquePeriodicWork(
            "${QuranReminderWorker.WORK_NAME_PREFIX}${reminder.id}",
            ExistingPeriodicWorkPolicy.REPLACE,
            dailyWorkRequest
        )

        Log.d(
            "QuranReminderWorkScheduler",
            "Scheduled ${reminder.surahName} at ${reminder.hour}:${String.format("%02d", reminder.minute)} via WorkManager"
        )
    }

    /**
     * Calculate delay until next occurrence of the reminder time
     */
    private fun calculateInitialDelay(hour: Int, minute: Int): Long {
        val currentTime = Calendar.getInstance()
        val targetTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // If time has passed today, schedule for tomorrow
            if (before(currentTime)) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        return targetTime.timeInMillis - currentTime.timeInMillis
    }

    /**
     * Cancel a specific reminder
     */
    fun cancelReminder(reminderId: Int) {
        workManager.cancelUniqueWork("${QuranReminderWorker.WORK_NAME_PREFIX}$reminderId")
        Log.d("QuranReminderWorkScheduler", "Cancelled WorkManager reminder ID: $reminderId")
    }

    /**
     * Cancel all reminders
     */
    fun cancelAllReminders() {
        workManager.cancelAllWorkByTag("quran_reminder")
        Log.d("QuranReminderWorkScheduler", "All WorkManager reminders cancelled")
    }
}