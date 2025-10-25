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
        longitude: Double
    ): PrayerTimes {
        // If empty, use today's date
        val finalDate = if (date.isBlank()) {
            SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        } else date

        // Call API
        val response = api.getPrayerTimes(finalDate, latitude, longitude)

        // Map to domain model
        return PrayerTimesMapper().map(response)
    }
}
