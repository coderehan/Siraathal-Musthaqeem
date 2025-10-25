package com.islamicapp.data.prayertimes.source.network

import retrofit2.http.Query
import com.islamicapp.data.prayertimes.model.PrayerTimesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PrayerTimesApi {

    @GET("v1/timings/{date}")
    suspend fun getPrayerTimes(
        @Path("date") date: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
    ): PrayerTimesResponse

}