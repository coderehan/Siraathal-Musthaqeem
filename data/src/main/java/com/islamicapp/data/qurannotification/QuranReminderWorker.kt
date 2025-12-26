package com.islamicapp.data.qurannotification

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.islamicapp.domain.qurannotification.QuranReminders
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.islamicapp.domain.qurannotification.IQuranNotificationManager
import java.time.LocalDate

/**
 * WorkManager-based worker for showing Quran reminders
 * Alternative to AlarmManager (can be used together or separately)
 */
class QuranReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    companion object {
        const val KEY_REMINDER_ID = "reminder_id"
        const val WORK_NAME_PREFIX = "quran_reminder_"
    }

    // Inject notification manager via Koin
    private val notificationManager: IQuranNotificationManager by inject()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        return try {
            val reminderId = inputData.getInt(KEY_REMINDER_ID, -1)

            if (reminderId == -1) {
                Log.e("QuranReminderWorker", "Invalid reminder ID")
                return Result.failure()
            }

            val reminder = QuranReminders.ALL_REMINDERS.find { it.id == reminderId }

            if (reminder == null) {
                Log.e("QuranReminderWorker", "Reminder not found for ID: $reminderId")
                return Result.failure()
            }

            // Check if today is a valid day for this reminder (e.g., Friday for Surah Kahf)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val today = LocalDate.now().dayOfWeek
                if (!reminder.daysOfWeek.contains(today)) {
                    Log.d("QuranReminderWorker", "Skipping ${reminder.surahName} - not scheduled for $today")
                    return Result.success()
                }
            }

            // Show notification using injected manager
            notificationManager.showNotification(reminder)

            Log.d("QuranReminderWorker", "Notification shown for ${reminder.surahName}")

            Result.success()
        } catch (e: Exception) {
            Log.e("QuranReminderWorker", "Error showing notification", e)
            Result.failure()
        }
    }
}