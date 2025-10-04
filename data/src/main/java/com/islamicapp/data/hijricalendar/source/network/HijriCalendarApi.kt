package com.islamicapp.data.hijricalendar.source.network

import com.islamicapp.data.hijricalendar.model.HijriCalendarResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HijriCalendarApi {

    @GET("/v1/gToH/{date}")
    suspend fun getHijriCalendarDate(
        @Path("date") date: String
    ): HijriCalendarResponse

}
