package com.islamicapp.data.prayertimes.source.network

import com.islamicapp.data.prayertimes.mapper.PrayerTimesMapper
import com.islamicapp.data.prayertimes.source.PrayerTimesDataSource
import com.islamicapp.domain.prayertimes.model.PrayerTimes
import java.text.SimpleDateFormat
import java.util.*

class PrayerTimesNetworkDataSource(
    private val api: PrayerTimesApi
) : PrayerTimesDataSource {

    override suspend fun getPrayerTimes(
        date: String,
        latitude: Double,
        longitude: Double,
        method: Int,
        school: Int
    ): PrayerTimes {
        val finalDate = if (date.isBlank()) {
            SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        } else date

        val response = api.getPrayerTimes(finalDate, latitude, longitude, method, school)
        return PrayerTimesMapper().map(response)
    }
}
