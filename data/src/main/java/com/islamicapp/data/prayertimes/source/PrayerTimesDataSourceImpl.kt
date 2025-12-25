package com.islamicapp.data.prayertimes.source

import com.islamicapp.domain.prayertimes.model.PrayerTimes
import com.islamicapp.domain.prayertimes.repository.PrayerTimesRepository

class PrayerTimesDataSourceImpl(
    private val dataSource: PrayerTimesDataSource
) : PrayerTimesRepository {

    override suspend fun getPrayerTimes(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): PrayerTimes {
        return dataSource.getPrayerTimes(date, latitude, longitude, method, school)
    }
}