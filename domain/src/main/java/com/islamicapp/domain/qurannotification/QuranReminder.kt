package com.islamicapp.domain.qurannotification

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek

data class QuranReminder(
    val id: Int,
    val surahName: String,
    val surahNameArabic: String,
    val hour: Int,
    val minute: Int,
    val title: String,
    val description: String,
    val daysOfWeek: List<DayOfWeek> = DayOfWeek.values().toList(), // Daily by default
    val isEnabled: Boolean = true
)

object QuranReminders {
    @RequiresApi(Build.VERSION_CODES.O)
    val ALL_REMINDERS = listOf(
        QuranReminder(
            id = 1,
            surahName = "Yaseen",
            surahNameArabic = "سورة يس",
            hour = 6,
            minute = 0,
            title = "🌅 Start Your Day with Barakah",
            description = "Recite Surah Yaseen and fill your morning with blessings",
        ),
        QuranReminder(
            id = 2,
            surahName = "Waqiah",
            surahNameArabic = "سورة الواقعة",
            hour = 19,
            minute = 0,
            title = "💰 Attract Rizq and Prosperity",
            description = "Recite Surah Al-Waqiah for abundance and wealth",
        ),
        QuranReminder(
            id = 3,
            surahName = "Mulk",
            surahNameArabic = "سورة الملك",
            hour = 21,
            minute = 0,
            title = "🛡️ Protection from the Grave",
            description = "Recite Surah Al-Mulk before sleep for divine protection",
        ),
        QuranReminder(
            id = 4,
            surahName = "Kahf",
            surahNameArabic = "سورة الكهف",
            hour = 12,
            minute = 0,
            title = "✨ Light Between Two Jumu'ahs",
            description = "Recite Surah Al-Kahf on Friday for a week of light",
            daysOfWeek = listOf(DayOfWeek.FRIDAY)
        )
    )
}