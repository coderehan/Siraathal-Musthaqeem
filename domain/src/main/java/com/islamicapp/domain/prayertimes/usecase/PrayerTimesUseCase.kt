package com.islamicapp.domain.prayertimes.usecase

import com.islamicapp.domain.prayertimes.model.PrayerTimes
import com.islamicapp.domain.prayertimes.repository.PrayerTimesRepository

class PrayerTimesUseCase(
    private val repository: PrayerTimesRepository
) {
    suspend operator fun invoke(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): PrayerTimes {
        return repository.getPrayerTimes(date, latitude, longitude, method, school)
    }
}