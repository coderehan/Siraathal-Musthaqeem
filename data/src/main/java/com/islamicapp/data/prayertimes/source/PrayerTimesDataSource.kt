package com.islamicapp.data.prayertimes.source

import com.islamicapp.domain.prayertimes.model.PrayerTimes

interface PrayerTimesDataSource {
    suspend fun getPrayerTimes(
        date: String,
        latitude: Double,
        longitude: Double
    ): PrayerTimes
}
