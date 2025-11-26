package com.islamicapp.siraathalmusthaqeem.screen.hijricalendar

import android.icu.util.IslamicCalendar
import java.util.Calendar

class CalculateHijriDateUseCase {

    operator fun invoke(): HijriCalendar {
        val islamicCalendar = IslamicCalendar()
        val gregorianCal = Calendar.getInstance()

        val hijriDay = islamicCalendar.get(Calendar.DAY_OF_MONTH)
        val hijriMonthIndex = islamicCalendar.get(Calendar.MONTH) // 0–11
        val hijriYear = islamicCalendar.get(Calendar.YEAR)

        val hijriMonth = hijriMonthName(hijriMonthIndex)

        val englishDay = gregorianCal.get(Calendar.DAY_OF_MONTH)
        val englishMonth = gregorianMonthName(gregorianCal.get(Calendar.MONTH))
        val englishYear = gregorianCal.get(Calendar.YEAR)
        val englishWeekday = weekdayName(gregorianCal.get(Calendar.DAY_OF_WEEK))

        return HijriCalendar(
            hijriDay = hijriDay.toString(),
            hijriMonth = hijriMonth,
            hijriYear = hijriYear.toString(),
            englishDay = englishDay.toString(),
            englishMonth = englishMonth,
            englishYear = englishYear.toString(),
            englishWeekday = englishWeekday
        )
    }

    private fun hijriMonthName(index: Int): String {
        return listOf(
            "Muharram", "Safar", "Rabi' al-Awwal", "Rabi' al-Thani",
            "Jumada al-Awwal", "Jumada al-Thani", "Rajab", "Sha'ban",
            "Ramadan", "Shawwal", "Dhul Qa'dah", "Dhul Hijjah"
        )[index]
    }

    private fun gregorianMonthName(index: Int): String {
        return listOf(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
        )[index]
    }

    private fun weekdayName(index: Int): String {
        return listOf(
            "Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday"
        )[index - 1]
    }
}
