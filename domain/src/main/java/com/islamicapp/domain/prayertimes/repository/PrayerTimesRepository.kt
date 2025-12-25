package com.islamicapp.domain.prayertimes.repository

import com.islamicapp.domain.prayertimes.model.PrayerTimes

interface PrayerTimesRepository {
    suspend fun getPrayerTimes(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): PrayerTimes
}