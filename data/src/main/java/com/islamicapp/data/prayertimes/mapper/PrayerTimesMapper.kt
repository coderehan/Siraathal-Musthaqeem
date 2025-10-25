package com.islamicapp.data.prayertimes.mapper

import com.islamicapp.data.prayertimes.model.PrayerTimesResponse
import com.islamicapp.domain.prayertimes.model.PrayerTimes

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
            hijriYear = date.hijri.year
        )
    }
}