package com.islamicapp.data.prayertimes.mapper

import com.islamicapp.data.prayertimes.model.PrayerTimesResponse
import com.islamicapp.domain.prayertimes.model.PrayerTimes
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PrayerTimesMapper {

    fun map(input: PrayerTimesResponse): PrayerTimes {
        val timings = input.data.timings
        val date = input.data.date

        return PrayerTimes(
            fajr = timings.Fajr,
            sunrise = timings.Sunrise,
            dhuhr = timings.Dhuhr,
            asr = timings.Asr,
            sunset = timings.Sunset,
            maghrib = timings.Maghrib,
            isha = timings.Isha,
            tahajjud = timings.Lastthird,
            gregorianDate = date.readable,
            hijriDay = date.hijri.day,
            hijriMonth = date.hijri.month.en,
            hijriYear = date.hijri.year,
            suhoorEnd = calculateSuhoorEnd(timings.Fajr),
            iftar = timings.Maghrib
        )
    }

    /**
     * Calculate Suhoor end time (10 minutes before Fajr for safety)
     */
    private fun calculateSuhoorEnd(fajrTime: String): String {
        return try {
            val format = SimpleDateFormat("HH:mm", Locale.getDefault())
            val calendar = Calendar.getInstance()

            // Parse Fajr time
            val time = format.parse(fajrTime) ?: return fajrTime
            calendar.time = time

            // Subtract 10 minutes for safety
            calendar.add(Calendar.MINUTE, -10)

            // Format back to string
            format.format(calendar.time)
        } catch (e: Exception) {
            fajrTime // Return original if parsing fails
        }
    }
}